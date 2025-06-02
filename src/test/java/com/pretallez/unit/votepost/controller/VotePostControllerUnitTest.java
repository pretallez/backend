package com.pretallez.unit.votepost.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pretallez.common.enums.success.ResSuccessCode;
import com.pretallez.common.fixture.FencingClubFixture;
import com.pretallez.common.fixture.VotePostFixture;
import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.controller.VotePostController;
import com.pretallez.domain.auth.filter.JwtAuthenticationFilter;
import com.pretallez.domain.fencingclub.entity.FencingClub;
import com.pretallez.domain.payment.config.PaymentInterceptorConfig;
import com.pretallez.domain.payment.interceptor.IdempotencyInterceptor;
import com.pretallez.domain.posting.dto.votepost.VotePostCreate;
import com.pretallez.domain.votepost.service.VotePostService;

@WebMvcTest(
    controllers = VotePostController.class,
    excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
    },
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
            JwtAuthenticationFilter.class, IdempotencyInterceptor.class, PaymentInterceptorConfig.class
        })
    }
)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@DisplayName("대관 게시글 컨트롤러 단위 테스트")
class VotePostControllerUnitTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private VotePostService votePostService;

    @Test
    @DisplayName("대관 신청 게시글 생성 요청 시, 성공 및 200 응답")
    void createBoard_ThenReturnSuccess_200() throws Exception {
        // Given: 요청 객체 생성
        FencingClub fencingClub = FencingClubFixture.fakeFencingClub(1L);
        VotePostCreate.Request votePostCreateRequest = VotePostFixture.votePostCreateRequest(fencingClub);
        Long fakeMemberId = 1L;
        VotePostCreate.Response votePostCreateResponse = VotePostFixture.votePostCreateResponse(1L);
        CustomApiResponse<VotePostCreate.Response> responseCustomApiResponse = CustomApiResponse.OK(ResSuccessCode.CREATED, votePostCreateResponse);
        objectMapper.registerModule(new JavaTimeModule());
        given(votePostService.addVotePost(eq(fakeMemberId), any(VotePostCreate.Request.class)))
                .willReturn(responseCustomApiResponse.getData());

        // When,Then: 요청 실행
        mockMvc.perform(post("/v1/api/vote-posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(votePostCreateRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("vote-post/create-vote-post",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(
                                fieldWithPath("boardCreateRequest").description("게시판 생성 요청 객체"),
                                fieldWithPath("boardCreateRequest.title").description("게시물 제목"),
                                fieldWithPath("boardCreateRequest.content").description("게시물 내용"),
                                fieldWithPath("boardCreateRequest.boardType").description("게시판 유형 (예: RENTAL_SERVICE)"),

                                fieldWithPath("fencingClub").description("펜싱 클럽 정보"),
                                fieldWithPath("fencingClub.id").optional().description("클럽 ID"),
                                fieldWithPath("fencingClub.type").description("펜싱 유형 (예: SABRE)"),
                                fieldWithPath("fencingClub.contact").description("연락처"),
                                fieldWithPath("fencingClub.address").description("주소"),
                                fieldWithPath("fencingClub.description").description("클럽 설명"),
                                fieldWithPath("fencingClub.gearExist").description("장비 존재 여부 (Y/N)"),
                                fieldWithPath("maxCapacity").description("최대 인원"),
                                fieldWithPath("minCapacity").description("최소 인원"),
                                fieldWithPath("totalAmount").description("총 금액"),
                                fieldWithPath("trainingDate").description("훈련 날짜")
                        ),
                        responseFields(
                                fieldWithPath("httpStatusCode").description("HTTP 상태 코드"),
                                fieldWithPath("code").description("응답 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("description").description("응답 메시지 설명"),
                                fieldWithPath("data").description("응답 데이터 객체"),
                                fieldWithPath("data.votePostId").description("게시글 ID")
                        )));
    }
}