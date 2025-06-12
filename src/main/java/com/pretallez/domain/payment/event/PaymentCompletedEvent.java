package com.pretallez.domain.payment.event;

public record PaymentCompletedEvent (
	Long memberId,
	Long amount
) { }
