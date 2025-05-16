package com.inventory.controller.api;

import com.inventory.dto.SalesInvoiceDTO;
import com.inventory.model.SalesInvoice;
import com.inventory.service.SalesInvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sales-invoices")
@RequiredArgsConstructor
public class SalesInvoiceController {
    
    private final SalesInvoiceService salesInvoiceService;
    
    @PostMapping
    public ResponseEntity<SalesInvoice> createSalesInvoice(@Valid @RequestBody SalesInvoiceDTO salesInvoiceDTO) {
        SalesInvoice createdSalesInvoice = salesInvoiceService.createSalesInvoice(salesInvoiceDTO);
        return new ResponseEntity<>(createdSalesInvoice, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SalesInvoice> updateSalesInvoice(@PathVariable Long id, @Valid @RequestBody SalesInvoiceDTO salesInvoiceDTO) {
        SalesInvoice updatedSalesInvoice = salesInvoiceService.updateSalesInvoice(id, salesInvoiceDTO);
        return ResponseEntity.ok(updatedSalesInvoice);
    }
    
    @PostMapping("/{id}/payment")
    public ResponseEntity<SalesInvoice> recordPayment(@PathVariable Long id, @RequestParam BigDecimal amount) {
        SalesInvoice updatedSalesInvoice = salesInvoiceService.recordPayment(id, amount);
        return ResponseEntity.ok(updatedSalesInvoice);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SalesInvoice> getSalesInvoiceById(@PathVariable Long id) {
        SalesInvoice salesInvoice = salesInvoiceService.getSalesInvoiceById(id);
        return ResponseEntity.ok(salesInvoice);
    }
    
    @GetMapping
    public ResponseEntity<List<SalesInvoice>> getAllSalesInvoices() {
        List<SalesInvoice> salesInvoices = salesInvoiceService.getAllSalesInvoices();
        return ResponseEntity.ok(salesInvoices);
    }
    
    @GetMapping("/number/{invoiceNumber}")
    public ResponseEntity<SalesInvoice> getSalesInvoiceByNumber(@PathVariable String invoiceNumber) {
        SalesInvoice salesInvoice = salesInvoiceService.getSalesInvoiceByNumber(invoiceNumber);
        return ResponseEntity.ok(salesInvoice);
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<SalesInvoice>> getSalesInvoicesByCustomer(@PathVariable Long customerId) {
        List<SalesInvoice> salesInvoices = salesInvoiceService.getSalesInvoicesByCustomer(customerId);
        return ResponseEntity.ok(salesInvoices);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<SalesInvoice>> getSalesInvoicesByPaymentStatus(@PathVariable SalesInvoice.PaymentStatus status) {
        List<SalesInvoice> salesInvoices = salesInvoiceService.getSalesInvoicesByPaymentStatus(status);
        return ResponseEntity.ok(salesInvoices);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<SalesInvoice>> getSalesInvoicesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<SalesInvoice> salesInvoices = salesInvoiceService.getSalesInvoicesByDateRange(startDate, endDate);
        return ResponseEntity.ok(salesInvoices);
    }
    
    @GetMapping("/overdue")
    public ResponseEntity<List<SalesInvoice>> getOverdueSalesInvoices() {
        List<SalesInvoice> salesInvoices = salesInvoiceService.getOverdueSalesInvoices();
        return ResponseEntity.ok(salesInvoices);
    }
}