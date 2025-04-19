package com.pretallez.domain.payment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "payment")
public class PaymentProperties {

	private String secretKey;
	private String baseUrl;
	private String confirmEndpoint;
	private int connectTimeout;
	private int readTimeout;
}
