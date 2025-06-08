package com.pretallez.application.payment.port.output;

import java.util.Optional;

import com.pretallez.application.payment.dto.response.ApproveSuccessResponse;

public interface PaymentIdempotencyStore {
	void saveApprovedResponse(String orderId, ApproveSuccessResponse response);
	Optional<ApproveSuccessResponse> findApprovedResponse(String orderId);
}
