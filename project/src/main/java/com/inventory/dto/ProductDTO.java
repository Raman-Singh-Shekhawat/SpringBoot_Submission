package com.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    
    @NotBlank(message = "Product code is required")
    private String productCode;
    
    @NotBlank(message = "Product name is required")
    private String name;
    
    private String description;
    
    @NotNull(message = "Purchase price is required")
    @Min(value = 0, message = "Purchase price must be positive")
    private BigDecimal purchasePrice;
    
    @NotNull(message = "Selling price is required")
    @Min(value = 0, message = "Selling price must be positive")
    private BigDecimal sellingPrice;
    
    @NotNull(message = "Current quantity is required")
    @Min(value = 0, message = "Current quantity cannot be negative")
    private Integer currentQuantity;
    
    @NotNull(message = "Reorder level is required")
    @Min(value = 0, message = "Reorder level must be positive")
    private Integer reorderLevel;
    
    @NotNull(message = "Category is required")
    private Long categoryId;
}