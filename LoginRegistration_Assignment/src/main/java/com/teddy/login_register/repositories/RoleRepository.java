package com.teddy.login_register.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.teddy.login_register.models.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
	
	List<Role> findAll();
	List<Role> findByName(String name);
}

