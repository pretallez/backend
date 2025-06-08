package com.pretallez.domain.payment.policy;

import static com.pretallez.application.payment.enums.PaymentErrorCode.*;

import com.pretallez.application.payment.enums.PaymentErrorCode;
import com.pretallez.application.payment.exception.PaymentException;

public class PaymentPolicy {

	public static void validateAmount(long expectedAmount, long actualAmount) {
		if (actualAmount < 100) {
			throw new PaymentException(PaymentErrorCode.INVALID_AMOUNT);
		}
		if (expectedAmount != actualAmount) {
			throw new PaymentException(AMOUNT_MISMATCH);
		}
	}
}
