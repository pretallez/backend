package com.pretallez.domain.payment.service;

import com.pretallez.domain.payment.dto.PaymentConfirm;
import com.pretallez.domain.payment.dto.PaymentTempData;

public interface PaymentService {

	/**
	 * 결제 승인을 요청한다.
	 * @param confirmRequest  결제 승인 요청 객체
	 * @return                결제 승인 응답 객체
	 */
	PaymentConfirm.Response confirmPayment(PaymentConfirm.Request confirmRequest);

	/**
	 * 결제 승인 전 결제 정보를 임시로 저장한다.
	 *
	 * @param paymentTempData 결제 임시 정보 객체
	 */
	void savePaymentTempData(PaymentTempData paymentTempData);
}
