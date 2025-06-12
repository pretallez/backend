package com.pretallez.application.payment.port.output;

public interface PaymentLockManager {

	boolean tryLock(String orderId);
}
