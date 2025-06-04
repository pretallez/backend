package com.pretallez.infrastructure.payment.dto;

public record CachedPrepareResponse(
	String orderId,
	Long amount
) { }
