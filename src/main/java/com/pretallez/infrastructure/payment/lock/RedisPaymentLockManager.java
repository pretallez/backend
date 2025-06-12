package com.pretallez.infrastructure.payment.lock;

import java.time.Duration;

import org.springframework.stereotype.Component;

import com.pretallez.application.payment.port.output.PaymentLockManager;
import com.pretallez.infrastructure.common.lock.RedisLockUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisPaymentLockManager implements PaymentLockManager {
	private static final String KEY_PREFIX= "payment:lock:";
	private static final Duration TTL = Duration.ofSeconds(3);

	private final RedisLockUtil lockUtil;

	@Override
	public boolean tryLock(String orderId) {
		return lockUtil.tryLock(KEY_PREFIX + orderId, TTL);
	}
}
