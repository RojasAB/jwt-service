package com.alonesoft.jwt.config.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.alonesoft.jwt.constants.SecurityConstants;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			FilterChain filterChain
			) throws IOException, ServletException {
		String header = httpServletRequest.getHeader(SecurityConstants.HEADER_STRING.getValue());
		if(StringUtils.isNotEmpty(header) && StringUtils.startsWith(header, SecurityConstants.TOKEN_PREFIX.getValue())) {
			SecurityContextHolder.getContext().setAuthentication(getAuthentication(header).orElse(null));
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
	
	private Optional<UsernamePasswordAuthenticationToken> getAuthentication(String header) {
		String user = JWT.require(Algorithm.HMAC256(SecurityConstants.SECRET.getValue()))
						 .build()
						 .verify(StringUtils.replace(header, SecurityConstants.TOKEN_PREFIX.getValue(), StringUtils.EMPTY))
						 .getSubject();
		if(StringUtils.isEmpty(user)) {
			return Optional.empty();
		}
		return Optional.of(new UsernamePasswordAuthenticationToken(user,  null, Collections.emptyList()));
	}
}
