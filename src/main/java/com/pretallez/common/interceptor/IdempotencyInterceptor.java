package com.pretallez.common.interceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.response.ResCode;
import com.pretallez.common.response.error.PaymentErrorCode;
import com.pretallez.common.response.success.ResSuccessCode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class IdempotencyInterceptor implements HandlerInterceptor {
	private static final String PAYMENT_CACHE_KEY_PREFIX = "payment:cache:";
	private static final String PENDING_STATUS = "PENDING";
	private static final String RESPONSE_CONTENT_TYPE = "application/json;charset=UTF-8";
	private static final long TTL_SECONDS = 600;

	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper objectMapper;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String paymentKey = extractPaymentKey(request, response);

		if (paymentKey == null) {
			return false;
		}

		String redisKey = PAYMENT_CACHE_KEY_PREFIX + paymentKey;
		Object cachedValue = redisTemplate.opsForValue().get(redisKey);

		return handleIdempotency(redisKey, cachedValue, response);
	}

	private boolean handleIdempotency(String redisKey, Object cachedValue, HttpServletResponse response) throws IOException {
		if (isCompletedResponse(cachedValue)) {
			writeSuccessResponse(response, cachedValue);
			return false;
		}

		if (isPendingState(cachedValue)) {
			writeErrorResponse(response, PaymentErrorCode.PAYMENT_PROCESSING);
			return false;
		}

		redisTemplate.opsForValue().set(redisKey, PENDING_STATUS, TTL_SECONDS, TimeUnit.SECONDS);
		return true;
	}

	private boolean isCompletedResponse(Object cachedValue) {
		return cachedValue != null & !PENDING_STATUS.equals(cachedValue);
	}

	private boolean isPendingState(Object cachedValue) {
		return PENDING_STATUS.equals(cachedValue);
	}

	private String extractPaymentKey(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String paymentKey = request.getParameter("paymentKey");
		if (paymentKey == null || paymentKey.isBlank()) {
			PaymentErrorCode paymentErrorCode = PaymentErrorCode.MISSING_PAYMENT_KEY;
			writeErrorResponse(response, paymentErrorCode);
			return null;
		}
		return paymentKey;
	}

	private void writeSuccessResponse(HttpServletResponse response, Object cachedValue) throws IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(RESPONSE_CONTENT_TYPE);
		CustomApiResponse<Object> body = CustomApiResponse.OK(ResSuccessCode.SUCCESS, cachedValue);

		response.getWriter().write(objectMapper.writeValueAsString(body));
	}

	private void writeErrorResponse(HttpServletResponse response, ResCode errorCode) throws IOException {
		response.setStatus(errorCode.getHttpStatusCode().value());
		response.setContentType(RESPONSE_CONTENT_TYPE);
		CustomApiResponse<Void> body = CustomApiResponse.ERROR(errorCode, errorCode.getDescription());

		response.getWriter().write(objectMapper.writeValueAsString(body));
	}

}
