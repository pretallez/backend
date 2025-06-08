package com.pretallez.application.payment.port.output;

import com.pretallez.application.payment.dto.request.ApproveRequest;

public interface PaymentRecoveryStore {

	void enqueueForRecovery(String orderId, ApproveRequest request);
	void remove(String orderId);
}
