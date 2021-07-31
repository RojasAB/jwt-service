package com.alonesoft.jwt.model;

import java.time.OffsetDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CHANGE_PASSWORD_REQUEST")
public class ChangePasswordRequest {

	@Id
	@Column(name="ID")
	private String id;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private User user;
	
	@Column(name = "EXPIRATION_TIME", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime expirationTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OffsetDateTime getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(OffsetDateTime expirationTime) {
		this.expirationTime = expirationTime;
	}
	
}
