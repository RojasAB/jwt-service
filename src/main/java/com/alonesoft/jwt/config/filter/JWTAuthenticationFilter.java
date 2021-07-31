package com.alonesoft.jwt.config.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alonesoft.jwt.constants.ArrayIndexEnum;
import com.alonesoft.jwt.constants.JWTConstants;
import com.alonesoft.jwt.constants.SecurityConstants;
import com.alonesoft.jwt.dto.JwtDTO;
import com.alonesoft.jwt.dto.UserDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	@Autowired
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		this.setFilterProcessesUrl(SecurityConstants.LOGIN_URL.getValue());
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			Scanner scan = new Scanner(request.getInputStream());
			String scanValue = scan.next();
			if (StringUtils.isEmpty(scanValue)) {
				return null;
			}
			String[] arr = StringUtils
					.replace(scanValue, 
							JWTConstants.AT_HTML.getValue(), 
							JWTConstants.AT_STR.getValue())
					.split(JWTConstants.FIELD_SEPARATOR.getValue());
			if (ArrayUtils.isEmpty(arr) || arr.length != NumberUtils.INTEGER_TWO) {
				return null;
			}
			scan.close();
			String username = arr[ArrayIndexEnum.FIRT.getValue()]
					.split(JWTConstants.SEPARATOR.getValue())[ArrayIndexEnum.SECOND.getValue()];
			String password = arr[ArrayIndexEnum.SECOND.getValue()]
					.split(JWTConstants.SEPARATOR.getValue())[ArrayIndexEnum.SECOND.getValue()];

			UserDTO creds = new UserDTO();
			creds.setUsername(username);
			creds.setEmail(username);
			creds.setPassword(password);

			String loginUsr = Optional.ofNullable(creds.getEmail()).orElse(creds.getUsername());

			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginUsr, creds.getPassword(), new ArrayList<>()));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = ((UserDetails) authResult.getPrincipal()).getUsername();
		String token = JWT.create().withSubject(username)
				.withExpiresAt(new Date(
						System.currentTimeMillis() + Long.valueOf(SecurityConstants.EXPIRATION_TIME.getValue())))
				.sign(Algorithm.HMAC256(SecurityConstants.SECRET.getValue()));
		JwtDTO jwtDTO = new JwtDTO();
		jwtDTO.setUsername(username);
		jwtDTO.setJwt(token);
		String body = new ObjectMapper().writeValueAsString(jwtDTO);
		PrintWriter resWriter = response.getWriter();
		resWriter.write(body);
		resWriter.flush();
	}

}
