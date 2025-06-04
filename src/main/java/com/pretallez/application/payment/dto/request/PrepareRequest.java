package com.pretallez.application.payment.dto.request;

public record PrepareRequest(
	String orderId,
	Integer amount
) { }
