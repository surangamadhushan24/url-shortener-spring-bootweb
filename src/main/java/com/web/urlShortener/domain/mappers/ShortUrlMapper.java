package com.web.urlShortener.domain.mappers;


import java.util.List;

import org.mapstruct.Mapper;

import com.web.urlShortener.domain.dtos.ShortUrlDto;
import com.web.urlShortener.domain.entities.ShortUrl;



@Mapper(componentModel = "spring")
public interface ShortUrlMapper {
	
	List<ShortUrlDto>  toShortUrlDto(List<ShortUrl> url);
	ShortUrlDto toShortUrlDto(ShortUrl url);
	
}
