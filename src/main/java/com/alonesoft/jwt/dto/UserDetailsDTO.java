package com.alonesoft.jwt.dto;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserDetailsDTO extends User {

	private static final long serialVersionUID = 1L;
	
	public UserDetailsDTO(com.alonesoft.jwt.model.User user) {
		super(user.getEmail(), user.getPassword(), authorities(user));
	}

	private static Collection<? extends GrantedAuthority> authorities(com.alonesoft.jwt.model.User user) {
		return Arrays.asList(() -> "ROLE_ADMIN");
	}

}
