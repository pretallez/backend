package com.pretallez.domain.payment.service;

import java.io.IOException;
import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.common.config.properties.PaymentProperties;
import com.pretallez.common.exception.PaymentConfirmException;
import com.pretallez.common.response.ResCode;
import com.pretallez.common.response.error.PaymentErrorCode;
import com.pretallez.common.util.EnumUtils;
import com.pretallez.domain.payment.dto.PaymentConfirm;
import com.pretallez.domain.payment.dto.PaymentTempData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TossPaymentService implements PaymentService {
	private static final String PAYMENT_PREFIX = "payment:temp:";
	private static final int PAYMENT_TEMP_DATA_EXPIRE_SECONDS = 600;

	private final RestClient paymentRestClient;
	private final PaymentProperties paymentProperties;
	private final ObjectMapper objectMapper;
	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public PaymentConfirm.Response confirmPayment(PaymentConfirm.Request confirmRequest) {
		return paymentRestClient.method(HttpMethod.POST)
			.uri(paymentProperties.getConfirmEndpoint())
			.body(confirmRequest)
			.retrieve()
			.onStatus(HttpStatusCode::isError, (request, response) -> {
				throw new PaymentConfirmException(getPaymentConfirmErrorCode(response));
			})
			.body(PaymentConfirm.Response.class);
	}

	@Override
	public void savePaymentTempData(PaymentTempData paymentTempData) {
		String key = PAYMENT_PREFIX + paymentTempData.getOrderId();

		try {
			redisTemplate.opsForValue().set(key, paymentTempData, Duration.ofMinutes(PAYMENT_TEMP_DATA_EXPIRE_SECONDS));
		} catch (Exception e) {
			throw new PaymentConfirmException(PaymentErrorCode.PREPARE_PAYMENT_FAIL, e);
		}
	}

	/**
	 * Toss 결제 승인 응답에서 실패 코드를 파싱하여 PaymentErrorCode로 변환한다.
	 *
	 * @param response     Toss 결제 승인 응답 객체
	 * @return             매핑된 PaymentErrorCode
	 */
	private PaymentErrorCode getPaymentConfirmErrorCode(ClientHttpResponse response) throws IOException {
		PaymentConfirm.FailResponse paymentConfirmFailOutput = objectMapper.readValue(
			response.getBody(),
			PaymentConfirm.FailResponse.class
		);

		return toPaymentErrorCode(
			EnumUtils.findByNameOrThrow(PaymentErrorCode.class, paymentConfirmFailOutput.code())
		);
	}

	/**
	 * ResCode를 PaymentErrorCode로 변환한다.
	 * 지정되지 않은 코드일 경우 기본 에러 코드로 매핑된다.
	 *
	 * @param code  응답 코드
	 * @return      변환된 PaymentErrorCode
	 */
	private PaymentErrorCode toPaymentErrorCode(ResCode code) {
		return code instanceof PaymentErrorCode
			? (PaymentErrorCode) code
			: PaymentErrorCode.PAYMENT_CONFIRM_ERROR_MISMATCH_ERROR;
	}
}