package com.pretallez.domain.chatting.dto.chatroom;

import com.pretallez.domain.chatting.entity.Chatroom;
import com.pretallez.domain.posting.entity.VotePost;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatroomCreate {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotNull(message = "votePostId는 필수 값입니다.")
        private Long votePostId;

        @NotNull(message = "notice는 필수 값입니다.")
        private String notice;

        @NotNull(message = "boardTitle는 필수 값입니다.")
        private String boardTitle;

        public static Chatroom toEntity(VotePost votePost, Request request) {
            return Chatroom.of(votePost, request.getNotice(), request.getBoardTitle());
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private Long votePostId;

        public static Response fromEntity(Chatroom chatroom) {
            return new Response(chatroom.getId(), chatroom.getVotePost().getId());
        }
    }
}
