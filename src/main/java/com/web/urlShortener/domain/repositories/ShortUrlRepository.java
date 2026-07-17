package com.web.urlShortener.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.urlShortener.domain.entities.ShortUrl;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
	
	@Query("select su from ShortUrl su where su.isPrivate = false order by su.createdAt desc")
	List<ShortUrl> findPublicShortUrls();
	
}
