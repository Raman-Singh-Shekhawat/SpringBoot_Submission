package com.inventory.service.impl;

import com.inventory.dto.SalesInvoiceDTO;
import com.inventory.dto.SalesInvoiceItemDTO;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.Customer;
import com.inventory.model.Product;
import com.inventory.model.SalesInvoice;
import com.inventory.model.SalesInvoiceItem;
import com.inventory.repository.CustomerRepository;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.SalesInvoiceRepository;
import com.inventory.service.ProductService;
import com.inventory.service.SalesInvoiceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesInvoiceServiceImpl implements SalesInvoiceService {
    
    private final SalesInvoiceRepository salesInvoiceRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    
    @Override
    @Transactional
    public SalesInvoice createSalesInvoice(SalesInvoiceDTO salesInvoiceDTO) {
        Customer customer = customerRepository.findById(salesInvoiceDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + salesInvoiceDTO.getCustomerId()));
        
        SalesInvoice salesInvoice = SalesInvoice.builder()
                .invoiceNumber(generateInvoiceNumber())
                .invoiceDate(salesInvoiceDTO.getInvoiceDate())
                .customer(customer)
                .items(new ArrayList<>())
                .taxAmount(salesInvoiceDTO.getTaxAmount() != null ? salesInvoiceDTO.getTaxAmount() : BigDecimal.ZERO)
                .discountAmount(salesInvoiceDTO.getDiscountAmount() != null ? salesInvoiceDTO.getDiscountAmount() : BigDecimal.ZERO)
                .notes(salesInvoiceDTO.getNotes())
                .dueDate(salesInvoiceDTO.getDueDate())
                .build();
        
        List<SalesInvoiceItem> items = salesInvoiceDTO.getItems().stream()
                .map(itemDTO -> createSalesInvoiceItem(itemDTO, salesInvoice))
                .collect(Collectors.toList());
        
        salesInvoice.setItems(items);
        salesInvoice.calculateTotals();
        
        SalesInvoice savedInvoice = salesInvoiceRepository.save(salesInvoice);
        
        // Update inventory for each item
        items.forEach(item -> {
            productService.updateInventory(
                item.getProduct().getId(),
                -item.getQuantity(),
                savedInvoice.getInvoiceNumber(),
                "Sales Invoice: " + savedInvoice.getInvoiceNumber()
            );
        });
        
        return savedInvoice;
    }
    
    @Override
    @Transactional
    public SalesInvoice updateSalesInvoice(Long id, SalesInvoiceDTO salesInvoiceDTO) {
        SalesInvoice salesInvoice = getSalesInvoiceById(id);
        
        if (salesInvoice.getPaymentStatus() == SalesInvoice.PaymentStatus.PAID) {
            throw new IllegalStateException("Cannot update a paid invoice");
        }
        
        Customer customer = customerRepository.findById(salesInvoiceDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + salesInvoiceDTO.getCustomerId()));
        
        // Reverse inventory changes for old items
        salesInvoice.getItems().forEach(item -> {
            productService.updateInventory(
                item.getProduct().getId(),
                item.getQuantity(),
                salesInvoice.getInvoiceNumber(),
                "Sales Invoice Update (Reversal): " + salesInvoice.getInvoiceNumber()
            );
        });
        
        salesInvoice.setInvoiceDate(salesInvoiceDTO.getInvoiceDate());
        salesInvoice.setCustomer(customer);
        salesInvoice.setTaxAmount(salesInvoiceDTO.getTaxAmount() != null ? salesInvoiceDTO.getTaxAmount() : BigDecimal.ZERO);
        salesInvoice.setDiscountAmount(salesInvoiceDTO.getDiscountAmount() != null ? salesInvoiceDTO.getDiscountAmount() : BigDecimal.ZERO);
        salesInvoice.setNotes(salesInvoiceDTO.getNotes());
        salesInvoice.setDueDate(salesInvoiceDTO.getDueDate());
        
        // Clear existing items and add new ones
        salesInvoice.getItems().clear();
        List<SalesInvoiceItem> items = salesInvoiceDTO.getItems().stream()
                .map(itemDTO -> createSalesInvoiceItem(itemDTO, salesInvoice))
                .collect(Collectors.toList());
        
        salesInvoice.setItems(items);
        salesInvoice.calculateTotals();
        
        SalesInvoice updatedInvoice = salesInvoiceRepository.save(salesInvoice);
        
        // Update inventory for new items
        items.forEach(item -> {
            productService.updateInventory(
                item.getProduct().getId(),
                -item.getQuantity(),
                updatedInvoice.getInvoiceNumber(),
                "Sales Invoice Update: " + updatedInvoice.getInvoiceNumber()
            );
        });
        
        return updatedInvoice;
    }
    
    @Override
    @Transactional
    public SalesInvoice recordPayment(Long id, BigDecimal amountPaid) {
        SalesInvoice salesInvoice = getSalesInvoiceById(id);
        
        if (amountPaid.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }
        
        BigDecimal newTotalPaid = salesInvoice.getAmountPaid().add(amountPaid);
        if (newTotalPaid.compareTo(salesInvoice.getTotalAmount()) > 0) {
            throw new IllegalArgumentException("Payment amount exceeds remaining balance");
        }
        
        salesInvoice.setAmountPaid(newTotalPaid);
        salesInvoice.updatePaymentStatus();
        
        return salesInvoiceRepository.save(salesInvoice);
    }
    
    @Override
    public SalesInvoice getSalesInvoiceById(Long id) {
        return salesInvoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sales invoice not found with id: " + id));
    }
    
    @Override
    public SalesInvoice getSalesInvoiceByNumber(String invoiceNumber) {
        return salesInvoiceRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Sales invoice not found with number: " + invoiceNumber));
    }
    
    @Override
    public List<SalesInvoice> getAllSalesInvoices() {
        return salesInvoiceRepository.findAll();
    }
    
    @Override
    public List<SalesInvoice> getSalesInvoicesByCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        return salesInvoiceRepository.findByCustomer(customer);
    }
    
    @Override
    public List<SalesInvoice> getSalesInvoicesByPaymentStatus(SalesInvoice.PaymentStatus paymentStatus) {
        return salesInvoiceRepository.findByPaymentStatus(paymentStatus);
    }
    
    @Override
    public List<SalesInvoice> getSalesInvoicesByDateRange(LocalDate startDate, LocalDate endDate) {
        return salesInvoiceRepository.findByInvoiceDateBetween(startDate, endDate);
    }
    
    @Override
    public List<SalesInvoice> getOverdueSalesInvoices() {
        return salesInvoiceRepository.findOverdueInvoices(LocalDate.now());
    }
    
    private SalesInvoiceItem createSalesInvoiceItem(SalesInvoiceItemDTO itemDTO, SalesInvoice salesInvoice) {
        Product product = productRepository.findById(itemDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemDTO.getProductId()));
        
        return SalesInvoiceItem.builder()
                .salesInvoice(salesInvoice)
                .product(product)
                .quantity(itemDTO.getQuantity())
                .unitPrice(itemDTO.getUnitPrice())
                .discountPercentage(itemDTO.getDiscountPercentage())
                .taxPercentage(itemDTO.getTaxPercentage())
                .build();
    }
    
    private String generateInvoiceNumber() {
        // Simple implementation - in production, use a more sophisticated method
        return "INV-" + System.currentTimeMillis();
    }
}