package com.inventory.service.impl;

import com.inventory.dto.PurchaseOrderDTO;
import com.inventory.dto.PurchaseOrderItemDTO;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.Product;
import com.inventory.model.PurchaseOrder;
import com.inventory.model.PurchaseOrderItem;
import com.inventory.model.Vendor;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.PurchaseOrderRepository;
import com.inventory.repository.VendorRepository;
import com.inventory.service.ProductService;
import com.inventory.service.PurchaseOrderService;
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
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final VendorRepository vendorRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    
    @Override
    @Transactional
    public PurchaseOrder createPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO) {
        Vendor vendor = vendorRepository.findById(purchaseOrderDTO.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + purchaseOrderDTO.getVendorId()));
        
        PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                .orderNumber(generateOrderNumber())
                .orderDate(purchaseOrderDTO.getOrderDate())
                .expectedDeliveryDate(purchaseOrderDTO.getExpectedDeliveryDate())
                .vendor(vendor)
                .status(PurchaseOrder.PurchaseOrderStatus.PENDING)
                .notes(purchaseOrderDTO.getNotes())
                .items(new ArrayList<>())
                .totalAmount(BigDecimal.ZERO)
                .build();
        
        List<PurchaseOrderItem> items = purchaseOrderDTO.getItems().stream()
                .map(itemDTO -> createPurchaseOrderItem(itemDTO, purchaseOrder))
                .collect(Collectors.toList());
        
        purchaseOrder.setItems(items);
        purchaseOrder.calculateTotal();
        
        return purchaseOrderRepository.save(purchaseOrder);
    }
    
    @Override
    @Transactional
    public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseOrder purchaseOrder = getPurchaseOrderById(id);
        
        if (purchaseOrder.getStatus() != PurchaseOrder.PurchaseOrderStatus.PENDING) {
            throw new IllegalStateException("Cannot update purchase order that is not in PENDING status");
        }
        
        Vendor vendor = vendorRepository.findById(purchaseOrderDTO.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + purchaseOrderDTO.getVendorId()));
        
        purchaseOrder.setOrderDate(purchaseOrderDTO.getOrderDate());
        purchaseOrder.setExpectedDeliveryDate(purchaseOrderDTO.getExpectedDeliveryDate());
        purchaseOrder.setVendor(vendor);
        purchaseOrder.setNotes(purchaseOrderDTO.getNotes());
        
        // Clear existing items and add new ones
        purchaseOrder.getItems().clear();
        List<PurchaseOrderItem> items = purchaseOrderDTO.getItems().stream()
                .map(itemDTO -> createPurchaseOrderItem(itemDTO, purchaseOrder))
                .collect(Collectors.toList());
        
        purchaseOrder.setItems(items);
        purchaseOrder.calculateTotal();
        
        return purchaseOrderRepository.save(purchaseOrder);
    }
    
    @Override
    @Transactional
    public void cancelPurchaseOrder(Long id, String cancelReason) {
        PurchaseOrder purchaseOrder = getPurchaseOrderById(id);
        
        if (purchaseOrder.getStatus() != PurchaseOrder.PurchaseOrderStatus.PENDING) {
            throw new IllegalStateException("Cannot cancel purchase order that is not in PENDING status");
        }
        
        purchaseOrder.setStatus(PurchaseOrder.PurchaseOrderStatus.CANCELLED);
        purchaseOrder.setNotes(purchaseOrder.getNotes() + "\nCancellation reason: " + cancelReason);
        
        purchaseOrderRepository.save(purchaseOrder);
    }
    
    @Override
    @Transactional
    public PurchaseOrder closePurchaseOrder(Long id) {
        PurchaseOrder purchaseOrder = getPurchaseOrderById(id);
        
        if (purchaseOrder.getStatus() != PurchaseOrder.PurchaseOrderStatus.RECEIVED) {
            throw new IllegalStateException("Cannot close purchase order that is not in RECEIVED status");
        }
        
        purchaseOrder.setStatus(PurchaseOrder.PurchaseOrderStatus.CLOSED);
        
        return purchaseOrderRepository.save(purchaseOrder);
    }
    
    @Override
    public PurchaseOrder getPurchaseOrderById(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase order not found with id: " + id));
    }
    
    @Override
    public PurchaseOrder getPurchaseOrderByNumber(String orderNumber) {
        return purchaseOrderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase order not found with number: " + orderNumber));
    }
    
    @Override
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }
    
    @Override
    public List<PurchaseOrder> getPurchaseOrdersByVendor(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + vendorId));
        return purchaseOrderRepository.findByVendor(vendor);
    }
    
    @Override
    public List<PurchaseOrder> getPurchaseOrdersByStatus(PurchaseOrder.PurchaseOrderStatus status) {
        return purchaseOrderRepository.findByStatus(status);
    }
    
    @Override
    public List<PurchaseOrder> getPurchaseOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
        return purchaseOrderRepository.findByOrderDateBetween(startDate, endDate);
    }
    
    @Override
    public List<PurchaseOrder> getOpenPurchaseOrders() {
        return purchaseOrderRepository.findOpenPurchaseOrders();
    }
    
    private PurchaseOrderItem createPurchaseOrderItem(PurchaseOrderItemDTO itemDTO, PurchaseOrder purchaseOrder) {
        Product product = productRepository.findById(itemDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemDTO.getProductId()));
        
        return PurchaseOrderItem.builder()
                .purchaseOrder(purchaseOrder)
                .product(product)
                .quantity(itemDTO.getQuantity())
                .unitPrice(itemDTO.getUnitPrice())
                .discountPercentage(itemDTO.getDiscountPercentage())
                .notes(itemDTO.getNotes())
                .build();
    }
    
    private String generateOrderNumber() {
        // Simple implementation - in production, use a more sophisticated method
        return "PO-" + System.currentTimeMillis();
    }
}