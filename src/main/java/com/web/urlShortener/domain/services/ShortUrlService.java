package com.web.urlShortener.domain.services;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.urlShortener.ApplicationProperties;
import com.web.urlShortener.domain.dtos.CreateShortUrlCmd;
import com.web.urlShortener.domain.dtos.ShortUrlDto;
import com.web.urlShortener.domain.entities.ShortUrl;
import com.web.urlShortener.domain.mappers.ShortUrlMapper;
import com.web.urlShortener.domain.models.PagedResult;
import com.web.urlShortener.domain.repositories.ShortUrlRepository;
import com.web.urlShortener.domain.repositories.UserRepository;
import com.web.urlShortener.util.UrlExistenceValidator;

import static java.time.temporal.ChronoUnit.*;

@Service
@Transactional(readOnly = true)
public class ShortUrlService {

	private final ShortUrlRepository shortUrlRepository;
	private final UserRepository userRepository;
	private final ShortUrlMapper shortUrlMapper;
	private final ApplicationProperties properties;
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	public ShortUrlService(ShortUrlRepository shortUrlRepository,UserRepository userRepository, ShortUrlMapper shortUrlMapper,
			ApplicationProperties properties) {

		this.shortUrlRepository = shortUrlRepository;
		this.userRepository = userRepository;
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

	public PagedResult<ShortUrlDto> findAllPublicShortUrls(int pageNo,int pageSize) {
		pageNo = pageNo < 1 ? 0 : pageNo -1;
		Pageable pageable = PageRequest.of(pageNo, pageSize,Sort.by(Sort.Direction.DESC, "createdAt"));
		Page<ShortUrlDto> shortUrlDtoPage = shortUrlRepository.findPublicShortUrls(pageable).map(shortUrlMapper::toShortUrlDto);
		return PagedResult.from(shortUrlDtoPage);
		
	}

	@Transactional
	public ShortUrlDto createShortUrl(CreateShortUrlCmd cmd) {

		if (properties.validateOriginalUrl()) {
			boolean isUrlExistance = UrlExistenceValidator.isUrlExists(cmd.originalUrl());
			if (!isUrlExistance) {
				throw new IllegalArgumentException("URL does not exist: " + cmd.originalUrl());
			}
		}

		String shortKeyString = generateRandomString(8);
		ShortUrl shortUrl = new ShortUrl();
		shortUrl.setOriginalUrl(cmd.originalUrl());
		shortUrl.setShortKey(shortKeyString);
		if (cmd.userId() == null) {
			shortUrl.setCreatedBy(null);
			shortUrl.setIsPrivate(false);
			shortUrl.setExpiresAt(Instant.now().plus(properties.defaultExpiryInDays(), DAYS));
		} else {
			shortUrl.setCreatedBy(userRepository.findById(cmd.userId()).orElseThrow());
			shortUrl.setIsPrivate(cmd.isPrivate() != null && cmd.isPrivate());
			shortUrl.setExpiresAt(
					cmd.expirationInDays() != null ? Instant.now().plus(cmd.expirationInDays(), DAYS) : null);
		}

		shortUrl.setClickCount(0L);
		shortUrl.setCreatedAt(Instant.now());
		shortUrlRepository.save(shortUrl);

		return shortUrlMapper.toShortUrlDto(shortUrl);

	}

	@Transactional
	public Optional<ShortUrlDto> redirectToOriginalUrl(String shortKey,Long userId) {
		Optional<ShortUrl> shortUrlOptional = shortUrlRepository.findByShortKey(shortKey);
		if (shortUrlOptional.isEmpty()) {
			return Optional.empty();
		}
		ShortUrl shortUrl = shortUrlOptional.get();
		if (shortUrl.getExpiresAt() != null && shortUrl.getExpiresAt().isBefore(Instant.now())) {
			return Optional.empty();
		}
		
		if(shortUrl.getIsPrivate() != null && shortUrl.getCreatedBy() != null  && !Objects.equals(shortUrl.getCreatedBy().getId(), userId)) {
            return Optional.empty();
		}
		
		shortUrl.setClickCount(shortUrl.getClickCount() + 1);
		shortUrlRepository.save(shortUrl);
		return shortUrlOptional.map(shortUrlMapper::toShortUrlDto);
	}

}
