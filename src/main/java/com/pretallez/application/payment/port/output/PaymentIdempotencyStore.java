package com.pretallez.application.payment.port.output;

import com.pretallez.infrastructure.payment.dto.ApproveSuccessResponse;

public interface PaymentIdempotencyStore {
	void saveApprovedResponse(String orderId, ApproveSuccessResponse response);
	ApproveSuccessResponse findApprovedResponse(String orderId);
}
