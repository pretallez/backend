package com.pretallez.application.payment.port.output;

import com.pretallez.application.payment.dto.request.ApproveRequest;
import com.pretallez.application.payment.dto.response.ApproveSuccessResponse;

public interface PaymentClient {

	ApproveSuccessResponse approvePayment(ApproveRequest request);
}
