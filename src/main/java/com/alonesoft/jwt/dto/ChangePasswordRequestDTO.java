package com.alonesoft.jwt.dto;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.apache.commons.lang3.math.NumberUtils;

import com.alonesoft.jwt.model.ChangePasswordRequest;
import com.alonesoft.jwt.model.User;

public class ChangePasswordRequestDTO {

	private static final int ONE_HOUR = NumberUtils.INTEGER_ONE;
	
	private String id;
	private User user;
	private OffsetDateTime expirationTime;
	
	public ChangePasswordRequestDTO(String id, User user) {
		OffsetDateTime expirationTime = OffsetDateTime.now(ZoneOffset.UTC).plusHours(ONE_HOUR); 
		this.id = id;
		this.user = user;
		this.expirationTime = expirationTime;
	}
	
	public ChangePasswordRequest toChangePasswordRequest() {
		ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
		changePasswordRequest.setId(id);
		changePasswordRequest.setUser(user);
		changePasswordRequest.setExpirationTime(expirationTime);
		return changePasswordRequest;
	}
	
	
	

		
	
}
