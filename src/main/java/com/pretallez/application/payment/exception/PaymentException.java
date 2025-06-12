package com.pretallez.application.payment.exception;

import com.pretallez.common.exception.BaseException;
import com.pretallez.common.response.ResCode;

public class PaymentException extends BaseException {
	public PaymentException(ResCode resCode) {
		super(resCode);
	}

	public PaymentException(ResCode resCode, Throwable cause) {
		super(resCode, cause);
	}
}
