package com.web.urlShortener.exceptions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;





@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger  logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(ShortUrlNotFoundException.class)
	String handleShortUrlNotFoundException(ShortUrlNotFoundException ex) {
		logger.error("Short URL not found: " + ex.getMessage());
		return "error/404";
	}
	
	@ExceptionHandler(Exception.class)
	String handleGenericException(Exception ex) {
		logger.error("An error occurred: " + ex.getMessage());
		return "error/500";
	}

}
