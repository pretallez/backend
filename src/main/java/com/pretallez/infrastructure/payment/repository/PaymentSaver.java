package com.pretallez.infrastructure.payment.repository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pretallez.application.payment.enums.PaymentErrorCode;
import com.pretallez.application.payment.exception.PaymentException;
import com.pretallez.domain.payment.entity.Payment;
import com.pretallez.domain.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentSaver {

	private final PaymentRepository paymentRepository;

	@Transactional
	public void savePayment(Payment payment) {
		try {
			paymentRepository.save(payment);
		} catch (DataIntegrityViolationException e) {
			log.error("결제 데이터 중복 저장 발생: memberId={}, orderId={}", payment.getMember().getId(), payment.getOrderId());
			throw new PaymentException(PaymentErrorCode.DUPLICATE_ORDER_ID, e);
		} catch (Exception e) {
			log.error("결제 저장 실패: memberId={}, orderId={}", payment.getMember().getId(), payment.getOrderId());
			throw new PaymentException(PaymentErrorCode.SAVE_PAYMENT_FAILED, e);
		}

	}
}
