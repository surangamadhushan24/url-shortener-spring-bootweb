package com.web.urlShortener.domain.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.urlShortener.ApplicationProperties;
import com.web.urlShortener.domain.dtos.CreateShortUrlForm;
import com.web.urlShortener.domain.dtos.ShortUrlDto;
import com.web.urlShortener.domain.entities.User;
import com.web.urlShortener.domain.services.ShortUrlService;
import com.web.urlShortener.exceptions.ShortUrlNotFoundException;
import com.web.urlShortener.util.SecurityUtils;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	private final ShortUrlService shortUrlService;
	private final ApplicationProperties property;
	private final SecurityUtils securityUtils;

	public HomeController(ShortUrlService shortUrlService,ApplicationProperties property,SecurityUtils securityUtils) {
		this.shortUrlService = shortUrlService;
		this.property = property;
		this.securityUtils =securityUtils ;
	}

	@GetMapping("/")
	public String home(Model model) {
		
		User currentUser = securityUtils.getCurrentUser();
		
		List<ShortUrlDto> shortUrls = shortUrlService.findAllPublicShortUrls();
		model.addAttribute("shortUrls", shortUrls);
		model.addAttribute("baseUrl", "http://localhost:8080");
		model.addAttribute("createShortUrlForm", new CreateShortUrlForm(""));
		return "index";
	}

	@PostMapping("/short-urls")
    public String createShortUrl(@ModelAttribute("createShortUrlForm") @Valid CreateShortUrlForm form,
    		BindingResult bindingResult,
    		RedirectAttributes redirectAttributes,
    		Model model) {
    	
    	if(bindingResult.hasErrors()) {
    		 List<ShortUrlDto> shortUrls = shortUrlService.findAllPublicShortUrls();
    		 model.addAttribute("shortUrls", shortUrls);
		     model.addAttribute("baseUrl", "http://localhost:8080");     
		     return "index";
		     
    	}
    	
    	try {
    		var shortUrl = shortUrlService.createShortUrl(form.getOriginalUrl());
    		redirectAttributes.addFlashAttribute("successMessage", "Short URL created successfully!"+ property.baseUrl() + "/s/" + shortUrl.shortKey());
    	    	
    	}
    	catch(Exception ex){
    		  redirectAttributes.addFlashAttribute("errorMessage", "Short URL created failed!");
    		 
    	}
    	return "redirect:/";
    		    
	}
	
	
	@GetMapping("/s/{shortKey}")
	public String redirectToOriginalUrl(@PathVariable String shortKey)  {
		Optional<ShortUrlDto> shortUrlOptional= shortUrlService.redirectToOriginalUrl(shortKey);
		if(shortUrlOptional.isEmpty()) {
			throw new ShortUrlNotFoundException("Short URL not found for key: " + shortKey);
		}
		
		return "redirect:" + shortUrlOptional.get().originalUrl();
		
		
	}
	
	 @GetMapping("/login")
	    String loginForm() {
	        return "login";
	    }
    
   

}
