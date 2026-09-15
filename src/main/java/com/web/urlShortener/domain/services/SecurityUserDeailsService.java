package com.web.urlShortener.domain.services;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.web.urlShortener.domain.entities.User;
import com.web.urlShortener.domain.repositories.UserRepository;


@Service
public class SecurityUserDeailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	public SecurityUserDeailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		User user =  userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
		
	
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(),
				user.getPassword(),
				List.of(new SimpleGrantedAuthority(user.getRole().name()))
			);
	}
}


