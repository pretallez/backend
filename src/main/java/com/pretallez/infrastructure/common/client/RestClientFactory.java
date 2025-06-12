package com.pretallez.infrastructure.common.client;

import java.util.Map;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestClientFactory {

	public RestClient create(String baseUrl, int connectTimeout, int readTimeout, Map<String, String> headers) {
		RestClient.Builder builder = RestClient.builder()
			.baseUrl(baseUrl)
			.requestFactory(requestFactory(connectTimeout, readTimeout));

		headers.forEach(builder::defaultHeader);
		return builder.build();
	}

	public ClientHttpRequestFactory requestFactory(int connectTimeout, int readTimeout) {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(connectTimeout);
		factory.setReadTimeout(readTimeout);

		return factory;
	}
}
