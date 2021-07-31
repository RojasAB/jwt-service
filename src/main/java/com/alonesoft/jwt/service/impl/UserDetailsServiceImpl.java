package com.alonesoft.jwt.service.impl;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alonesoft.jwt.dto.UserDetailsDTO;
import com.alonesoft.jwt.model.User;
import com.alonesoft.jwt.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;
	
	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = Optional.ofNullable(userRepository.findByEmail(username))
					.orElse(userRepository.findByUsername(username));
		return new UserDetailsDTO(Optional.ofNullable(user).orElseThrow(throwUserNotFoundException(username)));
	}
	
	private static Supplier<UsernameNotFoundException> throwUserNotFoundException(String username) {
		return () -> new UsernameNotFoundException(username);
	}

}
