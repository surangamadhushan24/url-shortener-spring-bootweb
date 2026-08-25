package com.web.urlShortener.domain.services;

import java.time.Instant;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.web.urlShortener.ApplicationProperties;
import com.web.urlShortener.domain.dtos.ShortUrlDto;
import com.web.urlShortener.domain.entities.ShortUrl;
import com.web.urlShortener.domain.mappers.ShortUrlMapper;
import com.web.urlShortener.domain.repositories.ShortUrlRepository;
import static java.time.temporal.ChronoUnit.*;





@Service
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

	public ShortUrlDto createShortUrl(String originalUrl) {
		
		//TODO check url already exist
		//TODO check url is valid
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
	
	

}
