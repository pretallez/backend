package com.pretallez.infrastructure.payment.cache;

import static com.pretallez.application.payment.enums.PaymentErrorCode.*;

import java.time.Duration;

import org.springframework.stereotype.Component;

import com.pretallez.application.payment.dto.request.PrepareRequest;
import com.pretallez.application.payment.exception.PaymentException;
import com.pretallez.application.payment.port.output.PaymentCacheStore;
import com.pretallez.infrastructure.common.cache.RedisCacheUtil;
import com.pretallez.infrastructure.payment.dto.CachedPrepareResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisPaymentCacheStore implements PaymentCacheStore {
	private static final String REDIS_KEY_PREFIX = "payment:temp:";
	private static final Duration TTL = Duration.ofMinutes(10);

	private final RedisCacheUtil redisCacheUtil;

	@Override
	public void save(PrepareRequest request) {
		try {
			redisCacheUtil.save(key(request.orderId()), request, TTL);
		} catch (Exception e) {
			throw new PaymentException(PREPARE_FAILED, e);
		}

	}

	@Override
	public CachedPrepareResponse findByOrderId(String orderId) {
		try {
			return redisCacheUtil.find(key(orderId), CachedPrepareResponse.class);
		} catch (Exception e) {
			throw new PaymentException(PREPARE_DATA_NOT_FOUND, e);
		}

	}

	private String key(String orderId) {
		return REDIS_KEY_PREFIX + orderId;
	}
}
