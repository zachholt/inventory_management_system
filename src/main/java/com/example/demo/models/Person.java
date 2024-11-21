package com.example.demo.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public class Person {
    private UUID id;
    @NotNull
    @Size(min = 3, max = 30)
    private String name;
    private String jobTitle;
    private List<String> crimesCommitted;
    private Boolean talented;
    private Boolean leftHanded;
    @Max(1)
    private int favoriteNumber;

    public UUID getId() {
        return id;
    }

    public Person setId(UUID id) {
        this.id = id;
        return this;
    }

    public @NotNull @Size(min = 3, max = 30) String getName() {
        return name;
    }

    public Person setName(@NotNull @Size(min = 3, max = 30) String name) {
        this.name = name;
        return this;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Person setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public List<String> getCrimesCommitted() {
        return crimesCommitted;
    }

    public Person setCrimesCommitted(List<String> crimesCommitted) {
        this.crimesCommitted = crimesCommitted;
        return this;
    }

    public Boolean getTalented() {
        return talented;
    }

    public Person setTalented(Boolean talented) {
        this.talented = talented;
        return this;
    }

    public Boolean getLeftHanded() {
        return leftHanded;
    }

    public Person setLeftHanded(Boolean leftHanded) {
        this.leftHanded = leftHanded;
        return this;
    }

    @Max(1)
    public int getFavoriteNumber() {
        return favoriteNumber;
    }

    public Person setFavoriteNumber(@Max(1) int favoriteNumber) {
        this.favoriteNumber = favoriteNumber;
        return this;
    }
}
