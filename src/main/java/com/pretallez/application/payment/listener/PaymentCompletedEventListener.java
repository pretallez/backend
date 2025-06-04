package com.pretallez.application.payment.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentCompletedEventListener {

	@EventListener
	public void handle(PaymentCompletedEventListener event) {
		log.info("PaymentCompletedEvent");

		// Todo: 회원 포인트 충전 및 알림 발송
	}
}
