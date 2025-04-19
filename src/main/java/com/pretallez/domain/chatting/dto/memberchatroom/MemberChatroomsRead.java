package com.pretallez.domain.chatting.dto.memberchatroom;

import com.pretallez.domain.posting.entity.Board;
import com.pretallez.domain.chatting.entity.Chatroom;
import com.pretallez.domain.chatting.entity.MemberChatroom;
import com.pretallez.domain.posting.entity.VotePost;
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
