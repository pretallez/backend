package com.pretallez.application.payment.port.output;

import com.pretallez.application.payment.dto.request.ApproveRequest;
import com.pretallez.infrastructure.payment.dto.ApproveSuccessResponse;

public interface PaymentClient {

	ApproveSuccessResponse approvePayment(ApproveRequest request);
}
