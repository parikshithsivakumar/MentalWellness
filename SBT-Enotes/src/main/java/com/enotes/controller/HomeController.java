package com.enotes.controller;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.enotes.entity.UserDtls;
import com.enotes.repository.UserRepository;
import com.enotes.service.WordleService;
import com.enotes.web.WebResult;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final int MAX_PASSWORD_LENGTH = 128;
	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private WordleService wordleService;

	@Autowired
	private UserRepository userRepo;

	@GetMapping("/")
	public String home() {
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute UserDtls user, HttpSession session) {
		logger.debug("User registration attempt: {}", user.getEmail());

		String validationError = validateUserInput(user);
		if (validationError != null) {
			logger.warn("User registration validation failed: {}", validationError);
			session.setAttribute("msg", validationError);
			return "redirect:/signup";
		}

		UserDtls existingUser = userRepo.findByEmail(user.getEmail());
		if (existingUser != null) {
			logger.warn("Registration attempt with existing email: {}", user.getEmail());
			session.setAttribute("msg", "Email already registered");
			return "redirect:/signup";
		}

		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRole("ROLE_USER");
			user.setEnabled(true);

			UserDtls savedUser = userRepo.save(user);

			if (savedUser != null) {
				logger.info("User registered successfully: {}", savedUser.getEmail());
				session.setAttribute("msg", "Registration successful. Please log in.");
			} else {
				logger.error("Failed to save user during registration");
				session.setAttribute("msg", "Registration failed. Please try again.");
			}
		} catch (Exception ex) {
			logger.error("Error during user registration", ex);
			session.setAttribute("msg", "An error occurred during registration. Please try again.");
		}

		return "redirect:/signup";
	}

	@GetMapping("/user/wordle")
	public String showWordle(HttpSession session, Model model) {
		// Initialize game state for this session if needed
		@SuppressWarnings("unchecked")
		List<String> attempts = (List<String>) session.getAttribute("wordleAttempts");
		if (attempts == null) {
			attempts = new ArrayList<>();
			session.setAttribute("wordleAttempts", attempts);
		}
		
		// Always initialize entries with empty list as default
		model.addAttribute("entries", new ArrayList<>());
		model.addAttribute("errorMessage", "");
		
		// Display the current game state
		displayWordleGame(model, attempts);
		return "user/WordleGuess";
	}

	@PostMapping("/user/wordle")
	public String submitWordleGuess(String guess, HttpSession session, Model model) {
		@SuppressWarnings("unchecked")
		List<String> attempts = (List<String>) session.getAttribute("wordleAttempts");
		if (attempts == null) {
			attempts = new ArrayList<>();
			session.setAttribute("wordleAttempts", attempts);
		}
		
		if (guess != null) {
			guess = guess.trim();
		}
		
		String error = wordleService.validate(guess);
		if (error != null) {
			model.addAttribute("errorMessage", error);
		} else {
			attempts.add(guess);
			model.addAttribute("errorMessage", "");
		}
		
		// Always initialize entries with empty list as default
		model.addAttribute("entries", new ArrayList<>());
		
		// Display the current game state
		displayWordleGame(model, attempts);
		return "user/WordleGuess";
	}

	@GetMapping("/wordle")
	public String redirectToUserWordle() {
		return "redirect:/user/wordle";
	}

	private void displayWordleGame(Model model, List<String> attempts) {
		List<WebResult[]> results = attempts.stream()
				.map(str -> WebResult.create(wordleService.calculateResults(str), str))
				.collect(Collectors.toList());
		model.addAttribute("entries", results);
	}

	private String validateUserInput(UserDtls user) {
		if (user == null) {
			return "Invalid user data";
		}

		if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
			return "Email is required";
		}

		if (!user.getEmail().matches(EMAIL_REGEX)) {
			return "Please enter a valid email address";
		}

		if (user.getPassword() == null || user.getPassword().isEmpty()) {
			return "Password is required";
		}

		if (user.getPassword().length() < MIN_PASSWORD_LENGTH || user.getPassword().length() > MAX_PASSWORD_LENGTH) {
			return "Password must be between " + MIN_PASSWORD_LENGTH + " and " + MAX_PASSWORD_LENGTH + " characters";
		}

		if (user.getName() == null || user.getName().trim().isEmpty()) {
			return "Name is required";
		}

		if (user.getName().trim().length() < 2) {
			return "Name must be at least 2 characters";
		}

		return null;
	}
}

