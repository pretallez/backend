package com.pretallez.domain.payment.dto;

public record PaymentConfirmRequest(String orderId, Integer amount, String paymentKey) {
}
