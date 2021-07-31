package com.alonesoft.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.alonesoft.jwt.config.filter.JWTAuthenticationFilter;
import com.alonesoft.jwt.config.filter.JWTAuthorizationFilter;
import com.alonesoft.jwt.constants.PageConstants;
import com.alonesoft.jwt.constants.SecundaryToolsConstants;
import com.alonesoft.jwt.constants.SecurityConstants;
import com.alonesoft.jwt.service.impl.UserDetailsServiceImpl;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsServiceImpl userDetailsServiceImpl;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public WebSecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsServiceImpl = userDetailsServiceImpl;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and().csrf().disable().authorizeRequests()
		
				.antMatchers(HttpMethod.POST, SecurityConstants.REGISTER_URL.getValue()).permitAll()
				.antMatchers(HttpMethod.POST, SecurityConstants.CHECK_URL.getValue()).permitAll()
				.antMatchers(HttpMethod.POST, SecurityConstants.FORGOT_URL.getValue()).permitAll()
				.antMatchers(HttpMethod.POST, SecurityConstants.RESET_URL.getValue()).permitAll()
				
				.antMatchers(PageConstants.ROOT_URL.getValue()).permitAll()
				.antMatchers(PageConstants.REGISTER_URL.getValue()).permitAll()
				.antMatchers(PageConstants.FORGOT_URL.getValue()).permitAll()
				.antMatchers(PageConstants.MANIFEST_JSON.getValue()).permitAll()
				.antMatchers(PageConstants.FAVICON_ICON.getValue()).permitAll()
				.antMatchers(PageConstants.LOGO_192.getValue()).permitAll()
				.antMatchers(PageConstants.STATIC_URL.getValue()).permitAll()
				
				.antMatchers(SecundaryToolsConstants.API_DOCS.getValue()).permitAll()
				.antMatchers(SecundaryToolsConstants.SWAGGER_UI.getValue()).permitAll()
				.antMatchers(SecundaryToolsConstants.H2_CONSOLE.getValue()).permitAll()
				
				.anyRequest().authenticated().and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager())).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder);
	}

	@Bean
	/* default */ CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		source.registerCorsConfiguration(SecurityConstants.CORS_URL.getValue(), corsConfiguration);
		return source;
	}

}
