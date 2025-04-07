package com.pretallez.common.response.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.pretallez.common.response.ResCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RabbitMqErrorCode implements ResCode {
	MESSEAGE_ACK_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "RabbitMQ 메시지 ACK 처리에 실패했습니다. Queue: %s, 태그: %d"),
	MESSEAGE_NACK_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "RabbitMQ 메시지 NACK 처리에 실패했습니다. Queue: %s, 태그: %d"),

	RABBITMQ_ERROR_MISMATCH_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "RabbitMQ 에러가 발생했습니다. 관리자에게 문의해주세요.");

	private final HttpStatusCode httpStatusCode;
	private final Integer code;
	private final String description;
}
