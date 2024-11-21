package com.example.demo.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public class VideoGame {
    private UUID id;
    @NotNull
    @Size(min = 3, max = 30)
    private String name;
    private String rating;
    private String genre;
    private List<String> platforms;
    private Boolean isOnline;
    private int hoursToBeat;
    private int price;

    public UUID getId() {
        return id;
    }

    public VideoGame setId(UUID id) {
        this.id = id;
        return this;
    }

    public @NotNull @Size(min = 3, max = 30) String getName() {
        return name;
    }

    public VideoGame setName(@NotNull @Size(min = 3, max = 30) String name) {
        this.name = name;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public VideoGame setRating(String rating) {
        this.rating = rating;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public VideoGame setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public VideoGame setPlatforms(List<String> platforms) {
        this.platforms = platforms;
        return this;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public VideoGame setOnline(Boolean online) {
        isOnline = online;
        return this;
    }

    public int getHoursToBeat() {
        return hoursToBeat;
    }

    public VideoGame setHoursToBeat(int hoursToBeat) {
        this.hoursToBeat = hoursToBeat;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public VideoGame setPrice(int price) {
        this.price = price;
        return this;
    }
}
