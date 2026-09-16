package com.web.urlShortener.domain.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateShortUrlForm {

    @NotBlank(message = "Original URL is required")
    @URL(message = "Please enter a valid URL")
    private String originalUrl;
    private Boolean isPrivate;
    @Min(1)
    @Max(365)
    private Integer expirationInDays;

   
}