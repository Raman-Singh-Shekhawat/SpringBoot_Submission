package com.inventory.service;

import com.inventory.dto.ProductDTO;
import com.inventory.model.Product;

import java.util.List;

public interface ProductService {
    
    Product createProduct(ProductDTO productDTO);
    
    Product updateProduct(Long id, ProductDTO productDTO);
    
    void deleteProduct(Long id);
    
    Product getProductById(Long id);
    
    Product getProductByCode(String productCode);
    
    List<Product> getAllProducts();
    
    List<Product> getProductsByCategory(Long categoryId);
    
    List<Product> getProductsBelowReorderLevel();
    
    List<Product> searchProducts(String searchTerm);
    
    boolean updateInventory(Long productId, int quantityChange, String referenceNumber, String notes);
}