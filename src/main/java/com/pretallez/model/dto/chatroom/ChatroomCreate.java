package com.pretallez.model.dto.chatroom;

import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.VotePost;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatroomCreate {

    @Getter
    @NoArgsConstructor
    public static class Request {
        private Long votePostId;

        private Request(Long votePostId) {
            this.votePostId = votePostId;
        }

        public static Request of(Long votePostId) {
            return new Request(votePostId);
        }

        public static Chatroom toEntity(VotePost votePost) {
            return Chatroom.of(votePost);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private Long votePostId;

        private Response(Long id, Long votePostId) {
            this.id = id;
            this.votePostId = votePostId;
        }

        public static Response of(Long id, Long votePostId) {
            return new Response(id, votePostId);
        }

        public static Response fromEntity(Chatroom chatroom) {
            return Response.of(chatroom.getId(), chatroom.getVotePost().getId());
        }
    }
}
