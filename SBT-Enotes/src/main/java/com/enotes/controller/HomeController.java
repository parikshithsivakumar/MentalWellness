package com.enotes.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.enotes.entity.UserDtls;
import com.enotes.repository.UserRepository;

import com.enotes.service.WordleService;
import com.enotes.web.WebResult;

@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder passwordEncode;

	@Autowired
	private WordleService wordleService;

	@Autowired
	private UserRepository userRepo;

	@GetMapping("/")
	public String home() {
		return "home";
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
	public String saveUser(@ModelAttribute UserDtls user, Model m, HttpSession session) {

		user.setPassword(passwordEncode.encode(user.getPassword()));
		user.setRole("ROLE_USER");

		UserDtls u = userRepo.save(user);

		if (u != null) {
			session.setAttribute("msg", "Registration Successfully");
		} else {
			session.setAttribute("msg", "Something wrong on server");
		}

		return "redirect:/signup";
	}

	private List<String> wordleAttempts = new ArrayList<>();

	@GetMapping("/wordle")
	public String wordle(Model model) {
		showWordleAttempts(model);
		return "WordleGuess"; // Assuming you have a WordleGuess template
	}

	@PostMapping("/wordle")
	public String submitWordleGuess(String guess, Model model) {
		String error = wordleService.validate(guess);
		if (error != null) {
			model.addAttribute("errorMessage", error);
		} else {
			model.addAttribute("errorMessage", "");
			wordleAttempts.add(guess);
		}
		showWordleAttempts(model);

		return "WordleGuess"; // Assuming you have a WordleGuess template
	}

	private void showWordleAttempts(Model model) {
		List<WebResult[]> results = wordleAttempts.stream()
				.map(str -> WebResult.create(wordleService.calculateResults(str), str))
				.collect(Collectors.toList());
		model.addAttribute("wordleEntries", results);
	}

}
