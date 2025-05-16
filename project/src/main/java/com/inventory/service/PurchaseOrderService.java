package com.inventory.service;

import com.inventory.dto.PurchaseOrderDTO;
import com.inventory.model.PurchaseOrder;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseOrderService {
    
    PurchaseOrder createPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO);
    
    PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrderDTO purchaseOrderDTO);
    
    void cancelPurchaseOrder(Long id, String cancelReason);
    
    PurchaseOrder closePurchaseOrder(Long id);
    
    PurchaseOrder getPurchaseOrderById(Long id);
    
    PurchaseOrder getPurchaseOrderByNumber(String orderNumber);
    
    List<PurchaseOrder> getAllPurchaseOrders();
    
    List<PurchaseOrder> getPurchaseOrdersByVendor(Long vendorId);
    
    List<PurchaseOrder> getPurchaseOrdersByStatus(PurchaseOrder.PurchaseOrderStatus status);
    
    List<PurchaseOrder> getPurchaseOrdersByDateRange(LocalDate startDate, LocalDate endDate);
    
    List<PurchaseOrder> getOpenPurchaseOrders();
}