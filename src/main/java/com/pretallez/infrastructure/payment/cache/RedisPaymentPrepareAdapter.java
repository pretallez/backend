package com.pretallez.infrastructure.payment.cache;

import static com.pretallez.application.payment.enums.PaymentErrorCode.*;

import java.time.Duration;

import org.springframework.stereotype.Component;

import com.pretallez.application.payment.dto.request.PrepareRequest;
import com.pretallez.application.payment.exception.PaymentException;
import com.pretallez.application.payment.port.output.PaymentPrepareStore;
import com.pretallez.application.payment.port.output.PaymentIdempotencyStore;
import com.pretallez.infrastructure.common.cache.RedisCacheUtil;
import com.pretallez.infrastructure.payment.dto.ApproveSuccessResponse;
import com.pretallez.infrastructure.payment.dto.CachedPrepareResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisPaymentPrepareAdapter implements PaymentPrepareStore, PaymentIdempotencyStore {
	private static final String PREPARE_KEY_PREFIX = "payment:temp:";
	private static final String IDEMPOTENT_KEY_PREFIX = "payment:idempotent:";
	private static final Duration TTL = Duration.ofMinutes(10);

	private final RedisCacheUtil redisCacheUtil;

	@Override
	public void savePrepareRequest(PrepareRequest request) {
		try {
			redisCacheUtil.save(PREPARE_KEY_PREFIX + request.orderId(), request, TTL);
		} catch (Exception e) {
			throw new PaymentException(PREPARE_FAILED, e);
		}
	}

	@Override
	public CachedPrepareResponse findPreparedRequest(String orderId) {
		try {
			return redisCacheUtil.find(PREPARE_KEY_PREFIX + orderId, CachedPrepareResponse.class);
		} catch (Exception e) {
			throw new PaymentException(PREPARE_DATA_NOT_FOUND, e);
		}
	}

	@Override
	public void saveApprovedResponse(String orderId, ApproveSuccessResponse response) {
		try {
			redisCacheUtil.save(IDEMPOTENT_KEY_PREFIX + response.orderId(), response, TTL);
		} catch (Exception e) {
			throw new PaymentException(PREPARE_FAILED, e);
		}
	}

	@Override
	public ApproveSuccessResponse findApprovedResponse(String orderId) {
		try {
			return redisCacheUtil.find(IDEMPOTENT_KEY_PREFIX + orderId, ApproveSuccessResponse.class);
		} catch (Exception e) {
			throw new PaymentException(PREPARE_DATA_NOT_FOUND, e);
		}
	}
}
