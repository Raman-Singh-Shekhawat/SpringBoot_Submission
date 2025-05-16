package com.inventory.service;

import com.inventory.dto.SalesInvoiceDTO;
import com.inventory.model.SalesInvoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface SalesInvoiceService {
    
    SalesInvoice createSalesInvoice(SalesInvoiceDTO salesInvoiceDTO);
    
    SalesInvoice updateSalesInvoice(Long id, SalesInvoiceDTO salesInvoiceDTO);
    
    SalesInvoice recordPayment(Long id, BigDecimal amountPaid);
    
    SalesInvoice getSalesInvoiceById(Long id);
    
    SalesInvoice getSalesInvoiceByNumber(String invoiceNumber);
    
    List<SalesInvoice> getAllSalesInvoices();
    
    List<SalesInvoice> getSalesInvoicesByCustomer(Long customerId);
    
    List<SalesInvoice> getSalesInvoicesByPaymentStatus(SalesInvoice.PaymentStatus paymentStatus);
    
    List<SalesInvoice> getSalesInvoicesByDateRange(LocalDate startDate, LocalDate endDate);
    
    List<SalesInvoice> getOverdueSalesInvoices();
}