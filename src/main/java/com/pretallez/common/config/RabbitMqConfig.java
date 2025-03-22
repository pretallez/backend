package com.pretallez.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

	private static final String CHAT_QUEUE = "chat.queue";
	private static final String CHAT_EXCHANGE = "chat.exchange";
	private static final String DLX_EXCHANGE = "chat.dlx.exchange";
	private static final String DLX_QUEUE = "chat.dlx.queue";
	private static final String ROUTING_KEY = "chat.message";
	private static final String DLX_KEY = "chat.dlx";

	@Value("${spring.rabbitmq.host:localhost}")
	private String rabbitHost;

	@Value("${spring.rabbitmq.port:5672}")
	private int rabbitPort;

	@Value("${spring.rabbitmq.username:admin}")
	private String rabbitUsername;

	@Value("${spring.rabbitmq.password:admin}")
	private String rabbitPassword;

	@Bean
	public Queue chatQueue() {
		return QueueBuilder.durable(CHAT_QUEUE)
			.deadLetterExchange(DLX_EXCHANGE)
			.deadLetterRoutingKey(DLX_KEY)
			.build();
	}

	@Bean
	public Queue dlxQueue() {
		return QueueBuilder.durable(DLX_QUEUE).build();
	}

	@Bean
	public DirectExchange chatExchange() {
		return new DirectExchange(CHAT_EXCHANGE);
	}

	@Bean
	public DirectExchange dlxExchange() {
		return new DirectExchange(DLX_EXCHANGE);
	}

	@Bean
	public Binding chatBinding(@Qualifier("chatQueue") Queue chatQueue,
		@Qualifier("chatExchange") DirectExchange chatExchange) {
		return BindingBuilder.bind(chatQueue).to(chatExchange).with(ROUTING_KEY);
	}

	@Bean
	public Binding dlxBinding(@Qualifier("dlxQueue") Queue dlxQueue,
		@Qualifier("dlxExchange") DirectExchange dlxExchange) {
		return BindingBuilder.bind(dlxQueue).to(dlxExchange).with(DLX_KEY);
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setHost(rabbitHost);
		connectionFactory.setPort(rabbitPort);
		connectionFactory.setUsername(rabbitUsername);
		connectionFactory.setPassword(rabbitPassword);
		return connectionFactory;
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public MessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
