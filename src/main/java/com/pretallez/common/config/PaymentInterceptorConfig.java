package com.pretallez.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.pretallez.common.interceptor.IdempotencyInterceptor;

@Configuration
public class PaymentInterceptorConfig implements WebMvcConfigurer {

	private final IdempotencyInterceptor interceptor;

	public PaymentInterceptorConfig(IdempotencyInterceptor interceptor) {
		this.interceptor = interceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
			.addPathPatterns("/v1/api/payments/confirm");
	}
}
