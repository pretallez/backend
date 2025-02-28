package com.pretallez.service.impls;

import com.pretallez.model.dto.board.BoardCreate;
import com.pretallez.model.entity.Board;
import com.pretallez.model.entity.Member;
import com.pretallez.repository.BoardRepository;
import com.pretallez.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public BoardCreate.Response addBoard(BoardCreate.Request boardCreateRequest, Long memberId) {
        Member member = new Member();
        Board board = BoardCreate.Request.toEntity(boardCreateRequest, member);
        return BoardCreate.Response.fromEntity(boardRepository.save(board));
    }
}
