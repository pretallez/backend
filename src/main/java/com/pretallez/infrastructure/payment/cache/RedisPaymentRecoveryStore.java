package com.pretallez.infrastructure.payment.cache;

import static com.pretallez.common.enums.error.RedisErrorCode.*;

import java.time.Duration;

import org.springframework.stereotype.Component;

import com.pretallez.application.payment.dto.request.ApproveRequest;
import com.pretallez.application.payment.port.output.PaymentRecoveryStore;
import com.pretallez.common.exception.RedisException;
import com.pretallez.infrastructure.common.cache.RedisCacheUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisPaymentRecoveryStore implements PaymentRecoveryStore {
	private static final String PENDING_KEY_PREFIX = "payment:recovery:";
	private static final Duration TTL = Duration.ofDays(7);

	private final RedisCacheUtil redisCacheUtil;

	@Override
	public void enqueueForRecovery(String orderId, ApproveRequest request) {
		try {
			redisCacheUtil.save(PENDING_KEY_PREFIX + orderId, request, TTL);
		} catch (Exception e) {
			log.error("결제 복구 데이터 레디스 저장 실패: orderId={}", orderId, e);
			throw new RedisException(REDIS_SAVE_FAILED, e);
		}
	}

	@Override
	public void remove(String orderId) {
		try {
			redisCacheUtil.delete(PENDING_KEY_PREFIX + orderId);
		} catch (Exception e) {
			log.error("결제 복구 데이터 레디스 삭제 실패: orderId={}", orderId, e);
			throw new RedisException(REDIS_DELETE_FAILED, e);
		}
	}
}
