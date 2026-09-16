package com.web.urlShortener.domain.dtos;

public record CreateShortUrlCmd(  String originalUrl,
	      Boolean isPrivate,
	      Integer expirationInDays,
	      Long userId) {
	
	
}
