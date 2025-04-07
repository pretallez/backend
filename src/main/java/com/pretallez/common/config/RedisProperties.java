package com.pretallez.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

	private String host;
	private int port;
	private int commandTimeout;
	private int shutdownTimeout;
}
