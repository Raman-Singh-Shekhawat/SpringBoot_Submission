package com.inventory.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesInvoiceDTO {
    
    private String invoiceNumber;
    
    @NotNull(message = "Invoice date is required")
    private LocalDate invoiceDate;
    
    @NotNull(message = "Customer is required")
    private Long customerId;
    
    @NotEmpty(message = "Sales invoice must have at least one item")
    @Valid
    private List<SalesInvoiceItemDTO> items;
    
    @Min(value = 0, message = "Tax amount must be positive")
    private BigDecimal taxAmount;
    
    @Min(value = 0, message = "Discount amount must be positive")
    private BigDecimal discountAmount;
    
    private String notes;
    
    private LocalDate dueDate;
}