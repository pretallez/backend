package com.pretallez.application.payment.dto.request;

public record PendingRequest(
	String orderId,
	Long amount
) { }
