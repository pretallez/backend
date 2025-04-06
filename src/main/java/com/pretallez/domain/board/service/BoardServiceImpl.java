package com.pretallez.domain.board.service;

import org.springframework.stereotype.Service;

import com.pretallez.common.entity.Board;
import com.pretallez.common.entity.Member;
import com.pretallez.common.exception.EntityException;
import com.pretallez.common.response.error.EntityErrorCode;
import com.pretallez.domain.board.dto.BoardCreate;
import com.pretallez.domain.board.repository.BoardRepository;
import com.pretallez.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

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
                .orElseThrow(() -> new EntityException(EntityErrorCode.VOTEPOST_NOT_FOUND, boardId));
    }

}
