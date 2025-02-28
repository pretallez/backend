package com.pretallez.service;

import com.pretallez.model.dto.board.BoardCreate;
import com.pretallez.model.entity.Board;

public interface BoardService {

    BoardCreate.Response addBoard(BoardCreate.Request boardCreateRequest,Long memberId);

}
