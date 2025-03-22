package com.pretallez.domain.board.service;

import com.pretallez.common.exception.EntityNotFoundException;
import com.pretallez.domain.board.dto.BoardCreate;
import com.pretallez.domain.board.repository.BoardRepository;
import com.pretallez.common.entity.Board;
import com.pretallez.common.entity.Member;
import com.pretallez.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;

    @Override
    public Board addBoard(BoardCreate.Request boardCreateRequest, Long writerId) {
        Member member = memberService.getMember(writerId);
        Board board = BoardCreate.Request.toEntity(boardCreateRequest, member);
        return boardRepository.save(board);
    }

    @Override
    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("ID [%d]에 해당하는 게시글을 찾을 수 없습니다.", boardId)));
    }

}
