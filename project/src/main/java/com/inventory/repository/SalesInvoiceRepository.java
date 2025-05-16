package com.inventory.repository;

import com.inventory.model.Customer;
import com.inventory.model.SalesInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalesInvoiceRepository extends JpaRepository<SalesInvoice, Long> {
    
    Optional<SalesInvoice> findByInvoiceNumber(String invoiceNumber);
    
    List<SalesInvoice> findByCustomer(Customer customer);
    
    List<SalesInvoice> findByPaymentStatus(SalesInvoice.PaymentStatus paymentStatus);
    
    List<SalesInvoice> findByInvoiceDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT si FROM SalesInvoice si WHERE si.dueDate <= :date AND si.paymentStatus != 'PAID'")
    List<SalesInvoice> findOverdueInvoices(LocalDate date);
    
    boolean existsByInvoiceNumber(String invoiceNumber);
}