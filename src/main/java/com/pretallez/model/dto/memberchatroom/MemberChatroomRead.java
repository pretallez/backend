package com.pretallez.model.dto.memberchatroom;

import com.pretallez.model.entity.Board;
import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.MemberChatroom;
import com.pretallez.model.entity.VotePost;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

public class MemberChatroomRead {

    @Getter
    @NoArgsConstructor
    public static class Request {

        @NotNull(message = "memberId는 필수 값입니다.")
        private Long memberId;

        private Request(Long memberId) {
            this.memberId = memberId;
        }

        public static Request of(Long memberId) {
            return new Request(memberId);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private Long votePostId;
        private String title;

        public static Response of(Long id, Long votePostId, String title) {
            return new Response(id, votePostId, title);
        }

        public static Response fromEntity(MemberChatroom memberChatroom) {
            Chatroom chatroom = memberChatroom.getChatroom();
            VotePost votePost = chatroom.getVotePost();
            Board board = votePost.getBoard();
            return Response.of(
                    memberChatroom.getId(),
                    votePost.getId(),
                    board.getTitle()
            );
        }
    }
}
