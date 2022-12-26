package com.searchengine.dto;

public class IndexEntryDto {
    private String index;
    private Double searchScore;

    public IndexEntryDto() {}

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Double getSearchScore() {
        return searchScore;
    }

    public void setSearchScore(Double searchScore) {
        this.searchScore = searchScore;
    }
}
