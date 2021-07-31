package com.alonesoft.jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class UserInfoRequiredException extends RuntimeException {

	private static final long serialVersionUID = -1358026376365504610L;
	
	public UserInfoRequiredException() {
        super();
    }
    public UserInfoRequiredException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserInfoRequiredException(String message) {
        super(message);
    }
    public UserInfoRequiredException(Throwable cause) {
        super(cause);
    }
}
