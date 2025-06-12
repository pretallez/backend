package com.pretallez.domain.payment.repository;

import com.pretallez.domain.payment.entity.Payment;

public interface PaymentRepository {

	// Command
	void save(Payment payment);
}
