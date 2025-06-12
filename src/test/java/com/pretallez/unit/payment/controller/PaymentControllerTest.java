package com.pretallez.unit.payment.controller;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.application.payment.dto.request.ApproveRequest;
import com.pretallez.application.payment.dto.request.PendingRequest;
import com.pretallez.application.payment.dto.response.ApproveSuccessResponse;
import com.pretallez.application.payment.dto.response.Receipt;
import com.pretallez.application.payment.port.input.PaymentUseCase;
import com.pretallez.controller.PaymentController;
import com.pretallez.domain.payment.enums.PaymentStatus;

@ExtendWith(RestDocumentationExtension.class)
class PaymentControllerTest {

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	private PaymentUseCase paymentUseCase;

	@BeforeEach
	void setUp(RestDocumentationContextProvider restDocumentation) {
		paymentUseCase = mock(PaymentUseCase.class);
		objectMapper = new ObjectMapper();

		PaymentController sut = new PaymentController(paymentUseCase);
		mockMvc = MockMvcBuilders.standaloneSetup(sut)
			.apply(documentationConfiguration(restDocumentation))
			.build();
	}

	@Test
	void 결제_준비_요청이_성공하면_200을_반환한다() throws Exception {
		PendingRequest request = new PendingRequest("order-1", 50_000L);

		mockMvc.perform(post("/v1/api/payments/prepare")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)))
			.andDo(print())
			.andExpect(status().isOk())

			.andDo(document("payments/prepare",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				requestFields(
					fieldWithPath("orderId").description("주문 식별번호"),
					fieldWithPath("amount").description("결제 요청 금액(최종 금액)")
				),
				responseFields(
					fieldWithPath("httpStatusCode").description("HTTP 상태 코드"),
					fieldWithPath("code").description("응답 코드"),
					fieldWithPath("message").description("응답 메시지"),
					fieldWithPath("description").description("응답 메시지 설명")
				)
			));

		verify(paymentUseCase).preparePayment(request);
	}

	@Test
	void 결제_승인_요청이_성공하면_200을_반환한다() throws Exception {
		ApproveRequest request = new ApproveRequest("order-1", 50_000L, "payment-key-1", 1L);

		ApproveSuccessResponse approveSuccessResponse = new ApproveSuccessResponse(
			"payment-1",
			"order-1",
			"CARD",
			50_000L,
			OffsetDateTime.now(), new Receipt("url.com"),
			PaymentStatus.DONE
		);

		when(paymentUseCase.approvePayment(request)).thenReturn(approveSuccessResponse);


		mockMvc.perform(get("/v1/api/payments/approve")
				.param("orderId", "order-1")
				.param("amount", "50000")
				.param("paymentKey", "payment-key-1")
				.param("memberId", "1"))
			.andDo(print())
			.andExpect(status().isOk())

			.andDo(document("payments/approve",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				responseFields(
					fieldWithPath("httpStatusCode").description("HTTP 상태 코드"),
					fieldWithPath("code").description("응답 코드"),
					fieldWithPath("message").description("응답 메시지"),
					fieldWithPath("description").description("응답 메시지 설명"),
					fieldWithPath("data.paymentKey").description("결제 키"),
					fieldWithPath("data.orderId").description("주문 ID"),
					fieldWithPath("data.method").description("결제 수단"),
					fieldWithPath("data.totalAmount").description("총 결제 금액"),
					fieldWithPath("data.approvedAt").description("승인 시각 (epoch timestamp)"),
					fieldWithPath("data.receipt.url").description("영수증 URL"),
					fieldWithPath("data.status").description("결제 상태")
				)
			));


		verify(paymentUseCase).approvePayment(request);
	}
}