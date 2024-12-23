package com.zachholt.inventoryManagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zachholt.InventoryManagement.controllers.ProductController;
import com.zachholt.InventoryManagement.models.Product;
import com.zachholt.InventoryManagement.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(MockitoExtension.class)
@DisplayName("Product Controller Test")
public class ProductControllerTest {
    @InjectMocks
    private ProductController subject;

    @Mock
    ProductService productService;

    private MockMvc mockMvc;
    private HttpHeaders httpHeaders;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        ReflectionTestUtils.setField(this, "httpHeaders", httpHeaders);
        ReflectionTestUtils.setField(this, "objectMapper", new ObjectMapper());
    }

    @Test
    @DisplayName("Get Products - Success")
    void getProducts() throws Exception {
        //given
        //when(productService.getAllProducts()).thenReturn(new Product);

        //when
        MockHttpServletResponse resp = mockMvc.perform(
                get("/api/v1/products")
                        .headers(httpHeaders)
        ).andReturn().getResponse();

        //then
        assertThat(resp).isNotNull();
        assertThat(resp.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Get Product By ID - Success")
    void getProduct_success() throws Exception {
        //given
        Product mockProduct = new Product();
        mockProduct.setProductId(UUID.randomUUID());
        when(productService.getProductById(any())).thenReturn(mockProduct);

        // when
        MockHttpServletResponse resp = mockMvc.perform(
                get("/api/v1/products/{id}", mockProduct.getProductId())
                        .headers(httpHeaders)
        ).andReturn().getResponse();


        // then
        assertThat(resp).isNotNull();
        assertThat(resp.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertTrue(resp.getContentAsString().contains(mockProduct.getProductId().toString()));
    }

    @Test
    @DisplayName("Get Product By ID - Failed")
    void getProduct_failed() throws Exception {
        //given
        UUID randomId = UUID.randomUUID();
        
        // when
        MockHttpServletResponse resp = mockMvc.perform(
                get("/api/v1/products/{id}", randomId.toString())
                        .headers(httpHeaders)
        ).andReturn().getResponse();

        // then
        assertThat(resp).isNotNull();
        assertThat(resp.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Create Product - Success")
    void createProduct_success() throws Exception {
        // given
        Product newProduct = new Product();
        newProduct.setProductId(UUID.randomUUID());
        newProduct.setName("Test Product");
        newProduct.setPrice(19.99);
        newProduct.setQuantity(100);
        newProduct.setSupplier("Test Supplier");
        newProduct.setBrand("Test Brand");
        newProduct.setBrand("Test Brand");

        Product savedProduct = new Product();
        savedProduct.setProductId(UUID.randomUUID());
        savedProduct.setName(newProduct.getName());
        savedProduct.setPrice(newProduct.getPrice());
        savedProduct.setSupplier(newProduct.getSupplier());
        savedProduct.setBrand(newProduct.getBrand());
        savedProduct.setQuantity(newProduct.getQuantity());

        when(productService.addProduct(any(Product.class))).thenReturn(savedProduct);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                post("/api/v1/products")
                        .headers(httpHeaders)
                        .content(objectMapper.writeValueAsString(newProduct))
        ).andReturn().getResponse();

        // then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertTrue(response.getContentAsString().contains(savedProduct.getProductId().toString()));
    }

    @Test
    @DisplayName("Update Product - Success")
    void updateProduct_success() throws Exception {
        // given
        UUID productId = UUID.randomUUID();
        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Product");
        updatedProduct.setPrice(29.99);
        updatedProduct.setQuantity(50);
        updatedProduct.setSupplier("Test Supplier");
        updatedProduct.setBrand("Test Brand");
        
        when(productService.updateProduct(any(String.class), any(Product.class))).thenReturn(updatedProduct);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                put("/api/v1/products/{id}", productId.toString())
                        .headers(httpHeaders)
                        .content(objectMapper.writeValueAsString(updatedProduct))
        ).andReturn().getResponse();

        // then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertTrue(response.getContentAsString().contains(updatedProduct.getName()));
    }

    @Test
    @DisplayName("Delete Product - Success")
    void deleteProduct_success() throws Exception {
        // given
        UUID productId = UUID.randomUUID();
        when(productService.deleteProduct(any(String.class))).thenReturn(true);
        // when
        MockHttpServletResponse response = mockMvc.perform(
                delete("/api/v1/products/{id}", productId.toString())
                        .headers(httpHeaders)
        ).andReturn().getResponse();

        // then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Create Product - Bad Request")
    void createProduct_badRequest() throws Exception {
        // given
        Product invalidProduct = new Product();

        // when
        MockHttpServletResponse response = mockMvc.perform(
                post("/api/v1/products")
                        .headers(httpHeaders)
                        .content(objectMapper.writeValueAsString(invalidProduct))
        ).andReturn().getResponse();

        // then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
