package com.pretallez.application.payment.port.output;

import java.util.Optional;

import com.pretallez.application.payment.dto.request.PendingRequest;
import com.pretallez.application.payment.dto.response.PaymentPendingResponse;

public interface PaymentPendingStore {
	void savePendingPayment(PendingRequest pendingRequest);
	Optional<PaymentPendingResponse> findPendingPayment(String orderId);


}
