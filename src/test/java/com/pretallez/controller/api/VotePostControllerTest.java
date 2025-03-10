package com.pretallez.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pretallez.common.fixture.Fixture;
import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.response.ResSuccessCode;
import com.pretallez.model.dto.VotePostCreate;
import com.pretallez.service.VotePostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VotePostController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
class VotePostControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private VotePostService votePostService;

    @Test
    void createBoard_Return200() throws Exception {
        // Given: 요청 객체 생성
        VotePostCreate.Request votePostCreateRequest = Fixture.votePostCreateRequest(Fixture.fencingClub());
        Long fakeMemberId = 1L;
        VotePostCreate.Response votePostCreateResponse = Fixture.votePostCreateResponse();
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
                                fieldWithPath("data").description("응답 데이터 객체"),
                                fieldWithPath("data.votePostId").description("게시글 ID")
                        )));
    }
}