package com.pretallez.domain.posting.service;

import org.springframework.stereotype.Service;

import com.pretallez.domain.posting.entity.Board;
import com.pretallez.domain.member.entity.Member;
import com.pretallez.common.exception.EntityException;
import com.pretallez.common.enums.error.EntityErrorCode;
import com.pretallez.domain.posting.dto.board.BoardCreate;
import com.pretallez.domain.posting.repository.BoardRepository;
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
