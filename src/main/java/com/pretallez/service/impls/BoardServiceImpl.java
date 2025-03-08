package com.pretallez.service.impls;

import com.pretallez.common.exception.EntityNotFoundException;
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
    public Board addBoard(BoardCreate.Request boardCreateRequest, Long writerId) {
        Member member = new Member();
        Board board = BoardCreate.Request.toEntity(boardCreateRequest, member);
        return boardRepository.save(board);
    }

    @Override
    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("ID [%d]에 해당하는 게시글을 찾을 수 없습니다.", boardId)));
    }

}
