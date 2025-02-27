package com.pretallez.model.dto.memberchatroom;

import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.Member;
import com.pretallez.model.entity.MemberChatroom;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberChatroomCreate {

    @Getter
    @NoArgsConstructor
    public static class Request {

        @NotNull(message = "memberId는 필수 값입니다.")
        private Long memberId;

        @NotNull(message = "chatroomId는 필수 값입니다.")
        private Long chatroomId;

        private Request(Long memberId, Long chatroomId) {
            this.memberId = memberId;
            this.chatroomId = chatroomId;
        }

        public static MemberChatroomCreate.Request of(Long memberId, Long chatroomId) {
            return new MemberChatroomCreate.Request(memberId, chatroomId);
        }

        public static MemberChatroom toEntity(Member member, Chatroom chatroom) {
            return MemberChatroom.of(member, chatroom);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private Long memberId;
        private long chatroomId;

        private Response(Long id, Long memberId, Long chatroomId) {
            this.id = id;
            this.memberId = memberId;
            this.chatroomId = chatroomId;
        }

        public static MemberChatroomCreate.Response of(Long id, Long memberId, Long chatroomId) {
            return new MemberChatroomCreate.Response(id, memberId, chatroomId);
        }

        public static MemberChatroomCreate.Response fromEntity(MemberChatroom memberChatroom) {
            return MemberChatroomCreate.Response.of(memberChatroom.getId(), memberChatroom.getMember().getId(), memberChatroom.getChatroom().getId());
        }
    }
}
