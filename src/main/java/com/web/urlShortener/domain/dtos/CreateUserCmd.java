package com.web.urlShortener.domain.dtos;



import com.web.urlShortener.domain.models.Role;

public record CreateUserCmd(
		String userName,
		String password,
		String email,
		Role role) {

}
