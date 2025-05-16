package com.inventory.repository;

import com.inventory.model.PurchaseInvoice;
import com.inventory.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseInvoiceRepository extends JpaRepository<PurchaseInvoice, Long> {
    
    Optional<PurchaseInvoice> findByInvoiceNumber(String invoiceNumber);
    
    Optional<PurchaseInvoice> findByPurchaseOrder(PurchaseOrder purchaseOrder);
    
    List<PurchaseInvoice> findByPaymentStatus(PurchaseInvoice.PaymentStatus paymentStatus);
    
    List<PurchaseInvoice> findByInvoiceDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT pi FROM PurchaseInvoice pi WHERE pi.dueDate <= :date AND pi.paymentStatus != 'PAID'")
    List<PurchaseInvoice> findOverdueInvoices(LocalDate date);
    
    boolean existsByInvoiceNumber(String invoiceNumber);
}