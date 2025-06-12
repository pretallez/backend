package com.pretallez.domain.common.event;

public interface DomainEventPublisher {
	void publish(Object event);
}
