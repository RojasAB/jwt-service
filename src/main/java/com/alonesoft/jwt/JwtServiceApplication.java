package com.alonesoft.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alonesoft.jwt.constants.ForwardConstants;

@SpringBootApplication
public class JwtServiceApplication implements ErrorController {

	
	public static void main(String[] args) {
		SpringApplication.run(JwtServiceApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    @RequestMapping(value = ForwardConstants.PATH)
    public String error() {
        return ForwardConstants.FORWARD_ERROR;
    }
}
