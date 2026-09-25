package com.web.urlShortener.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.urlShortener.ApplicationProperties;
import com.web.urlShortener.domain.dtos.ShortUrlDto;
import com.web.urlShortener.domain.models.PagedResult;
import com.web.urlShortener.domain.services.ShortUrlService;

@Controller
@RequestMapping("/admin")

public class AdminController {

	private final ShortUrlService shortUrlService;
	private final ApplicationProperties property;

	public AdminController(ShortUrlService shortUrlService, ApplicationProperties property) {

		this.shortUrlService = shortUrlService;
		this.property = property;
	}

	@GetMapping("/dashboard")
	public String dashboard(@RequestParam(defaultValue = "1") Integer page, Model model) {
		PagedResult<ShortUrlDto> allUrls = shortUrlService.findAllShortUrls(page, property.pageSize());
		model.addAttribute("shortUrls", allUrls);
		model.addAttribute("baseUrl", property.baseUrl());
		model.addAttribute("paginationUrl", "/admin/dashboard");
		return "admin-dashboard";
	}

}
