package com.zachholt.inventoryManagement.services;

import com.zachholt.InventoryManagement.models.Product;
import com.zachholt.InventoryManagement.services.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("Product Service Test")
public class ProductServiceTest {

    @InjectMocks
    private ProductService subject;

    @Test
    @DisplayName("Get All Products - Empty List")
    void test_getAllProducts_empty() {
        // when
        List<Product> result = subject.getAllProducts();

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Add and Get Product")
    void test_addAndGetProduct() {
        // given
        Product newProduct = new Product();
        newProduct.setName("Test Product");
        newProduct.setPrice(19.99);
        newProduct.setQuantity(100);
        newProduct.setSupplier("Test Supplier");
        newProduct.setBrand("Test Brand");

        // when
        Product savedProduct = subject.addProduct(newProduct);

        // then
        assertThat(savedProduct.getProductId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo(newProduct.getName());
        assertThat(savedProduct.getPrice()).isEqualTo(newProduct.getPrice());
        assertThat(savedProduct.getQuantity()).isEqualTo(newProduct.getQuantity());
        assertThat(savedProduct.getSupplier()).isEqualTo(newProduct.getSupplier());
        assertThat(savedProduct.getBrand()).isEqualTo(newProduct.getBrand());

        // when
        Product retrievedProduct = subject.getProductById(savedProduct.getProductId().toString());

        // then
        assertThat(retrievedProduct).isNotNull();
        assertThat(retrievedProduct.getProductId()).isEqualTo(savedProduct.getProductId());
    }

    @Test
    @DisplayName("Update Product")
    void test_updateProduct() {
        // given
        Product product = subject.addProduct(createTestProduct());
        Product updateRequest = createTestProduct();
        updateRequest.setName("Updated Name");
        updateRequest.setPrice(29.99);

        // when
        Product updatedProduct = subject.updateProduct(product.getProductId().toString(), updateRequest);

        // then
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getName()).isEqualTo("Updated Name");
        assertThat(updatedProduct.getPrice()).isEqualTo(29.99);
    }

    @Test
    @DisplayName("Delete Product")
    void test_deleteProduct() {
        // given
        Product product = subject.addProduct(createTestProduct());

        // when
        boolean deleteResult = subject.deleteProduct(product.getProductId().toString());

        // then
        assertThat(deleteResult).isTrue();
        assertThat(subject.getProductById(product.getProductId().toString())).isNull();
    }

    @Test
    @DisplayName("Delete Product - Not Found")
    void test_deleteProduct_notFound() {
        // when
        boolean result = subject.deleteProduct(UUID.randomUUID().toString());

        // then
        assertThat(result).isFalse();
    }

    private Product createTestProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(19.99);
        product.setQuantity(100);
        product.setSupplier("Test Supplier");
        product.setBrand("Test Brand");
        return product;
    }
} 