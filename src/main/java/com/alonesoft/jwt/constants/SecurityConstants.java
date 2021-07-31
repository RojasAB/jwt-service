package com.alonesoft.jwt.constants;

public enum SecurityConstants {
	
	SECRET("SECRET_KEY"), EXPIRATION_TIME("900000"),
	TOKEN_PREFIX("Bearer "), HEADER_STRING("Authorization"),
	REGISTER_URL("/rest/register"), CHECK_URL("/rest/check"), 
	FORGOT_URL("/rest/forgot"), RESET_URL("/rest/reset"),
	LOGIN_URL("/rest/login"), CORS_URL("/**");
	

	private String value;
	
	private SecurityConstants(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
