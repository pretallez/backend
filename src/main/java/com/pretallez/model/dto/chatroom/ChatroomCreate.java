package com.pretallez.model.dto.chatroom;

import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.VotePost;
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

        public static Chatroom toEntity(VotePost votePost) {
            return Chatroom.of(votePost);
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
