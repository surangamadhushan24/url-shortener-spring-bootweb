package com.web.urlShortener.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.web.urlShortener.domain.entities.User;
import com.web.urlShortener.domain.repositories.UserRepository;



@Service
public class SecurityUtils {
	
	private final UserRepository userRepository;
	
	public SecurityUtils(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication !=null && authentication.isAuthenticated()) {
			String email = authentication.getName();
			return userRepository.findByEmail(email).orElse(null);
		}
		return null;
	}
	
	public Long getCurrentUserId() {
		
		User currentUser = getCurrentUser();
		return currentUser != null ? currentUser.getId() : null;
	
	}

}
