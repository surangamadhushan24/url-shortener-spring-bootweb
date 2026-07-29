package com.web.urlShortener.domain.dtos;

import java.io.Serializable;

public record UserDto(
		Long id,
		String email,
		String name) implements Serializable {

}
