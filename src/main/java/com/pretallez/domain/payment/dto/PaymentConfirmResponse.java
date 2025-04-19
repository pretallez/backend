package com.pretallez.domain.payment.dto;

import com.pretallez.domain.payment.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentConfirmResponse {
	private String version;
	private String paymentKey;
	private String type;
	private String orderId;
	private String orderName;
	private String mId;
	private String currency;
	private String method;
	private int totalAmount;
	private int balanceAmount;
	private PaymentStatus status;
	private String requestedAt;
	private String approvedAt;
	private boolean useEscrow;
	private String lastTransactionKey;
	private int suppliedAmount;
	private int vat;
	private boolean cultureExpense;
	private int taxFreeAmount;
	private int taxExemptionAmount;
	private boolean isPartialCancelable;
	private String secret;
	private String country;
//	private Card card;
//	private EasyPay easyPay;
}
