package com.pretallez.domain.votepost.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pretallez.common.entity.Board;
import com.pretallez.common.entity.VotePost;
import com.pretallez.common.exception.EntityNotFoundException;
import com.pretallez.domain.board.service.BoardService;
import com.pretallez.domain.votepost.dto.VotePostCreate;
import com.pretallez.domain.votepost.repository.VotePostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VotePostServiceImpl implements VotePostService {

    private final VotePostRepository votePostRepository;
    private final BoardService boardService;

    @Override
    public VotePost getVotePost(Long votePostId) {
        return votePostRepository.findById(votePostId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("ID [%d]에 해당하는 투표 게시글을 찾을 수 없습니다.", votePostId)));
    }

    @Override
    @Transactional
    public VotePostCreate.Response addVotePost(Long writerId, VotePostCreate.Request votePostCreateRequest) {
        Board board = boardService.addBoard(votePostCreateRequest.getBoardCreateRequest(), writerId);
        return VotePostCreate.Response.fromEntity(votePostRepository.save(votePostCreateRequest.toEntity(board,votePostCreateRequest)));
    }

    @Override
    public void modifyVotePost() {

    }

    @Override
    public void removeVotePost() {

    }

    @Override
    public void getVotePostsWithPaging() {

    }

    @Override
    public void getVotePostDetails() {

    }
}
