package com.alonesoft.jwt.service;

import java.util.Optional;

import com.alonesoft.jwt.model.User;

public interface ChangePasswordRequestService {

	String generateHash(User user);
	
	Optional<Long> check(String code);
	
	String getPasswordEncripted(String password);
}
