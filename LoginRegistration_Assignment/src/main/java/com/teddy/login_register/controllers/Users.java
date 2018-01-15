package com.teddy.login_register.controllers;

import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.teddy.login_register.models.User;
import com.teddy.login_register.services.UserService;
import com.teddy.login_register.validations.UserValidator;

@Controller
public class Users {

	private UserService userService;
	private UserValidator userValidator;

	public Users(UserService userService, UserValidator userValidator) {
		super();
		this.userService = userService;
		this.userValidator = userValidator;
	}
	

	// log in or logout

	@RequestMapping("/login")
	public String login(@Valid @ModelAttribute("user") User user,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {
		if (error != null) {
			model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
		}
		if (logout != null) {
			model.addAttribute("logoutMessage", "Your have Logout!");
		}
		return "login_or_register.jsp";
	}

	// register new users
	
	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model,
			HttpSession session) {
		userValidator.validate(user, result);
		if (result.hasErrors()) {
			return "login_or_register.jsp";
		}
		session.setAttribute("created", "You account is created. Please log in.");
		userService.saveUserWithAdminRole(user);
		return "redirect:/login";
	}

	// record date and time when logged in
	
	@RequestMapping("/loggedin")
	public String updatedLoggedIn(Principal principal, HttpSession session) {
		
		session.removeAttribute("created");
		String email = principal.getName();
		userService.recordDateWhenLoggedIn(userService.findByEmail(email));
		System.out.println("logged in");
		return "redirect:/";
	}

	// redirect to user home page when logged in
	
	@RequestMapping("/")
	public String userPage(Principal principal, Model model) {
		String email = principal.getName();
		model.addAttribute("currentUser", userService.findByEmail(email));
		return "userPage.jsp";
	}

	// redirect to Admin page 
	
	@RequestMapping("/admin")
	public String adminPage(Principal principal, Model model) {
		String email = principal.getName();
		model.addAttribute("currentUser", userService.findByEmail(email));
		return "adminPage.jsp";
	}
}
