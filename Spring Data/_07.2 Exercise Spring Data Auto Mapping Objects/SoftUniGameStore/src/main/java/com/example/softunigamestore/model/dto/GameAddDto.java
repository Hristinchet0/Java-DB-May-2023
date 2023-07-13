package com.example.softunigamestore.model.dto;

import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public class GameAddDto {

    @Pattern(regexp = "[A-Z][a-z]{6,100}", message = "Enter valid title. Title has to begin with an uppercase letter and must have length between 3 and 100 symbols")
    private String title;

    @DecimalMin(value = "0", message = "Enter valid price. Price must be positive number")
    private BigDecimal price;

    @Positive(message = "Enter valid size. Size must be positive number")
    private Double size;

    @Size(min = 11, max = 11, message = "Enter valid trailer. Only videos from YouTube are allowed. Only their ID, which is a string of exactly 11 characters")
    private String trailer;

    @Pattern(regexp = "(https?).+", message = "Enter valid URL. Thumbnail URL – it should be a plain text starting with http://, https:// ")
    private String thumbnailURL;

    @Size(min = 20, message = "Enter valid description. Description must be at least 20 symbols")
    private String description;

    private String releaseDate;

    public GameAddDto() {
    }

    public GameAddDto(String title, BigDecimal price, Double size, String trailer, String thumbnailURL, String description, String releaseDate) {
        this.title = title;
        this.price = price;
        this.size = size;
        this.trailer = trailer;
        this.thumbnailURL = thumbnailURL;
        this.description = description;
        this.releaseDate = releaseDate;
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

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
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
}
