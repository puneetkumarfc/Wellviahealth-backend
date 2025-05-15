package com.wellvia.WellviaHealth.dto;

import lombok.Data;

@Data
public class SpecializationListingRequestDTO {
    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}