package com.alonesoft.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alonesoft.jwt.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
	User findByEmail(String email);
}
