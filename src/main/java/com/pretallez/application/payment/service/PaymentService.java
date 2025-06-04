package com.pretallez.application.payment.service;

import static com.pretallez.application.payment.enums.PaymentErrorCode.*;
import static com.pretallez.common.enums.error.EntityErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pretallez.application.payment.dto.request.ApproveRequest;
import com.pretallez.application.payment.dto.request.PrepareRequest;
import com.pretallez.application.payment.exception.PaymentException;
import com.pretallez.application.payment.port.input.PaymentUseCase;
import com.pretallez.application.payment.port.output.PaymentCacheStore;
import com.pretallez.application.payment.port.output.PaymentClient;
import com.pretallez.domain.common.event.DomainEventPublisher;
import com.pretallez.domain.member.entity.Member;
import com.pretallez.domain.member.repository.MemberRepository;
import com.pretallez.domain.payment.entity.Payment;
import com.pretallez.domain.payment.event.PaymentCompletedEvent;
import com.pretallez.domain.payment.policy.PaymentValidator;
import com.pretallez.domain.payment.repository.PaymentRepository;
import com.pretallez.infrastructure.payment.dto.ApproveSuccessResponse;
import com.pretallez.infrastructure.payment.dto.CachedPrepareResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {
	private final PaymentClient paymentClient;
	private final PaymentCacheStore cacheStore;

	private final PaymentRepository paymentRepository;
	private final MemberRepository memberRepository;

	private final DomainEventPublisher eventPublisher;

	@Override
	public void preparePayment(PrepareRequest request) {
		cacheStore.save(request);
	}

	@Override
	@Transactional
	public ApproveSuccessResponse approvePayment(ApproveRequest request) {
		validateAmount(request);

		ApproveSuccessResponse response = paymentClient.approvePayment(request);

		Member foundMember = getMember(request.memberId());

		Payment payment = Payment.complete(
			foundMember,
			response.paymentKey(),
			response.orderId(),
			response.method(),
			response.totalAmount(),
			response.approvedAt().toLocalDateTime(),
			response.receipt().url()
		);

		paymentRepository.save(payment);

		PaymentCompletedEvent paymentCompletedEvent = new PaymentCompletedEvent(foundMember.getId(), request.amount());
		eventPublisher.publish(paymentCompletedEvent);

		return response;
	}

	private void validateAmount(ApproveRequest request) {
		CachedPrepareResponse cachedResponse = cacheStore.findByOrderId(request.orderId());

		if (cachedResponse == null || cachedResponse.amount() == null) {
			throw new PaymentException(PREPARE_DATA_NOT_FOUND);
		}

		Long expectedAmount = cachedResponse.amount();
		Long actualAmount = request.amount();

		PaymentValidator.validateAmount(expectedAmount, actualAmount);
	}

	private Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new PaymentException(MEMBER_NOT_FOUND));
	}
}
