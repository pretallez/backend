package com.pretallez.common.exception;

import com.pretallez.common.response.ResCode;

public class PaymentConfirmException extends BaseException {
	public PaymentConfirmException(ResCode resCode) {
		super(resCode);
	}

	public PaymentConfirmException(ResCode resCode, Throwable cause) {
		super(resCode, cause);
	}
}
