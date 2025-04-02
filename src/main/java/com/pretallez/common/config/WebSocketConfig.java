package com.pretallez.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private final RabbitMqProperties rabbitMqProperties;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry
			.addEndpoint("ws")
			.setAllowedOriginPatterns("*");
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableStompBrokerRelay(rabbitMqProperties.getDestinationPrefix())
			.setRelayHost(rabbitMqProperties.getHost())
			.setRelayPort(rabbitMqProperties.getStompPort())
			.setClientLogin(rabbitMqProperties.getUsername())
			.setClientPasscode(rabbitMqProperties.getUsername())
			.setSystemLogin(rabbitMqProperties.getUsername())
			.setSystemPasscode(rabbitMqProperties.getPassword());

		registry.setApplicationDestinationPrefixes(rabbitMqProperties.getAppDestinationPrefix());
	}
}