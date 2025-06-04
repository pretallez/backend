package com.pretallez.application.payment.enums;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.pretallez.common.response.ResCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ResCode {
	PAYMENT_KEY_REQUIRED(HttpStatus.BAD_REQUEST, 10000, "paymentKey 파라미터는 필수 값입니다."),
	AMOUNT_MISMATCH(HttpStatus.BAD_REQUEST, 10000, "결제 금액이 결제 요청 금액과 일치하지 않습니다."),
	PREPARE_DATA_NOT_FOUND(HttpStatus.NOT_FOUND, 10000, "결제 요청 데이터가 존재하지 않습니다."),
	PREPARE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 10000, "결제 준비에 실패했습니다. 잠시 후 다시 시도해주세요."),
	APPROVAL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 10000, "결제 승인에 실패했습니다. 잠시 후 다시 시도해주세요."),

	CONFIRM_MISMATCH_INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 10000, "결제 과정에서 서버 에러가 발생했습니다. 관리자에게 문의해주세요.");

	private final HttpStatusCode httpStatusCode;
	private final Integer code;
	private final String description;
}
