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
@Table(name = "sales_invoice_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesInvoiceItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "sales_invoice_id", nullable = false)
    private SalesInvoice salesInvoice;
    
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
    private Float discountPercentage = 0f;
    
    @Min(value = 0, message = "Tax percentage must be positive")
    private Float taxPercentage = 0f;
    
    public BigDecimal getSubtotal() {
        BigDecimal subtotal = unitPrice.multiply(new BigDecimal(quantity));
        if (discountPercentage > 0) {
            BigDecimal discountAmount = subtotal.multiply(new BigDecimal(discountPercentage / 100f));
            subtotal = subtotal.subtract(discountAmount);
        }
        return subtotal;
    }
    
    public BigDecimal getTaxAmount() {
        if (taxPercentage > 0) {
            return getSubtotal().multiply(new BigDecimal(taxPercentage / 100f));
        }
        return BigDecimal.ZERO;
    }
    
    public BigDecimal getTotalAmount() {
        return getSubtotal().add(getTaxAmount());
    }
}