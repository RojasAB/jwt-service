package com.alonesoft.jwt.constants;

public enum ArrayIndexEnum {

	FIRT(0), SECOND(1);
	
	private int value;
	
	ArrayIndexEnum(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
