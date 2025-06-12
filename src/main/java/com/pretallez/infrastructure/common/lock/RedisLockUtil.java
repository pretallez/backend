package com.pretallez.infrastructure.common.lock;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisLockUtil {

	private final RedisTemplate<String, Object> redisTemplate;

	public boolean tryLock(String key, Duration ttl) {
		Boolean result = redisTemplate.opsForValue().setIfAbsent(key, "lock", ttl);
		return Boolean.TRUE.equals(result);
	}

	public void unlock(String key) {
		redisTemplate.delete(key);
	}
}
