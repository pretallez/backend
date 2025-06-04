package com.pretallez.infrastructure.payment.dto;

public record ApproveFailResponse(
	String code,
	String message,
	String data
) { }
