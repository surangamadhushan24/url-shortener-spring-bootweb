package com.web.urlShortener.domain.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.web.urlShortener.domain.entities.ShortUrl;
import com.web.urlShortener.domain.repositories.ShortUrlRepository;



@Service
public class ShortUrlService {
	
	private final ShortUrlRepository shortUrlRepository;

	public ShortUrlService(ShortUrlRepository shortUrlRepository) {

		this.shortUrlRepository = shortUrlRepository;
	}
	
	public List<ShortUrl> findAllPublicShortUrls(){
		return shortUrlRepository.findPublicShortUrls();
	}
	
	
	
	

}
