package com.pretallez.infrastructure.payment.cache;

import static com.pretallez.application.payment.enums.PaymentErrorCode.*;

import java.time.Duration;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.pretallez.application.payment.dto.request.PendingRequest;
import com.pretallez.application.payment.dto.response.PaymentPendingResponse;
import com.pretallez.application.payment.exception.PaymentException;
import com.pretallez.application.payment.port.output.PaymentPendingStore;
import com.pretallez.infrastructure.common.cache.RedisCacheUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisPaymentPendingStore implements PaymentPendingStore {
	private static final String PENDING_KEY_PREFIX = "payment:pending:";
	private static final Duration TTL = Duration.ofMinutes(10);

	private final RedisCacheUtil redisCacheUtil;

	@Override
	public void savePendingPayment(PendingRequest request) {
		try {
			redisCacheUtil.save(PENDING_KEY_PREFIX + request.orderId(), request, TTL);
		} catch (Exception e) {
			log.error("결제 요청 데이터 레디스 저장 실패: orderId={}", request.orderId(), e);
			throw new PaymentException(PREPARE_FAILED, e);
		}
	}

	@Override
	public Optional<PaymentPendingResponse> findPendingPayment(String orderId) {
		try {
			PaymentPendingResponse response = redisCacheUtil.find(
				PENDING_KEY_PREFIX + orderId,
				PaymentPendingResponse.class
			);
			return Optional.ofNullable(response);
		} catch (Exception e) {
			log.error("결제 요청 데이터 레디스 조회 실패: orderId={}", orderId, e);
			throw new PaymentException(PREPARE_DATA_NOT_FOUND, e);
		}
	}
}
