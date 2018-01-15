package com.teddy.login_register.validations;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.teddy.login_register.models.User;
import com.teddy.login_register.services.UserService;

@Component
public class UserValidator implements Validator {
	private UserService userService;

	public UserValidator(UserService userService) {
		this.userService = userService;
	}

	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	public void validate(Object object, Errors errors) {
		User user = (User) object;

		if (!user.getConfirmPassword().equals(user.getPassword())) {
			errors.rejectValue("confirmPassword", "Match");
		}

		if (userService.findByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "Taken");
		}
	}

}
