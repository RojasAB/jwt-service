package com.alonesoft.jwt.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alonesoft.jwt.constants.LiquibaseConstants;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
public class LiquibaseConfig {
	
	@Value("${spring.liquibase.init:false}")
	private boolean init;
	
	@Bean
	public SpringLiquibase springLiquibase(DataSource dataSource) {
		SpringLiquibase springLiquibase = new SpringLiquibase();
		springLiquibase.setChangeLog(getChangeLog(init));
		springLiquibase.setDataSource(dataSource);
		return springLiquibase;
	}
	
	private static String getChangeLog(boolean init) {
		StringBuilder changelog = new StringBuilder(LiquibaseConstants.CHANGELOG_PREFIX.getValue());
		changelog.append(init? 
				LiquibaseConstants.CHANGELOG_INIT.getValue() : 
				LiquibaseConstants.CHANGELOG_MASTER.getValue());
		return changelog.toString();
	}
}
