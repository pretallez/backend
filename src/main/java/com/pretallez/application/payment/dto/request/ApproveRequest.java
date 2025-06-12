package com.pretallez.application.payment.dto.request;

public record ApproveRequest(
	String orderId,
	Long amount,
	String paymentKey,
	Long memberId
) { }

