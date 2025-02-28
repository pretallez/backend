package com.pretallez.service;

import com.pretallez.model.dto.board.BoardCreate;

public interface BoardService {

    BoardCreate.Response addBoard(BoardCreate.Request boardCreateRequest, Long memberId);

}
