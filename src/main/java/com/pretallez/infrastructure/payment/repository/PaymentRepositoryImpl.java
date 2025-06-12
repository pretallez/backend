package com.pretallez.infrastructure.payment.repository;

import org.springframework.stereotype.Repository;

import com.pretallez.domain.payment.entity.Payment;
import com.pretallez.domain.payment.repository.PaymentRepository;
import com.pretallez.infrastructure.payment.repository.jpa.PaymentJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

	private final PaymentJpaRepository paymentJpaRepository;

	@Override
	public void save(Payment payment) {
		paymentJpaRepository.save(payment);
	}
}
