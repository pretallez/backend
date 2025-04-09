package com.pretallez.domain.payment.service;

import com.pretallez.domain.payment.dto.PaymentConfirm;

public interface PaymentService {

	/**
	 * 결제 승인을 요청한다.
	 * @param confirmRequest  결제 승인 요청 객체
	 * @return                결제 승인 응답 객체
	 */
	PaymentConfirm.Response confirmPayment(PaymentConfirm.Request confirmRequest);
}
