package com.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "purchase_order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder purchaseOrder;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    
    @NotNull(message = "Unit price is required")
    @Min(value = 0, message = "Unit price must be positive")
    private BigDecimal unitPrice;
    
    @Min(value = 0, message = "Discount percentage must be positive")
    private Float discountPercentage;
    
    private String notes;
    
    public BigDecimal getSubtotal() {
        BigDecimal subtotal = unitPrice.multiply(new BigDecimal(quantity));
        if (discountPercentage != null && discountPercentage > 0) {
            BigDecimal discountAmount = subtotal.multiply(new BigDecimal(discountPercentage / 100f));
            subtotal = subtotal.subtract(discountAmount);
        }
        return subtotal;
    }
}