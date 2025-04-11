package com.pretallez.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTempData {
	private String orderId;
	private Integer amount;
}
