package com.pretallez.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pretallez.application.payment.dto.request.ApproveRequest;
import com.pretallez.application.payment.dto.request.PrepareRequest;
import com.pretallez.application.payment.port.input.PaymentUseCase;
import com.pretallez.common.enums.success.ResSuccessCode;
import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.infrastructure.payment.dto.ApproveSuccessResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/api/payments")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PaymentController {
	private final PaymentUseCase paymentUseCase;

	@PostMapping("/prepare")
	public CustomApiResponse<Void> preparePayment(@RequestBody PrepareRequest request) {
		paymentUseCase.preparePayment(request);
		return CustomApiResponse.OK(ResSuccessCode.SUCCESS);
	}

	@GetMapping("/approve")
	public CustomApiResponse<ApproveSuccessResponse> approvePayment(@ModelAttribute ApproveRequest request) {
		return CustomApiResponse.OK(
			ResSuccessCode.SUCCESS,
			paymentUseCase.approvePayment(request)
		);
	}
}
