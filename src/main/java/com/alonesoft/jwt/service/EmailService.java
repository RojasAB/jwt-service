package com.alonesoft.jwt.service;

import javax.mail.MessagingException;

@FunctionalInterface
public interface EmailService {

	public void sendForgotMessage(String login) throws MessagingException;
}
