package com.alonesoft.jwt.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.alonesoft.jwt.constants.EmailConstants;

@Configuration
public class EmailConfig {
	
	@Value("${spring.mail.template:mail.templates}")
	private String templateDir;

	@Bean
	@Primary
	public ITemplateResolver thymeleafTemplateResolver() {
	    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
	    StringBuilder prefixPath = new StringBuilder(templateDir).append(File.separator);
	    templateResolver.setPrefix(prefixPath.toString());
	    templateResolver.setSuffix(EmailConstants.HTML_SUFFIX.getValue());
	    templateResolver.setTemplateMode(EmailConstants.HTML.getValue());
	    templateResolver.setCharacterEncoding(EmailConstants.UTF_8.getValue());
	    return templateResolver;
	}
	
	@Bean
	public SpringTemplateEngine thymeleafTemplateEngine(ITemplateResolver templateResolver) {
	    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver);
	    templateEngine.setTemplateEngineMessageSource(emailMessageSource());
	    return templateEngine;
	}
	
	@Bean
	@Description("Spring Message Resolver")
	public ResourceBundleMessageSource emailMessageSource() {
	    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	    messageSource.setBasename(EmailConstants.BASE_NAME.getValue());
	    return messageSource;
	}
}
