package com.web.urlShortener.domain.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.web.urlShortener.domain.entities.ShortUrl;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
	
	@Query("select su from ShortUrl su left join fetch su.createdBy where su.isPrivate = false")
	Page<ShortUrl> findPublicShortUrls(Pageable pageable);
	
	Page<ShortUrl> findByCreatedById(Long userId, Pageable pageable);

	@Query("select su from ShortUrl su left join fetch su.createdBy where su.shortKey = :shortKey")
	Optional<ShortUrl> findByShortKey(String shortKey);
	
	@Query("select u from ShortUrl u left join fetch u.createdBy")
    Page<ShortUrl> findAllShortUrls(Pageable pageable);
	
}
