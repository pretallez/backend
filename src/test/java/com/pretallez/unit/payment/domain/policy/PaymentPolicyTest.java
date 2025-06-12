package com.pretallez.unit.payment.domain.policy;

import static com.pretallez.application.payment.enums.PaymentErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.pretallez.application.payment.exception.PaymentException;
import com.pretallez.domain.payment.policy.PaymentPolicy;

class PaymentPolicyTest {

	@Test
	void 결제_금액은_100원_이상이어야_한다() {
		// given
		Long expectedAmount = 50_000L;
		Long actualAmount = 99L;

		// then
		PaymentException exception = assertThrows(PaymentException.class, () ->
			PaymentPolicy.validateAmount(expectedAmount, actualAmount)
		);
		assertThat(exception.getResCode()).isEqualTo(INVALID_AMOUNT);
	}

	@Test
	void 예상_금액과_실제_금액이_일치하지_않으면_예외가_발생한다() {
		// given
		Long expectedAmount = 50_000L;
		Long actualAmount = 100_000L;

		// then
		PaymentException exception = assertThrows(PaymentException.class, () ->
			PaymentPolicy.validateAmount(expectedAmount, actualAmount)
		);
		assertThat(exception.getResCode()).isEqualTo(AMOUNT_MISMATCH);
	}

}