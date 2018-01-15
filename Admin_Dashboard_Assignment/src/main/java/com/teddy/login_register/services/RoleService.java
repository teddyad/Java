package com.teddy.login_register.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.teddy.login_register.models.Role;
import com.teddy.login_register.repository.RoleRepository;

@Service
public class RoleService {

	private RoleRepository roleRepo;

	public RoleService(RoleRepository roleRepo) {
		this.roleRepo = roleRepo;
	}

	public List<Role> getAll() {
		return roleRepo.findAll();
	}

	public Role findByName(String name) {
		return roleRepo.findByName(name);
	}

	public void createRole(Role role) {
		roleRepo.save(role);
	}

	public void updateRole(Role role) {
		roleRepo.save(role);
	}

	public void deleteRole(Long id) {
		roleRepo.delete(id);
	}

}
