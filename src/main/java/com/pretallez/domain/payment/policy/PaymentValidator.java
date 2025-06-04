package com.pretallez.domain.payment.policy;

import com.pretallez.application.payment.enums.PaymentErrorCode;
import com.pretallez.application.payment.exception.PaymentException;

public class PaymentValidator {

	public static void validateAmount(long expectedAmount, long actualAmount) {
		if (expectedAmount != actualAmount) {
			throw new PaymentException(PaymentErrorCode.AMOUNT_MISMATCH);
		}
	}
}
