package com.alonesoft.jwt.service;

import java.util.Optional;

import com.alonesoft.jwt.dto.UserDTO;
import com.alonesoft.jwt.model.User;

public interface UserService {

	public Optional<User> save(UserDTO userDTO) throws Exception;
	
	public void deleteAll();
	
	public User getUser(String login);
	
	public User findById(Long id);
	
	public void save(User user);
}
