package com.pretallez.controller.api;

import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.response.ResSuccessCode;
import com.pretallez.model.dto.board.BoardCreate;
import com.pretallez.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/boards")
    public CustomApiResponse<BoardCreate.Response> createBoard(@RequestBody BoardCreate.Request boardCreateRequest,
                                                               ) {
        Long memberId =1L;
        BoardCreate.Response response = boardService.addBoard(boardCreateRequest, memberId);
        return CustomApiResponse.OK(ResSuccessCode.CREATED,response);
    }

}
