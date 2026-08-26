package com.web.urlShortener.domain.exceptions;



public class ShortUrlNotFoundException extends RuntimeException {
    

    // Constructor to pass a specific error message
    public ShortUrlNotFoundException(String message) {
        super(message);
    }
}
