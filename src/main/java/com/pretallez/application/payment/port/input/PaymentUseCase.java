package com.pretallez.application.payment.port.input;

import com.pretallez.application.payment.dto.request.ApproveRequest;
import com.pretallez.application.payment.dto.request.PendingRequest;
import com.pretallez.application.payment.dto.response.ApproveSuccessResponse;

public interface PaymentUseCase {
	// Command
	void preparePayment(PendingRequest request);
	ApproveSuccessResponse approvePayment(ApproveRequest request);
}
