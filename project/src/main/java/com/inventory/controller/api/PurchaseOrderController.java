package com.inventory.controller.api;

import com.inventory.dto.PurchaseOrderDTO;
import com.inventory.model.PurchaseOrder;
import com.inventory.service.PurchaseOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {
    
    private final PurchaseOrderService purchaseOrderService;
    
    @PostMapping
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(@Valid @RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseOrder createdPurchaseOrder = purchaseOrderService.createPurchaseOrder(purchaseOrderDTO);
        return new ResponseEntity<>(createdPurchaseOrder, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrder> updatePurchaseOrder(@PathVariable Long id, @Valid @RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseOrder updatedPurchaseOrder = purchaseOrderService.updatePurchaseOrder(id, purchaseOrderDTO);
        return ResponseEntity.ok(updatedPurchaseOrder);
    }
    
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelPurchaseOrder(@PathVariable Long id, @RequestParam String reason) {
        purchaseOrderService.cancelPurchaseOrder(id, reason);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{id}/close")
    public ResponseEntity<PurchaseOrder> closePurchaseOrder(@PathVariable Long id) {
        PurchaseOrder closedPurchaseOrder = purchaseOrderService.closePurchaseOrder(id);
        return ResponseEntity.ok(closedPurchaseOrder);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrderById(@PathVariable Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(id);
        return ResponseEntity.ok(purchaseOrder);
    }
    
    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> getAllPurchaseOrders() {
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.getAllPurchaseOrders();
        return ResponseEntity.ok(purchaseOrders);
    }
    
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrderByNumber(@PathVariable String orderNumber) {
        PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderByNumber(orderNumber);
        return ResponseEntity.ok(purchaseOrder);
    }
    
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<PurchaseOrder>> getPurchaseOrdersByVendor(@PathVariable Long vendorId) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.getPurchaseOrdersByVendor(vendorId);
        return ResponseEntity.ok(purchaseOrders);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PurchaseOrder>> getPurchaseOrdersByStatus(@PathVariable PurchaseOrder.PurchaseOrderStatus status) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.getPurchaseOrdersByStatus(status);
        return ResponseEntity.ok(purchaseOrders);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<PurchaseOrder>> getPurchaseOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.getPurchaseOrdersByDateRange(startDate, endDate);
        return ResponseEntity.ok(purchaseOrders);
    }
    
    @GetMapping("/open")
    public ResponseEntity<List<PurchaseOrder>> getOpenPurchaseOrders() {
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.getOpenPurchaseOrders();
        return ResponseEntity.ok(purchaseOrders);
    }
}