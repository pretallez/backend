package com.pretallez.service;

import com.pretallez.model.dto.board.BoardCreate;
import com.pretallez.model.entity.Board;

public interface BoardService {

    /** 게시글을 생성합니다. */
    Board addBoard(BoardCreate.Request boardCreateRequest, Long writerId);

    /** Board 엔티티가 존재하면 반환, 존재하지 않으면 예외를 던집니다. */
    Board getBoard(Long boardId);

}
