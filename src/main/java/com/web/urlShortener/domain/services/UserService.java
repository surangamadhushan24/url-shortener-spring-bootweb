package com.web.urlShortener.domain.services;

import java.time.Instant;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.urlShortener.domain.dtos.CreateUserCmd;
import com.web.urlShortener.domain.entities.User;
import com.web.urlShortener.domain.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void createUser(CreateUserCmd cmd) {

		if (userRepository.existsByEmail(cmd.email())) {
			throw new RuntimeException("Email already exists");
		}

		var user = new User();
		user.setEmail(cmd.email());
		user.setPassword(passwordEncoder.encode(cmd.password()));
		user.setName(cmd.userName());
		user.setRole(cmd.role());
		user.setCreatedAt(Instant.now());
		userRepository.save(user);

	}

}
