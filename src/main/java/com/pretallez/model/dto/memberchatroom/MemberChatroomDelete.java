package com.pretallez.model.dto.memberchatroom;

import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.Member;
import com.pretallez.model.entity.MemberChatroom;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberChatroomDelete {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotNull(message = "memberId는 필수 값입니다.")
        private Long memberId;

        @NotNull(message = "chatroomId는 필수 값입니다.")
        private Long chatroomId;

        public static MemberChatroom toEntity(Member member, Chatroom chatroom) {
            return MemberChatroom.of(member, chatroom);
        }
    }
}
