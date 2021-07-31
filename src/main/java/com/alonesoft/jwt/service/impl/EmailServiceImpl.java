package com.alonesoft.jwt.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.alonesoft.jwt.constants.EmailConstants;
import com.alonesoft.jwt.model.User;
import com.alonesoft.jwt.service.ChangePasswordRequestService;
import com.alonesoft.jwt.service.EmailService;
import com.alonesoft.jwt.service.UserService;
import com.alonesoft.jwt.utils.EmailUtils;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender emailSender;
    private SpringTemplateEngine thymeleafTemplateEngine;
    private UserService userService;
    private ChangePasswordRequestService changePasswordRequestService;

    @Autowired
	public EmailServiceImpl(
			JavaMailSender emailSender, 
			SpringTemplateEngine thymeleafTemplateEngine,
			UserService userService,
			ChangePasswordRequestService changePasswordRequestService) {
    	
		this.emailSender = emailSender;
		this.thymeleafTemplateEngine = thymeleafTemplateEngine;
		this.userService = userService;
		this.changePasswordRequestService = changePasswordRequestService;
	}
    
    @Override
    public void sendForgotMessage(String login) throws MessagingException {
    	
    	User user = userService.getUser(login);
    	
    	String userFirstName = user.getFirstName();    	
		String recipientName = StringUtils.isNotEmpty(userFirstName) ? userFirstName : EmailConstants.CUSTOMER.getValue();
		String key = changePasswordRequestService.generateHash(user);
		StringBuilder emailMessage = new StringBuilder(EmailConstants.MESSAGE_A.getValue()).append(key).append(EmailConstants.MESSAGE_B.getValue());
		Map<String, Object> mapParams = new HashMap<String, Object>() {
			private static final long serialVersionUID = 8051536973416564284L;
			{
				put(EmailConstants.RECIPIENT_NAME_KEY.getValue(),recipientName);
				put(EmailConstants.TEXT_KEY.getValue(), emailMessage.toString());
			}
		};		
		EmailUtils.sendMessageUsingThymeleafTemplate(
				user.getEmail(), 
				EmailConstants.PASSWORD_RESET.getValue(), 
				mapParams,
				thymeleafTemplateEngine,
				emailSender
				);
    }	
}