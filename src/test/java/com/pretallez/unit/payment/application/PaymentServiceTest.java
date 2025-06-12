package com.pretallez.unit.payment.application;

import static com.pretallez.application.payment.enums.PaymentErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pretallez.application.payment.dto.request.ApproveRequest;
import com.pretallez.application.payment.dto.response.ApproveSuccessResponse;
import com.pretallez.application.payment.dto.response.PaymentPendingResponse;
import com.pretallez.application.payment.dto.response.Receipt;
import com.pretallez.application.payment.exception.PaymentException;
import com.pretallez.application.payment.port.output.PaymentClient;
import com.pretallez.application.payment.port.output.PaymentIdempotencyStore;
import com.pretallez.application.payment.port.output.PaymentLockManager;
import com.pretallez.application.payment.port.output.PaymentPendingStore;
import com.pretallez.application.payment.port.output.PaymentRecoveryStore;
import com.pretallez.application.payment.service.PaymentService;
import com.pretallez.domain.common.event.DomainEventPublisher;
import com.pretallez.domain.member.entity.Member;
import com.pretallez.domain.member.enums.MannerLevel;
import com.pretallez.domain.member.repository.MemberRepository;
import com.pretallez.domain.payment.enums.PaymentStatus;
import com.pretallez.infrastructure.payment.repository.PaymentSaver;

class PaymentServiceTest {

	private PaymentService sut;

	private PaymentLockManager lockManager;
	private PaymentClient paymentClient;
	private PaymentPendingStore pendingStore;
	private PaymentIdempotencyStore idempotencyStore;
	private PaymentRecoveryStore recoveryStore;
	private MemberRepository memberRepository;
	private PaymentSaver paymentSaver;
	private DomainEventPublisher eventPublisher;

	@BeforeEach
	void setUp() {
		lockManager = mock(PaymentLockManager.class);
		paymentClient = mock(PaymentClient.class);
		pendingStore = mock(PaymentPendingStore.class);
		idempotencyStore = mock(PaymentIdempotencyStore.class);
		recoveryStore = mock(PaymentRecoveryStore.class);
		memberRepository = mock(MemberRepository.class);
		paymentSaver = mock(PaymentSaver.class);
		eventPublisher = mock(DomainEventPublisher.class);

		sut = new PaymentService(
			lockManager,
			paymentClient,
			pendingStore,
			idempotencyStore,
			recoveryStore,
			memberRepository,
			paymentSaver,
			eventPublisher
		);
	}

	@Test
	void 결제_승인_요청이_정상_처리되고_저장된다() {
		// given
		PaymentPendingResponse pendingResponse = new PaymentPendingResponse(
			"order-1",
			50_000L
		);

		ApproveRequest approveRequest = new ApproveRequest(
			"order-1",
			50_000L,
			"payment-1",
			1L
		);

		ApproveSuccessResponse approveSuccessResponse = new ApproveSuccessResponse(
			"payment-1",
			"order-1",
			"CARD",
			50_000L,
			OffsetDateTime.now(), new Receipt("url.com"),
			PaymentStatus.DONE
		);

		Member member = Member.of(
			"jylim@pretallez.com",
			"펜싱 고수",
			"임종엽",
			MannerLevel.BEGINNER,
			"01012345678",
			"M",
			0
		);

		// when
		when(lockManager.tryLock("order-1")).thenReturn(true);
		when(pendingStore.findPendingPayment("order-1")).thenReturn(Optional.of(pendingResponse));
		when(paymentClient.approvePayment(approveRequest)).thenReturn(approveSuccessResponse);
		when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

		ApproveSuccessResponse response = sut.approvePayment(approveRequest);

		// then
		assertThat(response).isEqualTo(approveSuccessResponse);
		verify(paymentSaver).savePayment(any());
		verify(eventPublisher).publish(any());
		verify(recoveryStore).remove("order-1");
	}

	@Test
	void 결제_중복_요청_시_예외_발생하고_API가_호출되지_않는다() {
		// given
		ApproveRequest approveRequest = new ApproveRequest(
			"order-1",
			50_000L,
			"payment-1",
			1L
		);

		// when
		when(lockManager.tryLock("order-1")).thenReturn(false);

		// then
		PaymentException exception = assertThrows(PaymentException.class, () ->
			sut.approvePayment(approveRequest)
		);
		assertThat(exception.getResCode()).isEqualTo(PAYMENT_ALREADY_IN_PROGRESS);
		verify(paymentClient, never()).approvePayment(any());
		verify(paymentSaver, never()).savePayment(any());
		verify(eventPublisher, never()).publish(any());
	}

	@Test
	void 결제_승인_요청_시_멱등성_응답_존재하면_API_호출_없이_반환된다() {
		// given
		ApproveRequest request = new ApproveRequest(
			"order-1",
			50_000L,
			"payment-1",
			1L
		);

		ApproveSuccessResponse approveSuccessResponse = new ApproveSuccessResponse(
			"payment-1",
			"order-1",
			"CARD",
			50_000L,
			OffsetDateTime.now(), new Receipt("url.com"),
			PaymentStatus.DONE
		);

		// when
		when(lockManager.tryLock("order-1")).thenReturn(true);
		when(idempotencyStore.findApprovedResponse("order-1")).thenReturn(
			Optional.of(approveSuccessResponse)
		);
		ApproveSuccessResponse response = sut.approvePayment(request);

		// then
		assertThat(response).isEqualTo(approveSuccessResponse);
		verify(paymentClient, never()).approvePayment(any());
	}

	@Test
	void 멱등_저장_실패해도_결제_승인은_정상_처리된다() {
		// given
		PaymentPendingResponse pendingResponse = new PaymentPendingResponse(
			"order-1",
			50_000L
		);

		ApproveRequest request = new ApproveRequest(
			"order-1",
			50_000L,
			"payment-1",
			1L
		);

		ApproveSuccessResponse approveSuccessResponse = new ApproveSuccessResponse(
			"payment-1",
			"order-1",
			"CARD",
			50_000L,
			OffsetDateTime.now(), new Receipt("url.com"),
			PaymentStatus.DONE
		);

		Member member = Member.of(
			"jylim@pretallez.com",
			"펜싱 고수",
			"임종엽",
			MannerLevel.BEGINNER,
			"01012345678",
			"M",
			0
		);

		// when
		when(lockManager.tryLock("order-1")).thenReturn(true);
		when(pendingStore.findPendingPayment("order-1")).thenReturn(Optional.of(pendingResponse));
		when(paymentClient.approvePayment(request)).thenReturn(approveSuccessResponse);
		when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
		doThrow(new PaymentException(PREPARE_FAILED))
			.when(idempotencyStore).saveApprovedResponse("order-1", approveSuccessResponse);

		ApproveSuccessResponse response = sut.approvePayment(request);

		// then
		assertThat(response).isEqualTo(approveSuccessResponse);
		verify(paymentSaver).savePayment(any());
		verify(eventPublisher).publish(any());
		verify(recoveryStore).remove("order-1");
	}

	@Test
	void 복구_큐_저장_실패시_예외를_떠지고_승인을_진행하지_않는다() {
		// given
		ApproveRequest request = new ApproveRequest(
			"order-1",
			50_000L,
			"payment-1",
			1L
		);

		// when
		when(lockManager.tryLock(any())).thenReturn(true);
		when(pendingStore.findPendingPayment("order-1")).thenReturn(
			Optional.of(new PaymentPendingResponse("order-1", 50_000L))
		);
		doThrow(new PaymentException(APPROVAL_FAILED)).when(recoveryStore).enqueueForRecovery("order-1", request);

		// then
		PaymentException exception = assertThrows(PaymentException.class, () ->
			sut.approvePayment(request)
		);
		assertThat(exception.getResCode()).isEqualTo(APPROVAL_FAILED);
		verify(paymentClient, never()).approvePayment(any());
		verify(paymentSaver, never()).savePayment(any());
		verify(eventPublisher, never()).publish(any());
	}

	@Test
	void 결제_금액_불일치_시_예외가_발생하고_승인_API가_호출되지_않는다() {
		// given
		PaymentPendingResponse pendingResponse = new PaymentPendingResponse(
			"order-1",
			50_000L
		);

		ApproveRequest request = new ApproveRequest(
			"order-1",
			100_000L,
			"payment-1",
			1L
		);

		// when
		when(lockManager.tryLock("order-1")).thenReturn(true);
		when(pendingStore.findPendingPayment("order-1")).thenReturn(Optional.of(pendingResponse));

		// then
		PaymentException exception = assertThrows(PaymentException.class, () ->
			sut.approvePayment(request)
		);
		assertThat(exception.getResCode()).isEqualTo(AMOUNT_MISMATCH);
		verify(paymentClient, never()).approvePayment(any());
		verify(paymentSaver, never()).savePayment(any());
		verify(eventPublisher, never()).publish(any());
	}
}