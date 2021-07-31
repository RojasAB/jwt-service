package com.alonesoft.jwt.constants;

public enum EmailConstants {
	HTML_SUFFIX(".html"), HTML("HTML"),	UTF_8("UTF-8"),	BASE_NAME("messages"),
	CUSTOMER("Customer"), RECIPIENT_NAME_KEY("recipientName"), TEXT_KEY("text"),
	MESSAGE_A("Use this link: http://localhost:8081/reset/"), MESSAGE_B(" to activate your account"),
	PASSWORD_RESET("Password Reset"), TEMPLATE_THYMELEAF("template-thymeleaf.html");
	
	private String value;
	
	EmailConstants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
