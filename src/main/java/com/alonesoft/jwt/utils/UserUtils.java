package com.alonesoft.jwt.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.alonesoft.jwt.dto.UserDTO;
import com.alonesoft.jwt.model.User;

public class UserUtils {

	private UserUtils() {
		// do nothing
	}
	
	public static User toUser(UserDTO userDTO, BCryptPasswordEncoder bCryptPasswordEncoder) {
		User user = new User();
		user.setId(userDTO.getId());
		user.setUsername(userDTO.getUsername());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		user.setEmail(userDTO.getEmail());
		return user;
	}
}
