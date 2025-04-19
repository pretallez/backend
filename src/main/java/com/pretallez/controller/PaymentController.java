package com.pretallez.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.response.success.ResSuccessCode;
import com.pretallez.domain.payment.dto.PaymentConfirmRequest;
import com.pretallez.domain.payment.dto.PaymentConfirmResponse;
import com.pretallez.domain.payment.dto.PaymentTempData;
import com.pretallez.domain.payment.service.TossPaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/api/payments")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PaymentController {
	private final TossPaymentService paymentService;

	@PostMapping("/prepare")
	public CustomApiResponse<Void> preparePayment(@RequestBody PaymentTempData paymentTempData) {
		log.info("[Payment][Prepare] orderId={}, amount={}",
			paymentTempData.getOrderId(),
			paymentTempData.getAmount());

		paymentService.savePaymentTempData(paymentTempData);

		return CustomApiResponse.OK(ResSuccessCode.SUCCESS);
	}

	@GetMapping("/confirm")
	public CustomApiResponse<PaymentConfirmResponse> confirmPayment(@ModelAttribute PaymentConfirmRequest request) {
		log.info("[Payment][Confirm] orderId={}, amount={}, paymentKey={}",
			request.orderId(),
			request.amount(),
			request.paymentKey());

		paymentService.validatePaymentAmount(request);

		return CustomApiResponse.OK(ResSuccessCode.SUCCESS, paymentService.confirmPayment(request));
	}
}
