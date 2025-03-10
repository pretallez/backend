package com.pretallez.model.dto.memberchatroom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class ChatroomMembersRead {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long chatroomId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Response {
        private Long id;
        private String nickname;
    }
}
