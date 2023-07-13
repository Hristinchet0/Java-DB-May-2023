package com.example.softunigamestore.model.dto;

import java.math.BigDecimal;

public class DetailGameDto {

    private String title;

    private BigDecimal price;

    private String description;

    private String releaseDate;

    public DetailGameDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Title: %s", this.getTitle())).append(System.lineSeparator());
        sb.append(String.format("Price: %.2f", this.getPrice())).append(System.lineSeparator());
        sb.append(String.format("Description: %s", this.getDescription())).append(System.lineSeparator());
        sb.append(String.format("Release date: %s", this.getReleaseDate())).append(System.lineSeparator());

        return sb.toString();
    }
}
