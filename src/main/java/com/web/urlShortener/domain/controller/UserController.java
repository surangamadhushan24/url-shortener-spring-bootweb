package com.web.urlShortener.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.urlShortener.domain.dtos.CreateUserCmd;
import com.web.urlShortener.domain.dtos.RegisterUserRequest;
import com.web.urlShortener.domain.models.Role;
import com.web.urlShortener.domain.services.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute("user", new RegisterUserRequest("", "", ""));
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") @Valid RegisterUserRequest registerUserRequest,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

		if (bindingResult.hasErrors()) {
			return "register";
		}

		try {

			CreateUserCmd cmd = new CreateUserCmd(registerUserRequest.email(), registerUserRequest.password(),
					registerUserRequest.name(), Role.ROLE_USER);

			userService.createUser(cmd);
			redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please login.");
			return "redirect:/login";

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Registration failed: " + e.getMessage());
			return "redirect:/register";

		}
		

	}

}
