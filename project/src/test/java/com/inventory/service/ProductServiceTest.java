package com.inventory.service;

import com.inventory.dto.ProductDTO;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.Category;
import com.inventory.model.InventoryTransaction;
import com.inventory.model.Product;
import com.inventory.repository.CategoryRepository;
import com.inventory.repository.InventoryTransactionRepository;
import com.inventory.repository.ProductRepository;
import com.inventory.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private InventoryTransactionRepository inventoryTransactionRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private Category category;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1L)
                .name("Electronics")
                .build();

        product = Product.builder()
                .id(1L)
                .productCode("PRD-001")
                .name("Test Product")
                .description("Test Description")
                .purchasePrice(new BigDecimal("10.00"))
                .sellingPrice(new BigDecimal("15.00"))
                .currentQuantity(100)
                .reorderLevel(20)
                .category(category)
                .build();

        productDTO = ProductDTO.builder()
                .productCode("PRD-001")
                .name("Test Product")
                .description("Test Description")
                .purchasePrice(new BigDecimal("10.00"))
                .sellingPrice(new BigDecimal("15.00"))
                .currentQuantity(100)
                .reorderLevel(20)
                .categoryId(1L)
                .build();
    }

    @Test
    void createProduct_Success() {
        when(productRepository.existsByProductCode(anyString())).thenReturn(false);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.createProduct(productDTO);

        assertNotNull(savedProduct);
        assertEquals("PRD-001", savedProduct.getProductCode());
        assertEquals("Test Product", savedProduct.getName());
        verify(productRepository, times(1)).save(any(Product.class));
        verify(inventoryTransactionRepository, times(1)).save(any(InventoryTransaction.class));
    }

    @Test
    void createProduct_ProductCodeExists_ThrowsException() {
        when(productRepository.existsByProductCode(anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            productService.createProduct(productDTO);
        });

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void createProduct_CategoryNotFound_ThrowsException() {
        when(productRepository.existsByProductCode(anyString())).thenReturn(false);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.createProduct(productDTO);
        });

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void getProductById_Success() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(1L);

        assertNotNull(foundProduct);
        assertEquals(1L, foundProduct.getId());
        assertEquals("PRD-001", foundProduct.getProductCode());
    }

    @Test
    void getProductById_NotFound_ThrowsException() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductById(1L);
        });
    }

    @Test
    void getAllProducts_Success() {
        Product product2 = Product.builder()
                .id(2L)
                .productCode("PRD-002")
                .name("Another Product")
                .build();

        when(productRepository.findAll()).thenReturn(Arrays.asList(product, product2));

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
    }

    @Test
    void updateInventory_Success() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        boolean result = productService.updateInventory(1L, 10, "REF-001", "Test note");

        assertTrue(result);
        assertEquals(110, product.getCurrentQuantity());
        verify(productRepository, times(1)).save(product);
        verify(inventoryTransactionRepository, times(1)).save(any(InventoryTransaction.class));
    }

    @Test
    void updateInventory_NegativeQuantity_Fails() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        boolean result = productService.updateInventory(1L, -110, "REF-001", "Test note");

        assertFalse(result);
        assertEquals(100, product.getCurrentQuantity()); // Quantity unchanged
        verify(productRepository, never()).save(any(Product.class));
        verify(inventoryTransactionRepository, never()).save(any(InventoryTransaction.class));
    }
}