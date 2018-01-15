package com.teddy.login_register.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.teddy.login_register.models.Role;
import com.teddy.login_register.models.User;
import com.teddy.login_register.repository.RoleRepository;
import com.teddy.login_register.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepo;
	private RoleRepository roleRepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserService(UserRepository userRepo, RoleRepository roleRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {

		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public List<User> getAll() {
		return (List<User>) userRepo.findAll();
	}

	public User findById(Long id) {
		return userRepo.findOne(id);
	}

	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	// create user and role

	public void createUser(String[] roles, User user) {
		List<Role> userRoles = new ArrayList<>();

		for (String role : roles) {
			Role getRole = roleRepo.findByName(role);
			if (getRole != null) {
				userRoles.add(getRole);
			}
		}

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(userRoles);
		userRepo.save(user);
	}

	public void updateUser(User user) {
		userRepo.save(user);
	}

	public void deleteUser(Long id) {
		userRepo.delete(id);
	}

	public void recordDateWhenLoggedIn(User user) {
		user.setLastSignIn(new Date());
		userRepo.save(user);
	}

}
