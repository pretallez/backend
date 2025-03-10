package com.pretallez.common.fixture;

import com.pretallez.model.dto.VotePostCreate;
import com.pretallez.model.dto.board.BoardCreate;
import com.pretallez.model.entity.Board;
import com.pretallez.model.entity.FencingClub;
import com.pretallez.model.entity.VotePost;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

public class VotePostFixture {

    public static VotePost votePost(Board board, FencingClub fencingClub) {
        Integer maxCapacity = 10;
        Integer minCapacity = 50;
        Integer totalAmount = 10000;
        LocalDateTime trainingDate = LocalDateTime.now().plusDays(10L);

        return VotePost.of(board, fencingClub, maxCapacity, minCapacity, totalAmount, trainingDate);
    }

    public static VotePost fakeVotePost(Long id, Board board, FencingClub fencingClub) {
        Integer maxCapacity = 10;
        Integer minCapacity = 50;
        Integer totalAmount = 10000;
        LocalDateTime trainingDate = LocalDateTime.now().plusDays(10L);
        VotePost votePost = VotePost.of(board, fencingClub, maxCapacity, minCapacity, totalAmount, trainingDate);
        ReflectionTestUtils.setField(votePost, "id", id);

        return votePost;
    }

    public static VotePostCreate.Request votePostCreateRequest(FencingClub fencingClub) {
        BoardCreate.Request boardCreateRequest = BoardFixture.boardCreateRequest();
        Integer maxCapacity = 20;
        Integer minCapacity = 5;
        Integer totalAmount = 70000;
        LocalDateTime trainingDate = LocalDateTime.now().plusMonths(1);

        return new VotePostCreate.Request(boardCreateRequest, fencingClub, maxCapacity, minCapacity, totalAmount, trainingDate);
    }

    public static VotePostCreate.Response votePostCreateResponse(Long id) {
        return new VotePostCreate.Response(id);
    }

}
