package com.alonesoft.jwt.constants;

public enum MessageExceptionConstants {

	USER_ALREADY_REGISTERED("User already registered"),
	USERNAME_OR_EMAIL_REQUIRED("Username or email required"),
	NOT_FOUND_USER_FOR_LOGIN("Not Found User for login: "),
	NOT_FOUND_USER_FOR_ID("Not Found User for id: ");
	
	private String value;
	
	MessageExceptionConstants(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
