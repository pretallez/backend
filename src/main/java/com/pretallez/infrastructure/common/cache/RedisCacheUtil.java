package com.pretallez.infrastructure.common.cache;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.common.enums.error.RedisErrorCode;
import com.pretallez.common.exception.RedisException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisCacheUtil {

	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper objectMapper;

	public <T> void save(String key, T value, Duration ttl) {
		try {
			redisTemplate.opsForValue().set(key, value, ttl);
		} catch (Exception e) {
			throw new RedisException(RedisErrorCode.REDIS_SAVE_FAILED);
		}
	}

	public <T> T find(String key, Class<T> type) {
		try {
			Object raw = redisTemplate.opsForValue().get(key);
			return objectMapper.convertValue(raw, type);
		} catch (Exception e) {
			throw new RedisException(RedisErrorCode.REDIS_FIND_FAILED);
		}
	}
}
