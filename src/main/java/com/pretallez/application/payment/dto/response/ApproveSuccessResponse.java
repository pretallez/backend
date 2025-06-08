package com.pretallez.application.payment.dto.response;

import java.time.OffsetDateTime;

import com.pretallez.domain.payment.enums.PaymentStatus;

public record ApproveSuccessResponse(
	String paymentKey,
	String orderId,
	String method,
	Long totalAmount,
	OffsetDateTime approvedAt,
	Receipt receipt,
	PaymentStatus status
) { }
