package com.pretallez.application.payment.port.input;

import com.pretallez.application.payment.dto.request.ApproveRequest;
import com.pretallez.application.payment.dto.request.PrepareRequest;
import com.pretallez.infrastructure.payment.dto.ApproveSuccessResponse;

public interface PaymentUseCase {
	// Command
	void preparePayment(PrepareRequest request);
	ApproveSuccessResponse approvePayment(ApproveRequest request);
}
