package com.web.urlShortener.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class CreateShortUrlForm {

    @NotBlank(message = "Original URL is required")
    @URL(message = "Please enter a valid URL")
    private String originalUrl;

    public CreateShortUrlForm() {
    }

    public CreateShortUrlForm(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}