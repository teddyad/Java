package com.teddy.login_register.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.teddy.login_register.models.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
	
	 List<Role> findAll();
	 Role findByName(String name);
}
