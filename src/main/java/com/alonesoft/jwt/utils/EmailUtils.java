package com.alonesoft.jwt.utils;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.alonesoft.jwt.constants.EmailConstants;

public class EmailUtils {

	private EmailUtils() {
		// do nothing
	}

	/**
	 * Send a custom email using Thymeleaf templates
	 * @param to email of the customer
	 * @param subject the title of the email
	 * @param templateModel variables of the template
	 * @param thymeleafTemplateEngine the engine
	 * @param emailSender the sender
	 * @throws MessagingException
	 */
	public static void sendMessageUsingThymeleafTemplate(String to, String subject, Map<String, Object> templateModel,
			SpringTemplateEngine thymeleafTemplateEngine, JavaMailSender emailSender) throws MessagingException {

		Context thymeleafContext = new Context();
		thymeleafContext.setVariables(templateModel);
		String htmlBody = thymeleafTemplateEngine.process(EmailConstants.TEMPLATE_THYMELEAF.getValue(),
				thymeleafContext);
		sendHtmlMessage(to, subject, htmlBody, emailSender);
	}

	private static void sendHtmlMessage(String to, String subject, String htmlBody, JavaMailSender emailSender)
			throws MessagingException {

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, EmailConstants.UTF_8.getValue());
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlBody, true);
		emailSender.send(message);
	}
}
