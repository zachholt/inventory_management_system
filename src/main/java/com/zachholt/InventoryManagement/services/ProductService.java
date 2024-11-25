package com.zachholt.InventoryManagement.services;

import com.zachholt.InventoryManagement.models.Product;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class ProductService {
    List<Product> products = new ArrayList<>();

    public List<Product> getAllProducts(){
        return products;
    }

    public Product getProductById(String id){
        return  products
                .stream()
                .filter(product -> product.getProductId().toString().equals(id))
                .findFirst().orElse(null);
    }

    public Product addProduct(Product productRequest) {
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setSupplier(productRequest.getSupplier());
        product.setBrand(productRequest.getBrand());
        products.add(product);
        return product;
    }

    public Product updateProduct(String id, Product productRequest){
        Product productToUpdate = products.stream()
                .filter(product -> product.getProductId().toString().equals(id))
                .findFirst().orElse(null);
        if (productToUpdate != null) {
            productToUpdate.setName(productRequest.getName());
            productToUpdate.setPrice(productRequest.getPrice());
            productToUpdate.setQuantity(productRequest.getQuantity());
            productToUpdate.setSupplier(productRequest.getSupplier());
            productToUpdate.setBrand(productRequest.getBrand());
        }
        return productToUpdate;
    }

    public Product updateStock(String id, Product productRequest){
        Product productToUpdate = products.stream()
                .filter(product -> product.getProductId().toString().equals(id))
                .findFirst().orElse(null);
        if (productToUpdate != null) {
            productToUpdate.setQuantity(productRequest.getQuantity());
        }
        return productToUpdate;
    }

    public boolean deleteProduct(String id){
        Product productToDelete = products.stream()
                .filter(product -> product.getProductId().toString().equals(id))
                .findFirst().orElse(null);
        if (productToDelete != null) {
            products.remove(productToDelete);
            return true;
        }
        return false;
    }
}
