package com.alonesoft.jwt.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.alonesoft.jwt.annotation.RestApiController;
import com.alonesoft.jwt.dto.UserDTO;
import com.alonesoft.jwt.model.User;
import com.alonesoft.jwt.service.ChangePasswordRequestService;
import com.alonesoft.jwt.service.UserService;

@RestApiController("/rest")
public class UserController {

	private UserService userService;
	private ChangePasswordRequestService changePasswordRequestService;

	@Autowired
	public UserController(UserService userService, ChangePasswordRequestService changePasswordRequestService) {
		this.userService = userService;
		this.changePasswordRequestService = changePasswordRequestService; 
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserDTO user) throws Exception {
		Optional<User> optUserDB = userService.save(user);
		if (optUserDB.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@DeleteMapping("/users")
	public ResponseEntity<String> delete() {
		userService.deleteAll();
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping(
			  path = "/reset",
			  consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<String> reset(
			@RequestParam("uuid") String uuid,
			@RequestParam("password") String password) {
		Optional<Long> optUserId = changePasswordRequestService.check(uuid);
		if(!optUserId.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		User user = userService.findById(optUserId.get());
		user.setPassword(changePasswordRequestService.getPasswordEncripted(password));
		userService.save(user);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
