package com.teddy.login_register.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.teddy.login_register.models.Role;
import com.teddy.login_register.models.User;
import com.teddy.login_register.services.RoleService;
import com.teddy.login_register.services.UserService;
import com.teddy.login_register.validations.UserValidator;

@Controller
public class Users {

	private UserService userService;
	private UserValidator userValidator;
	private RoleService roleService;

	public Users(UserService userService, UserValidator userValidator, RoleService roleService) {
		super();
		this.userService = userService;
		this.userValidator = userValidator;
		this.roleService = roleService;
	}

	// ============= register new users and assign appropriate role =============

	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model,
			HttpSession session) {
		userValidator.validate(user, result);

		if (result.hasErrors()) {
			return "login_or_register.jsp";
		}

		if (roleService.findByName("ROLE_SUPERADMIN").getUsers().size() < 1) {
			userService.createUser(new String[] { "ROLE_USER", "ROLE_SUPERADMIN" }, user);
			session.setAttribute("created", "Your account is created. Please log in.");

		} else {
			userService.createUser(new String[] { "ROLE_USER" }, user);
			session.setAttribute("created", "Your account is created. Please log in.");
		}

		return "redirect:/login";
	}

	// ======================== login/logout Validation =========================

	@RequestMapping(value = { "/", "/login", "registration" })
	public String login(@Valid @ModelAttribute("user") User user,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {
		if (error != null) {
			model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
		}
		if (logout != null) {
			model.addAttribute("logoutMessage", "You have Logout!");
		}

		model.addAttribute("user", new User());
		return "login_or_register.jsp";
	}

	// ======= check users role and redirect to appropriate when thy login =======
	
	@RequestMapping(value = { "/", "/dashboard" })
	public String dashboardPage(Principal principal, Model model) {
		User user = userService.findByEmail(principal.getName());
		model.addAttribute("user", user);
		user.setUpdatedAt(new Date());
		userService.updateUser(user);
		userService.recordDateWhenLoggedIn(user);

		if (user.isSuperAdmin()) {
			return "redirect:/superAdmin";
		} else if (user.isAdmin()) {
			return "redirect:/admin";
		} else {
			user.isUser();
			return "dashboardPage.jsp";
		}

	}

	// ============= dashboard - non-Admin user page =========================

	@RequestMapping("/")
	public String userPage(Principal principal, Model model) {
		String email = principal.getName();
		model.addAttribute("user", userService.findByEmail(email));
		return "dashboardPage.jsp";
	}

	// ================ ADMIN page - show, promote and delete =================

	@RequestMapping("/admin")
	public String adminPage(Principal principal, Model model) {
		model.addAttribute("user", userService.findByEmail(principal.getName()));
		model.addAttribute("users", userService.getAll());
		return "adminPage.jsp";
	}

	@RequestMapping("/admin/promote/{id}")
	public String giveUserAdminPrivilege(@PathVariable("id") Long id) {
		User user = userService.findById(id);
		List<Role> userRoles = user.getRoles();
		userRoles.add(roleService.findByName("ROLE_ADMIN"));
		userService.updateUser(user);
		return "redirect:/admin";
	}

	@RequestMapping("/admin/delete/{id}")
	public String deleteUserByAdmin(@PathVariable("id") Long id) {
		userService.deleteUser(id);
		return "redirect:/admin";
	}

	// ======= SUPER ADMIN page - show, promote and delete =============

	@RequestMapping("/superAdmin")
	public String superAdminPage(Principal principal, Model model) {
		model.addAttribute("user", userService.findByEmail(principal.getName()));
		model.addAttribute("users", userService.getAll());
		return "superAdminPag.jsp";
	}

	@RequestMapping("/superAdmin/promote/{id}")
	public String promoteToSuperAdmin(@PathVariable("id") Long id) {
		User user = userService.findById(id);
		List<Role> userRoles = user.getRoles();
		userRoles.add(roleService.findByName("ROLE_SUPERADMIN"));
		userService.updateUser(user);
		return "redirect:/superAdmin";
	}

	@RequestMapping("/superAdmin/delete/{id}")
	public String deleteUserBySuperAdmin(@PathVariable("id") Long id) {
		userService.deleteUser(id);
		return "redirect:/superAdmin";
	}

}
