package com.pretallez.domain.payment.dto;


import com.pretallez.domain.payment.enums.PaymentStatus;

public class PaymentConfirm {

	public record Request(String orderId, Integer amount, String paymentKey) {
	}

	public record Response(String paymentKey, String orderId, String orderName, String totalAmount,
								  String provider, String paymentMethod, PaymentStatus status) {
	}

	public record FailResponse(String code, String message, String data) {
	}
}
