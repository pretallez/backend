package com.pretallez.application.payment.service;

import static com.pretallez.application.payment.enums.PaymentErrorCode.*;
import static com.pretallez.common.enums.error.EntityErrorCode.*;

import org.springframework.stereotype.Service;

import com.pretallez.application.payment.dto.request.ApproveRequest;
import com.pretallez.application.payment.dto.request.PendingRequest;
import com.pretallez.application.payment.dto.response.ApproveSuccessResponse;
import com.pretallez.application.payment.dto.response.PaymentPendingResponse;
import com.pretallez.application.payment.exception.PaymentException;
import com.pretallez.application.payment.port.input.PaymentUseCase;
import com.pretallez.application.payment.port.output.PaymentClient;
import com.pretallez.application.payment.port.output.PaymentIdempotencyStore;
import com.pretallez.application.payment.port.output.PaymentLockManager;
import com.pretallez.application.payment.port.output.PaymentPendingStore;
import com.pretallez.application.payment.port.output.PaymentRecoveryStore;
import com.pretallez.domain.common.event.DomainEventPublisher;
import com.pretallez.domain.member.entity.Member;
import com.pretallez.domain.member.repository.MemberRepository;
import com.pretallez.domain.payment.entity.Payment;
import com.pretallez.domain.payment.event.PaymentCompletedEvent;
import com.pretallez.domain.payment.policy.PaymentPolicy;
import com.pretallez.infrastructure.payment.repository.PaymentSaver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {
	private final PaymentLockManager lockManager;

	private final PaymentClient paymentClient;

	private final PaymentPendingStore pendingStore;
	private final PaymentIdempotencyStore idempotencyStore;
	private final PaymentRecoveryStore recoveryStore;

	private final MemberRepository memberRepository;
	private final PaymentSaver paymentSaver;

	private final DomainEventPublisher eventPublisher;

	@Override
	public void preparePayment(PendingRequest request) {
		pendingStore.savePendingPayment(request);
	}

	@Override
	public ApproveSuccessResponse approvePayment(ApproveRequest request) {
		checkDuplicateRequest(request.orderId(), request.memberId());

		return idempotencyStore.findApprovedResponse(request.orderId())
			.orElseGet(() -> processPaymentApproval(request));
	}

	private void checkDuplicateRequest(String orderId, Long memberId) {
		if (!lockManager.tryLock(orderId)) {
			log.warn("결제 승인 중 중복 요청 차단: orderId={}, memberId={}", orderId, memberId);
			throw new PaymentException(PAYMENT_ALREADY_IN_PROGRESS);
		}
	}

	private ApproveSuccessResponse processPaymentApproval(ApproveRequest request) {
		String orderId = request.orderId();

		validateAmount(request);

		recoveryStore.enqueueForRecovery(orderId, request);

		ApproveSuccessResponse response = paymentClient.approvePayment(request);
		saveIdempotentResponse(orderId, response);

		Payment payment = createPaymentFromResponse(request, response);
		paymentSaver.savePayment(payment);

		publishPaymentCompleledEvent(payment);

		removeFromRecoveryQueue(orderId);

		return response;
	}

	private void saveIdempotentResponse(String orderId, ApproveSuccessResponse response) {
		try {
			idempotencyStore.saveApprovedResponse(orderId, response);
		} catch (Exception e) {
			// 실패해도 예외 무시하고 진행
			log.warn("멱등 응답 저장 실패: orderId={}", orderId, e);
		}
	}

	private void removeFromRecoveryQueue(String orderId) {
		try {
			recoveryStore.remove(orderId);
		} catch (Exception e) {
			// 실패해도 예외 무시하고 진행
			log.warn("복구 큐 데이터 삭제 실패: orderId={}", orderId, e);
		}
	}

	private void publishPaymentCompleledEvent(Payment payment) {
		PaymentCompletedEvent paymentCompletedEvent = new PaymentCompletedEvent(
			payment.getMember().getId(),
			payment.getAmount()
		);
		eventPublisher.publish(paymentCompletedEvent);
	}

	private Payment createPaymentFromResponse(ApproveRequest request, ApproveSuccessResponse response) {
		Member member = getMember(request.memberId());
		return Payment.complete(
			member,
			response.paymentKey(),
			response.orderId(),
			response.method(),
			response.totalAmount(),
			response.approvedAt().toLocalDateTime(),
			response.receipt().url()
		);
	}

	private void validateAmount(ApproveRequest request) {
		PaymentPendingResponse response = pendingStore.findPendingPayment(request.orderId())
			.orElseThrow(() -> new PaymentException(PREPARE_DATA_NOT_FOUND));

		PaymentPolicy.validateAmount(response.amount(), request.amount());
	}

	private Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new PaymentException(MEMBER_NOT_FOUND));
	}
}
