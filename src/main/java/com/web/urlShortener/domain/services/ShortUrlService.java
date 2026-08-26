package com.web.urlShortener.domain.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.urlShortener.ApplicationProperties;
import com.web.urlShortener.domain.dtos.ShortUrlDto;
import com.web.urlShortener.domain.entities.ShortUrl;
import com.web.urlShortener.domain.mappers.ShortUrlMapper;
import com.web.urlShortener.domain.repositories.ShortUrlRepository;
import com.web.urlShortener.util.UrlExistenceValidator;


import static java.time.temporal.ChronoUnit.*;





@Service
@Transactional(readOnly = true)
public class ShortUrlService {
	
	private final ShortUrlRepository shortUrlRepository;
	private final ShortUrlMapper shortUrlMapper;
	private final ApplicationProperties properties;
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	public ShortUrlService(ShortUrlRepository shortUrlRepository,ShortUrlMapper shortUrlMapper,ApplicationProperties properties) {

		this.shortUrlRepository = shortUrlRepository;
		this.shortUrlMapper = shortUrlMapper;
		this.properties = properties;
	}
	
	 public static String generateRandomString(int length) {
	        Random random = new Random();
	        StringBuilder sb = new StringBuilder(length);

	        for (int i = 0; i < length; i++) {
	            int randomIndex = random.nextInt(CHARACTERS.length());
	            sb.append(CHARACTERS.charAt(randomIndex));
	        }

	        return sb.toString();
	    }
	
	public List<ShortUrlDto> findAllPublicShortUrls(){
		List<ShortUrl> urls =  shortUrlRepository.findPublicShortUrls();
		return shortUrlMapper.toShortUrlDto(urls);
			
	}
	
	
	
	@Transactional
	public ShortUrlDto createShortUrl(String originalUrl) {
		
		if(properties.validateOriginalUrl()) {
			boolean isUrlExistance = UrlExistenceValidator.isUrlExists(originalUrl);
			if(!isUrlExistance) {
				throw new IllegalArgumentException("URL does not exist: " + originalUrl);
			}
		}
		
		//TODO check url already exist
		
		String shortKeyString =  generateRandomString(8);
		ShortUrl shortUrl = new ShortUrl();
		shortUrl.setOriginalUrl(originalUrl);
		shortUrl.setShortKey(shortKeyString);
		shortUrl.setCreatedBy(null);
		shortUrl.setExpiresAt(Instant.now().plus(properties.defaultExpiryInDays(), DAYS));
		shortUrl.setCreatedAt(Instant.now());
		shortUrl.setIsPrivate(false);
		shortUrl.setClickCount(0L);
		shortUrlRepository.save(shortUrl);
		
		return shortUrlMapper.toShortUrlDto(shortUrl);
				
		
		
	}
	
	@Transactional
	public Optional<ShortUrlDto> redirectToOriginalUrl(String shortKey) {
		Optional<ShortUrl> shortUrlOptional = shortUrlRepository.findByShortKey(shortKey);
		if(shortUrlOptional.isEmpty()) {
			return Optional.empty();
		}
		ShortUrl shortUrl = shortUrlOptional.get();
		if(shortUrl.getExpiresAt() != null && shortUrl.getExpiresAt().isBefore(Instant.now())) {
			return Optional.empty();
		}
		shortUrl.setClickCount(shortUrl.getClickCount() + 1);
		shortUrlRepository.save(shortUrl);
		return shortUrlOptional.map(shortUrlMapper::toShortUrlDto);
	}
	
	

}
