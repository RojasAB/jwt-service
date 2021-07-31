package com.alonesoft.jwt.api;

import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alonesoft.jwt.annotation.RestApiController;
import com.alonesoft.jwt.dto.ForgotDTO;
import com.alonesoft.jwt.service.ChangePasswordRequestService;
import com.alonesoft.jwt.service.EmailService;

@RestApiController("/rest")
public class ForgotController {

	private EmailService emailService;
	private ChangePasswordRequestService changePasswordRequestService;
	
	public ForgotController(EmailService emailService, ChangePasswordRequestService changePasswordRequestService) {
		this.emailService = emailService;
		this.changePasswordRequestService = changePasswordRequestService;
	}
	
	@PostMapping("/forgot")
	public ResponseEntity<String> forgot(@RequestParam String email) throws MessagingException {
		emailService.sendForgotMessage(email);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/check")
	public ResponseEntity<ForgotDTO> check(@RequestParam String code) {
		Optional<Long> optUserId = changePasswordRequestService.check(code);
		if(optUserId.isPresent()) {
			ForgotDTO forgotDTO = new ForgotDTO();
			forgotDTO.setUserId(optUserId.get());
			forgotDTO.setUuid(code);
			return ResponseEntity.status(HttpStatus.OK).body(forgotDTO);
		}
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
	}
	
}
