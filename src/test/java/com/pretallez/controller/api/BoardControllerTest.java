package com.pretallez.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.response.ResSuccessCode;
import com.pretallez.model.dto.board.BoardCreate;
import com.pretallez.model.enums.BoardType;
import com.pretallez.service.BoardService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
class BoardControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private BoardService boardService;

    @Test
    void createBoard_Return200() throws Exception {
        // Given: 요청 객체 생성
        BoardCreate.Request request = new BoardCreate.Request("게시글 제목", "게시글 내용", BoardType.OPEN_PISTE);

        BoardCreate.Response response = new BoardCreate.Response(1L);
        CustomApiResponse<BoardCreate.Response> responseCustomApiResponse = CustomApiResponse.OK(ResSuccessCode.CREATED, response);

        given(boardService.addBoard(any(BoardCreate.Request.class), anyLong()))
                .willReturn(responseCustomApiResponse.getData());

        // When: 요청 실행
        mockMvc.perform(post("/v1/api/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("boards/create-board",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용"),
                                fieldWithPath("boardType").description("게시글 타입")
                        ),
                        responseFields(
                                fieldWithPath("httpStatusCode").description("HTTP 상태 코드"),
                                fieldWithPath("code").description("응답 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터 객체"),
                                fieldWithPath("data.id").description("게시글 ID")
                        )));
    }
}