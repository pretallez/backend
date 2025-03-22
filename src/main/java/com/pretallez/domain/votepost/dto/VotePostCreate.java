package com.pretallez.domain.votepost.dto;

import com.pretallez.domain.board.dto.BoardCreate;
import com.pretallez.common.entity.Board;
import com.pretallez.common.entity.FencingClub;
import com.pretallez.common.entity.VotePost;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class VotePostCreate {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Request {

        @NotNull
        private BoardCreate.Request boardCreateRequest;

        @NotNull
        private FencingClub fencingClub;

        @NotNull
        private Integer maxCapacity;

        @NotNull
        private Integer minCapacity;

        @NotNull
        private Integer totalAmount;

        @NotNull
        private LocalDateTime trainingDate;

        public VotePost toEntity(Board board, Request votePostCreateRequest) {
            return VotePost.of(
                    board,
                    votePostCreateRequest.getFencingClub(),
                    votePostCreateRequest.getMaxCapacity(),
                    votePostCreateRequest.getMinCapacity(),
                    votePostCreateRequest.getTotalAmount(),
                    votePostCreateRequest.getTrainingDate()
            );
        }

    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Response {

        private Long votePostId;

        private Response(VotePost votePost) {
            this.votePostId = votePost.getId();
        }

        public static Response fromEntity(VotePost votePost) {
            return new Response(votePost);
        }

    }

}
