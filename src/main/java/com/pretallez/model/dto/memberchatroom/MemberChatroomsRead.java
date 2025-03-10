package com.pretallez.model.dto.memberchatroom;

import com.pretallez.model.entity.Board;
import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.MemberChatroom;
import com.pretallez.model.entity.VotePost;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberChatroomsRead {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotNull(message = "memberId는 필수 값입니다.")
        private Long memberId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private Long votePostId;
        private String title;

        public static Response fromEntity(MemberChatroom memberChatroom) {
            Chatroom chatroom = memberChatroom.getChatroom();
            VotePost votePost = chatroom.getVotePost();
            Board board = votePost.getBoard();
            return new Response(
                    memberChatroom.getId(),
                    votePost.getId(),
                    board.getTitle()
            );
        }
    }
}
