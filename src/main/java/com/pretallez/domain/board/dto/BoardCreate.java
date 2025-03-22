package com.pretallez.domain.board.dto;

import com.pretallez.common.entity.Board;
import com.pretallez.common.entity.Member;
import com.pretallez.common.enums.BoardType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BoardCreate {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotNull(message = "title은 필수 입력 값입니다.")
        private String title;

        @NotNull(message = "content는 필수 입력 값입니다.")
        private String content;

        @NotNull(message = "boardType은 필수 입력 값입니다.")
        private BoardType boardType;

        public static Board toEntity(Request request, Member member) {
            return Board.of(member, request.getTitle(), request.getContent(), request.getBoardType());
        }

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long id;

        public static Response fromEntity(Board board) {
            return new Response(board.getId());
        }
    }


}
