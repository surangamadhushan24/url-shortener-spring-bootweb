package com.web.urlShortener.domain.mappers;

import org.mapstruct.Mapper;

import com.web.urlShortener.domain.dtos.UserDto;
import com.web.urlShortener.domain.entities.User;



@Mapper(componentModel = "spring")
public interface UserMapper {
	
	UserDto toDto(User user);
	
	
	
}
