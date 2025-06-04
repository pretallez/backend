package com.pretallez.infrastructure.payment.dto;

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
