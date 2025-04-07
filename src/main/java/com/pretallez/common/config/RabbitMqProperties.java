package com.pretallez.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Component
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMqProperties {

	private String host;
	private int port;
	private String username;
	private String password;
	private int stompPort;
	private String destinationPrefix;
	private String appDestinationPrefix;
	private Chat chat;

	@Getter @Setter
	public static class Chat {
		private String exchange;
		private String queue;
		private String routingKey;
	}
}
