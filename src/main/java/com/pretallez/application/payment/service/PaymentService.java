package com.pretallez.application.payment.service;

import static com.pretallez.application.payment.enums.PaymentErrorCode.*;
import static com.pretallez.common.enums.error.EntityErrorCode.*;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pretallez.application.payment.dto.request.ApproveRequest;
import com.pretallez.application.payment.dto.request.PrepareRequest;
import com.pretallez.application.payment.exception.PaymentException;
import com.pretallez.application.payment.port.input.PaymentUseCase;
import com.pretallez.application.payment.port.output.PaymentPrepareStore;
import com.pretallez.application.payment.port.output.PaymentClient;
import com.pretallez.application.payment.port.output.PaymentIdempotencyStore;
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
	private final PaymentPrepareStore cacheStore;
	private final PaymentIdempotencyStore idempotencyStore;

	private final PaymentRepository paymentRepository;
	private final MemberRepository memberRepository;

	private final DomainEventPublisher eventPublisher;

	@Override
	public void preparePayment(PrepareRequest request) {
		cacheStore.savePrepareRequest(request);
	}

	@Override
	public ApproveSuccessResponse approvePayment(ApproveRequest request) {
		ApproveSuccessResponse existingResponse = idempotencyStore.findApprovedResponse(request.orderId());
		if (existingResponse != null) {
			return existingResponse;
		}

		validateAmount(request);

		ApproveSuccessResponse response = executePaymentAndSave(request);

		PaymentCompletedEvent paymentCompletedEvent = new PaymentCompletedEvent(request.memberId(), request.amount());
		eventPublisher.publish(paymentCompletedEvent);

		idempotencyStore.saveApprovedResponse(request.orderId(), response);
		return response;
	}

	@Transactional
	private ApproveSuccessResponse executePaymentAndSave(ApproveRequest request) {
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

		try {
			paymentRepository.save(payment);
			return response;
		} catch (DataIntegrityViolationException e) {
			throw new PaymentException(DUPLICATE_ORDER_ID);
		}
	}

	private void validateAmount(ApproveRequest request) {
		CachedPrepareResponse cachedResponse = cacheStore.findPreparedRequest(request.orderId());

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
