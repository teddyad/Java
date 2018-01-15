package com.teddy.login_register.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.teddy.login_register.models.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	User findByEmail(String email);

}

