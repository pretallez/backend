package com.pretallez.infrastructure.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.pretallez.common.util.HeaderUtils;
import com.pretallez.infrastructure.config.web.RestClientFactory;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class PaymentRestClient {
	private final PaymentProperties paymentProperties;
	private final RestClientFactory factory;

	@Bean(name = "paymentClient")
	public RestClient paymentRestClient() {
		return factory.create(
			paymentProperties.getBaseUrl(),
			paymentProperties.getConnectTimeout(),
			paymentProperties.getReadTimeout(),
			HeaderUtils.basicAuthHeaders(paymentProperties.getSecretKey())
		);
	}

}
