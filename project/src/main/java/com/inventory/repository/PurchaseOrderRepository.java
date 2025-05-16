package com.inventory.repository;

import com.inventory.model.PurchaseOrder;
import com.inventory.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    
    Optional<PurchaseOrder> findByOrderNumber(String orderNumber);
    
    List<PurchaseOrder> findByVendor(Vendor vendor);
    
    List<PurchaseOrder> findByStatus(PurchaseOrder.PurchaseOrderStatus status);
    
    List<PurchaseOrder> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT po FROM PurchaseOrder po WHERE po.status = 'PENDING' OR po.status = 'APPROVED'")
    List<PurchaseOrder> findOpenPurchaseOrders();
    
    boolean existsByOrderNumber(String orderNumber);
}