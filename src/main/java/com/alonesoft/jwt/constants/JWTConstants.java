package com.alonesoft.jwt.constants;

public enum JWTConstants {
	
	SEPARATOR("="), AT_HTML("%40"), AT_STR("@"), FIELD_SEPARATOR("&");

	private String value;
	
	JWTConstants(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

}
