package com.pretallez.infrastructure.payment.cache;

import static com.pretallez.common.enums.error.RedisErrorCode.*;

import java.time.Duration;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.pretallez.application.payment.dto.response.ApproveSuccessResponse;
import com.pretallez.application.payment.port.output.PaymentIdempotencyStore;
import com.pretallez.common.exception.RedisException;
import com.pretallez.infrastructure.common.cache.RedisCacheUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisPaymentIdempotencyStore implements PaymentIdempotencyStore {
	private static final String IDEMPOTENT_KEY_PREFIX = "payment:idempotent:";
	private static final Duration TTL = Duration.ofMinutes(10);

	private final RedisCacheUtil redisCacheUtil;

	@Override
	public void saveApprovedResponse(String orderId, ApproveSuccessResponse response) {
		try {
			redisCacheUtil.save(IDEMPOTENT_KEY_PREFIX + orderId, response, TTL);
		} catch (Exception e) {
			log.error("결제 승인 응답 데이터 레디스 저장 실패: orderId={}", orderId, e);
			throw new RedisException(REDIS_SAVE_FAILED, e);
		}
	}

	@Override
	public Optional<ApproveSuccessResponse> findApprovedResponse(String orderId) {
		try {
			ApproveSuccessResponse response = redisCacheUtil.find(
				IDEMPOTENT_KEY_PREFIX + orderId,
				ApproveSuccessResponse.class
			);
			return Optional.ofNullable(response);
		} catch (Exception e) {
			log.error("결제 승인 응답 데이터 레디스 조회 실패: orderId={}", orderId, e);
			throw new RedisException(REDIS_FIND_FAILED, e);
		}
	}
}
