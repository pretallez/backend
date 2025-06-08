package com.pretallez.infrastructure.payment.client;

import static com.pretallez.application.payment.enums.TossErrorCode.*;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.application.payment.dto.request.ApproveRequest;
import com.pretallez.application.payment.exception.PaymentException;
import com.pretallez.application.payment.port.output.PaymentClient;
import com.pretallez.common.enums.error.RedisErrorCode;
import com.pretallez.common.exception.RedisException;
import com.pretallez.common.response.ResCode;
import com.pretallez.common.util.EnumUtils;
import com.pretallez.infrastructure.payment.config.PaymentProperties;
import com.pretallez.application.payment.dto.response.ApproveFailResponse;
import com.pretallez.application.payment.dto.response.ApproveSuccessResponse;
import com.pretallez.application.payment.enums.TossErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TossPaymentClient implements PaymentClient {
	private final RestClient restClient;
	private final PaymentProperties paymentProperties;
	private final ObjectMapper objectMapper;

	public TossPaymentClient(@Qualifier("paymentClient") RestClient restClient, PaymentProperties paymentProperties, ObjectMapper objectMapper) {
		this.restClient = restClient;
		this.paymentProperties = paymentProperties;
		this.objectMapper = objectMapper;
	}

	@Override
	public ApproveSuccessResponse approvePayment(ApproveRequest request) {
		String json = restClient.method(HttpMethod.POST)
			.uri(paymentProperties.getConfirmEndpoint())
			.body(request)
			.header("Idempotency-Key", request.paymentKey())
			.retrieve()
			.onStatus(HttpStatusCode::isError, (req, res) -> {
				throw new PaymentException(parseErrorCodeFromResponse(res));
			})
			.body(String.class);

		return deserialize(json);
	}

	private ApproveSuccessResponse deserialize(String json) {
		log.info("토스페이먼츠 결제 승인 응답 데이터: {}", json);

		try {
			return objectMapper.readValue(json, ApproveSuccessResponse.class);
		} catch (IOException e) {
			throw new RedisException(RedisErrorCode.REDIS_JSON_DESERIALIZATION_FAILED, e);
		}
	}

	private TossErrorCode parseErrorCodeFromResponse(ClientHttpResponse response) throws IOException {
		ApproveFailResponse paymentFailResponse = objectMapper.readValue(
			response.getBody(),
			ApproveFailResponse.class
		);

		return mapToPaymentErrorCode(EnumUtils.findByNameOrThrow(
			TossErrorCode.class,
			paymentFailResponse.code())
		);
	}

	private TossErrorCode mapToPaymentErrorCode(ResCode code) {
		return code instanceof TossErrorCode ? (TossErrorCode) code : UNKNOWN_PAYMENT_ERROR;
	}
}
