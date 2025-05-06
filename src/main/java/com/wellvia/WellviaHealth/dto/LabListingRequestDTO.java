package com.wellvia.WellviaHealth.dto;

public class LabListingRequestDTO {
    private String city;
    private String state;
    private String ratingSort; // "ASC" or "DESC"
    private String priceSort;  // "ASC" or "DESC"
    private Integer page = 0;
    private Integer size = 10;

    // Getters and Setters
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRatingSort() {
        return ratingSort;
    }

    public void setRatingSort(String ratingSort) {
        this.ratingSort = ratingSort;
    }

    public String getPriceSort() {
        return priceSort;
    }

    public void setPriceSort(String priceSort) {
        this.priceSort = priceSort;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
} 