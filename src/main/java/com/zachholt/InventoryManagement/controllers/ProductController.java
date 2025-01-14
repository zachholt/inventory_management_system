package com.zachholt.InventoryManagement.controllers;

import com.zachholt.InventoryManagement.models.Product;
import com.zachholt.InventoryManagement.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product APIs")
public class ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    @Operation(
            description = "Returns product by the supplied ID",
            summary = "Get a specific product by its ID",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Product not found",
                            responseCode = "404"
                    )
            }
    )
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
    @Operation(
            description = "Returns all available products",
            summary = "Get a list of all products in the inventory",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)
                            )
                    )
            }
    )
    public ResponseEntity<List<Product>> getAllProducts(){
        logger.info("Received request to get all products");
        List<Product> products = productService.getAllProducts();
        logger.info("Retrieved {} products", products.size());
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    @Operation(
            description = "Allows the user to update a product with the supplied data",
            summary = "Update an existing product's information",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            description = "Product updated successfully",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Invalid request body",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Product not found",
                            responseCode = "404"
                    )
            }
    )
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product request, @PathVariable String id){
        logger.info("Received request to update product with ID: {}", id);
        Product updatedProduct = productService.updateProduct(id, request);
        logger.info("Successfully updated product: {}", updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/{id}/stock")
    @Operation(
            description = "Allows the user to update only the quantity of a product and leaves the rest of the data the same",
            summary = "Update a product's stock quantity",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    name = "StockUpdate",
                                    requiredProperties = {"quantity"}
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            description = "Stock updated successfully",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Invalid quantity",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Product not found",
                            responseCode = "404"
                    )
            }
    )
    public ResponseEntity<Product> updateStock(@Valid @RequestBody Product request, @PathVariable String id){
        logger.info("Received request to update stock for product with ID: {}", id);
        Product updatedProduct = productService.updateStock(id, request);
        logger.info("Successfully updated stock for product: {}", updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @PostMapping()
    @Operation(
            description = "Allows the user to create a product with the supplied data",
            summary = "Create a new product in the inventory",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            description = "Product created successfully",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Invalid request body",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product request) {
        logger.info("Received request to create new product: {}", request);
        Product newProduct = productService.addProduct(request);
        logger.info("Successfully created product: {}", newProduct);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(
            description = "Allows the user to delete a product from inventory",
            summary = "Delete a product from the inventory",
            responses = {
                    @ApiResponse(
                            description = "Product deleted successfully",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Product not found",
                            responseCode = "404"
                    )
            }
    )
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
