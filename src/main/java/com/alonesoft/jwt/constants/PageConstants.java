package com.alonesoft.jwt.constants;

public enum PageConstants {
	ROOT_URL("/"), REGISTER_URL("/register"),
	FORGOT_URL("/forgot"), MANIFEST_JSON("/manifest.json"),
	FAVICON_ICON("/favicon.ico"), LOGO_192("/logo192.png"),
	STATIC_URL("/static/**");
	

	private String value;
	
	private PageConstants(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
