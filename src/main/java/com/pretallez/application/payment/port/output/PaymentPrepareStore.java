package com.pretallez.application.payment.port.output;

import com.pretallez.application.payment.dto.request.PrepareRequest;
import com.pretallez.infrastructure.payment.dto.CachedPrepareResponse;

public interface PaymentPrepareStore {
	void savePrepareRequest(PrepareRequest prepareRequest);
	CachedPrepareResponse findPreparedRequest(String orderId);


}
