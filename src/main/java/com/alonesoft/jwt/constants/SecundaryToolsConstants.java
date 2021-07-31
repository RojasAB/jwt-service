package com.alonesoft.jwt.constants;

public enum SecundaryToolsConstants {

	API_DOCS("/api/v2/api-docs"), SWAGGER_UI("/swagger-ui"),
	H2_CONSOLE("/h2-console/**");
	
	private String value;
	
	SecundaryToolsConstants(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
