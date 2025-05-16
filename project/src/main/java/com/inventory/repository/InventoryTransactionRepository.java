package com.inventory.repository;

import com.inventory.model.InventoryTransaction;
import com.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {
    
    List<InventoryTransaction> findByProduct(Product product);
    
    List<InventoryTransaction> findByProductAndTransactionDateBetween(
            Product product, LocalDateTime startDate, LocalDateTime endDate);
    
    List<InventoryTransaction> findByTransactionType(InventoryTransaction.TransactionType transactionType);
    
    List<InventoryTransaction> findByTransactionDateBetween(
            LocalDateTime startDate, LocalDateTime endDate);
}