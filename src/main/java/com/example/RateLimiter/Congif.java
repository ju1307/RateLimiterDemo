package com.example.RateLimiter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 
 * @author jutekar
 *
 */

@Configuration
@EnableWebMvc
public class Congif extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor(new RateLimitingInterceptor());
//		.addPathPatterns("/test/abc");
	}
}
