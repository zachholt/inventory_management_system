package com.zachholt.InventoryManagement.controllers;

import com.zachholt.InventoryManagement.models.Product;
import com.zachholt.InventoryManagement.services.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id){
        logger.info("Received request to get product with ID: {}", id);
        Product product = productService.getProductById(id);
        if (product != null) {
            logger.info("Found product: {}", product);
            return ResponseEntity.ok(product);
        } else {
            logger.warn("Product not found with ID: {}", id);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(){
        logger.info("Received request to get all products");
        List<Product> products = productService.getAllProducts();
        logger.info("Retrieved {} products", products.size());
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product request, @PathVariable String id){
        logger.info("Received request to update product with ID: {}", id);
        Product updatedProduct = productService.updateProduct(id, request);
        logger.info("Successfully updated product: {}", updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Product> updateStock(@Valid @RequestBody Product request, @PathVariable String id){
        logger.info("Received request to update stock for product with ID: {}", id);
        Product updatedProduct = productService.updateStock(id, request);
        logger.info("Successfully updated stock for product: {}", updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product request) {
        logger.info("Received request to create new product: {}", request);
        Product newProduct = productService.addProduct(request);
        logger.info("Successfully created product: {}", newProduct);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable String id) {
        logger.info("Received request to delete product with ID: {}", id);
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            logger.info("Successfully deleted product with ID: {}", id);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            logger.warn("Product not found for deletion with ID: {}", id);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
