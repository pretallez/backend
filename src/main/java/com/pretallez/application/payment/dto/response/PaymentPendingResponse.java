package com.pretallez.application.payment.dto.response;

public record PaymentPendingResponse(
	String orderId,
	Long amount
) { }
