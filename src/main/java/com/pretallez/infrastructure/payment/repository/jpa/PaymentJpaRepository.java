package com.pretallez.infrastructure.payment.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pretallez.domain.payment.entity.Payment;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
}
