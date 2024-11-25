package com.zachholt.InventoryManagement.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class Product {
    private UUID productId;
    @NotNull
    @Size(min = 3, max = 30)
    private String name;
    @NotNull
    private Double price;
    @NotNull
    private int quantity;
    @NotNull
    private String supplier;
    @NotNull
    private String brand;

    public UUID getProductId() {
        return productId;
    }

    public Product setProductId(@NotNull UUID productId) {
        this.productId = productId;
        return this;
    }

    public @NotNull @Size(min = 3, max = 30) String getName() {
        return name;
    }

    public Product setName(@NotNull @Size(min = 3, max = 30) String name) {
        this.name = name;
        return this;
    }

    public @NotNull Double getPrice() {
        return price;
    }

    public Product setPrice(@NotNull Double price) {
        this.price = price;
        return this;
    }

    @NotNull
    public int getQuantity() {
        return quantity;
    }

    public Product setQuantity(@NotNull int quantity) {
        this.quantity = quantity;
        return this;
    }

    public @NotNull String getSupplier() {
        return supplier;
    }

    public Product setSupplier(@NotNull String supplier) {
        this.supplier = supplier;
        return this;
    }

    public @NotNull String getBrand() {
        return brand;
    }

    public Product setBrand(@NotNull String brand) {
        this.brand = brand;
        return this;
    }
}
