package com.inventory.service.impl;

import com.inventory.dto.ProductDTO;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.Category;
import com.inventory.model.InventoryTransaction;
import com.inventory.model.Product;
import com.inventory.repository.CategoryRepository;
import com.inventory.repository.InventoryTransactionRepository;
import com.inventory.repository.ProductRepository;
import com.inventory.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final InventoryTransactionRepository inventoryTransactionRepository;
    
    @Override
    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        if (productRepository.existsByProductCode(productDTO.getProductCode())) {
            throw new IllegalArgumentException("Product code already exists");
        }
        
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productDTO.getCategoryId()));
        
        Product product = Product.builder()
                .productCode(productDTO.getProductCode())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .purchasePrice(productDTO.getPurchasePrice())
                .sellingPrice(productDTO.getSellingPrice())
                .currentQuantity(productDTO.getCurrentQuantity())
                .reorderLevel(productDTO.getReorderLevel())
                .category(category)
                .build();
        
        Product savedProduct = productRepository.save(product);
        
        // Create initial inventory transaction if initial quantity is greater than 0
        if (productDTO.getCurrentQuantity() > 0) {
            InventoryTransaction transaction = InventoryTransaction.builder()
                    .product(savedProduct)
                    .transactionDate(LocalDateTime.now())
                    .transactionType(InventoryTransaction.TransactionType.ADJUSTMENT)
                    .quantity(productDTO.getCurrentQuantity())
                    .notes("Initial inventory setup")
                    .build();
            
            inventoryTransactionRepository.save(transaction);
        }
        
        return savedProduct;
    }
    
    @Override
    @Transactional
    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product product = getProductById(id);
        
        if (!product.getProductCode().equals(productDTO.getProductCode()) && 
                productRepository.existsByProductCode(productDTO.getProductCode())) {
            throw new IllegalArgumentException("Product code already exists");
        }
        
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productDTO.getCategoryId()));
        
        int quantityDifference = productDTO.getCurrentQuantity() - product.getCurrentQuantity();
        
        product.setProductCode(productDTO.getProductCode());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPurchasePrice(productDTO.getPurchasePrice());
        product.setSellingPrice(productDTO.getSellingPrice());
        product.setCurrentQuantity(productDTO.getCurrentQuantity());
        product.setReorderLevel(productDTO.getReorderLevel());
        product.setCategory(category);
        
        Product updatedProduct = productRepository.save(product);
        
        // Create inventory transaction if quantity was changed
        if (quantityDifference != 0) {
            InventoryTransaction transaction = InventoryTransaction.builder()
                    .product(updatedProduct)
                    .transactionDate(LocalDateTime.now())
                    .transactionType(InventoryTransaction.TransactionType.ADJUSTMENT)
                    .quantity(Math.abs(quantityDifference))
                    .notes("Quantity adjustment from product update")
                    .build();
            
            inventoryTransactionRepository.save(transaction);
        }
        
        return updatedProduct;
    }
    
    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
    
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }
    
    @Override
    public Product getProductByCode(String productCode) {
        return productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with code: " + productCode));
    }
    
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
    
    @Override
    public List<Product> getProductsBelowReorderLevel() {
        return productRepository.findProductsBelowReorderLevel();
    }
    
    @Override
    public List<Product> searchProducts(String searchTerm) {
        return productRepository.searchProducts(searchTerm);
    }
    
    @Override
    @Transactional
    public boolean updateInventory(Long productId, int quantityChange, String referenceNumber, String notes) {
        Product product = getProductById(productId);
        
        int newQuantity = product.getCurrentQuantity() + quantityChange;
        if (newQuantity < 0) {
            return false; // Cannot have negative inventory
        }
        
        product.setCurrentQuantity(newQuantity);
        productRepository.save(product);
        
        // Determine transaction type
        InventoryTransaction.TransactionType transactionType;
        if (quantityChange > 0) {
            transactionType = InventoryTransaction.TransactionType.PURCHASE;
        } else {
            transactionType = InventoryTransaction.TransactionType.SALES;
        }
        
        // Record the transaction
        InventoryTransaction transaction = InventoryTransaction.builder()
                .product(product)
                .transactionDate(LocalDateTime.now())
                .transactionType(transactionType)
                .quantity(Math.abs(quantityChange))
                .referenceNumber(referenceNumber)
                .notes(notes)
                .build();
        
        inventoryTransactionRepository.save(transaction);
        return true;
    }
}