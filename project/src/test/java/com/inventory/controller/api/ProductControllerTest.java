package com.inventory.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.dto.ProductDTO;
import com.inventory.model.Product;
import com.inventory.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@WithMockUser
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .productCode("PRD-001")
                .name("Test Product")
                .description("Test Description")
                .purchasePrice(new BigDecimal("10.00"))
                .sellingPrice(new BigDecimal("15.00"))
                .currentQuantity(100)
                .reorderLevel(20)
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
    void createProduct_ReturnsCreated() throws Exception {
        when(productService.createProduct(any(ProductDTO.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.productCode").value("PRD-001"))
                .andExpect(jsonPath("$.name").value("Test Product"));

        verify(productService, times(1)).createProduct(any(ProductDTO.class));
    }

    @Test
    void getAllProducts_ReturnsProducts() throws Exception {
        Product product2 = Product.builder()
                .id(2L)
                .productCode("PRD-002")
                .name("Another Product")
                .build();

        when(productService.getAllProducts()).thenReturn(Arrays.asList(product, product2));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].productCode").value("PRD-001"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].productCode").value("PRD-002"));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void getProductById_ReturnsProduct() throws Exception {
        when(productService.getProductById(anyLong())).thenReturn(product);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.productCode").value("PRD-001"));

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void updateProduct_ReturnsUpdatedProduct() throws Exception {
        when(productService.updateProduct(anyLong(), any(ProductDTO.class))).thenReturn(product);

        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.productCode").value("PRD-001"));

        verify(productService, times(1)).updateProduct(eq(1L), any(ProductDTO.class));
    }

    @Test
    void deleteProduct_ReturnsNoContent() throws Exception {
        doNothing().when(productService).deleteProduct(anyLong());

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(1L);
    }

    @Test
    void updateInventory_ReturnsSuccess() throws Exception {
        when(productService.updateInventory(anyLong(), anyInt(), anyString(), anyString())).thenReturn(true);

        mockMvc.perform(post("/api/products/1/inventory")
                .param("quantity", "10")
                .param("reference", "REF-001")
                .param("notes", "Test note"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(productService, times(1)).updateInventory(eq(1L), eq(10), eq("REF-001"), eq("Test note"));
    }

    @Test
    void updateInventory_FailsWithNegativeQuantity() throws Exception {
        when(productService.updateInventory(anyLong(), anyInt(), anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/api/products/1/inventory")
                .param("quantity", "-200")
                .param("reference", "REF-001")
                .param("notes", "Test note"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("false"));

        verify(productService, times(1)).updateInventory(eq(1L), eq(-200), eq("REF-001"), eq("Test note"));
    }
}