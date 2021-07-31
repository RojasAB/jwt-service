package com.alonesoft.jwt.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alonesoft.jwt.constants.JWTConstants;
import com.alonesoft.jwt.constants.MessageExceptionConstants;
import com.alonesoft.jwt.dto.UserDTO;
import com.alonesoft.jwt.exception.UserAlreadyExistsException;
import com.alonesoft.jwt.exception.UserInfoRequiredException;
import com.alonesoft.jwt.exception.UserNotFoundException;
import com.alonesoft.jwt.model.User;
import com.alonesoft.jwt.repository.UserRepository;
import com.alonesoft.jwt.service.UserService;
import com.alonesoft.jwt.utils.UserUtils;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public UserServiceImpl(
			UserRepository userRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Optional<User> save(UserDTO userDTO) throws Exception {
		if(Objects.nonNull(userRepository.findByEmail(userDTO.getEmail()))) {
			throw new UserAlreadyExistsException(MessageExceptionConstants.USER_ALREADY_REGISTERED.getValue());
		}
		User user = UserUtils.toUser(userDTO, bCryptPasswordEncoder);
		return Optional.ofNullable(userRepository.save(user));
	}
	
	@Override
	public void save(User user) {
		userRepository.save(user);
	}
	
	@Override
	public void deleteAll() {
		userRepository.deleteAll();
	}

	@Override
	public User getUser(String login) {
		if(StringUtils.isEmpty(login)) {
    		throw new UserInfoRequiredException(MessageExceptionConstants.USERNAME_OR_EMAIL_REQUIRED.getValue());
    	}
    	User user = StringUtils.contains(login, JWTConstants.AT_STR.getValue()) ? 
    							userRepository.findByEmail(login) : 
    							userRepository.findByUsername(login);
    	if(Objects.isNull(user)) {
    		throw new UserNotFoundException(MessageExceptionConstants.NOT_FOUND_USER_FOR_LOGIN.getValue() + login); 
    	}
    	return user;
	}
	
	@Override
	public User findById(Long id) {
		Objects.requireNonNull(id);
		Optional<User> optUser = userRepository.findById(id);
		if(!optUser.isPresent()) {
			throw new UserNotFoundException(MessageExceptionConstants.NOT_FOUND_USER_FOR_ID.getValue() + id); 
		}
		return optUser.get();
	}
}
