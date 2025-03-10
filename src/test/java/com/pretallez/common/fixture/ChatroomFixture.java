package com.pretallez.common.fixture;

import com.pretallez.model.dto.chatroom.ChatroomCreate;
import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.VotePost;
import org.springframework.test.util.ReflectionTestUtils;

public class ChatroomFixture {

    public static Chatroom chatroom(VotePost votePost) {
        return Chatroom.of(votePost);
    }

    public static Chatroom fakeChatroom(Long id, VotePost votePost) {
        Chatroom chatroom = Chatroom.of(votePost);
        ReflectionTestUtils.setField(chatroom, "id", id);
        return chatroom;
    }

    public static ChatroomCreate.Request chatroomCreateRequest(Long votePostId) {
        return new ChatroomCreate.Request(votePostId);
    }

    public static ChatroomCreate.Response chatroomCreateResponse(Long id, Long votePostId) {
        return new ChatroomCreate.Response(id, votePostId);
    }
}
