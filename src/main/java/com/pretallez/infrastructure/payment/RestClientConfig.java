package com.pretallez.infrastructure.payment;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import com.pretallez.domain.payment.config.PaymentProperties;
import com.pretallez.common.util.HeaderUtils;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {
	private final PaymentProperties paymentProperties;

	@Bean
	public RestClient paymentRestClient() {
		return buildClient(
			paymentProperties.getBaseUrl(),
			paymentProperties.getConnectTimeout(),
			paymentProperties.getReadTimeout(),
			HeaderUtils.basicAuthHeaders(paymentProperties.getSecretKey())
		);
	}

	private RestClient buildClient(String baseUrl, int connectTimeout, int readTimeout, Map<String, String> headers) {
		RestClient.Builder builder = RestClient.builder()
			.baseUrl(baseUrl)
			.requestFactory(requestFactory(connectTimeout, readTimeout));

		if (headers != null && !headers.isEmpty()) {
				headers.forEach(builder::defaultHeader);
		}
		return builder.build();
	}

	private ClientHttpRequestFactory requestFactory(int connectTimeout, int readTimeout) {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(connectTimeout);
		factory.setReadTimeout(readTimeout);

		return factory;
	}
}
