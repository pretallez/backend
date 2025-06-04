package com.pretallez.infrastructure.common.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.pretallez.domain.common.event.DomainEventPublisher;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringDomainEventPublish implements DomainEventPublisher {

	private final ApplicationEventPublisher publisher;

	@Override
	public void publish(Object event) {
		publisher.publishEvent(event);
	}
}
