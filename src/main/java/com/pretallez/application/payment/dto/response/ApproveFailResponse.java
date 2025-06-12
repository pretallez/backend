package com.pretallez.application.payment.dto.response;

public record ApproveFailResponse(
	String code,
	String message,
	String data
) { }
