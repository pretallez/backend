package com.pretallez.model.dto.board;

import com.pretallez.model.entity.Board;
import com.pretallez.model.entity.Member;
import com.pretallez.model.enums.BoardType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class BoardCreate {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private String title;
        private String content;
        private BoardType boardType;

        public static Board toEntity(Request request, Member member) {
            return Board.of(member, request.getTitle(), request.getTitle(), request.getBoardType());
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
