package com.pretallez.application.payment.port.output;

import com.pretallez.infrastructure.payment.dto.CachedPrepareResponse;
import com.pretallez.application.payment.dto.request.PrepareRequest;

public interface PaymentCacheStore {
	void save(PrepareRequest prepareRequest);
	CachedPrepareResponse findByOrderId(String orderId);
}
