package com.web.urlShortener.domain.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.web.urlShortener.domain.dtos.ShortUrlDto;
import com.web.urlShortener.domain.entities.ShortUrl;
import com.web.urlShortener.domain.mappers.ShortUrlMapper;
import com.web.urlShortener.domain.repositories.ShortUrlRepository;





@Service
public class ShortUrlService {
	
	private final ShortUrlRepository shortUrlRepository;
	private final ShortUrlMapper shortUrlMapper;

	public ShortUrlService(ShortUrlRepository shortUrlRepository,ShortUrlMapper shortUrlMapper) {

		this.shortUrlRepository = shortUrlRepository;
		this.shortUrlMapper = shortUrlMapper;
	}
	
	public List<ShortUrlDto> findAllPublicShortUrls(){
		List<ShortUrl> urls =  shortUrlRepository.findPublicShortUrls();
		return shortUrlMapper.toShortUrlDto(urls);
			
	}
	
	

}
