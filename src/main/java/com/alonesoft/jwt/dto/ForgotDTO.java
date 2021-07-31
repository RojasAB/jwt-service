package com.alonesoft.jwt.dto;

public class ForgotDTO {

	private Long userId;
	private String uuid;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	} 
	
}
