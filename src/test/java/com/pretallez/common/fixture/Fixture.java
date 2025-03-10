package com.pretallez.common.fixture;

import com.pretallez.model.dto.chatroom.ChatroomCreate;
import com.pretallez.model.dto.memberchatroom.MemberChatroomCreate;
import com.pretallez.model.dto.memberchatroom.MemberChatroomDelete;
import com.pretallez.model.dto.memberchatroom.MemberChatroomRead;
import com.pretallez.model.entity.*;
import com.pretallez.model.enums.BoardType;
import com.pretallez.model.enums.MannerLevel;

import java.time.LocalDateTime;
import java.util.List;

public class Fixture {

    public static Chatroom chatroom(VotePost votePost) {
        return Chatroom.of(votePost);
    }

    public static VotePost votePost(Board board, FencingClub fencingClub) {
        Integer maxCapacity = 10;
        Integer minCapacity = 50;
        Integer totalAmount = 10000;
        LocalDateTime trainingDate = LocalDateTime.now().plusDays(10L);
        return VotePost.of(board, fencingClub, maxCapacity, minCapacity, totalAmount, trainingDate);
    }

    public static Board board(Member member) {
        String title = "testTitle";
        String content = "testContent";
        BoardType boardType = BoardType.OPEN_PISTE;
        return Board.of(member, title, content, boardType);
    }

    public static FencingClub fencingClub() {
        String type = "SABRE";
        String contact = "0212345678";
        String address = "seoul";
        String description = "fencing club";
        String gearExist = "Y";
        return FencingClub.of(type, contact, address, description, gearExist);
    }

    public static Member member() {
        String email = "test@test.com";
        String nickname = "testNickname";
        String name = "testName";
        MannerLevel mannerLevel = MannerLevel.BEGINNER;
        String phone = "01012345678";
        String gender = "M";
        Integer point = 0;
        return Member.of(email, nickname, name, mannerLevel, phone, gender, point);
    }

    public static MemberChatroom memberChatroom(Member member, Chatroom chatroom) {
        return MemberChatroom.of(member, chatroom);
    }

    public static ChatroomCreate.Request chatroomCreateRequest(Long votePostId) {
        return new ChatroomCreate.Request(votePostId);
    }

    public static ChatroomCreate.Response chatroomCreateResponse(Long id, Long votePostId) {
        return new ChatroomCreate.Response(id, votePostId);
    }

    public static MemberChatroomCreate.Request memberChatroomCreateRequest(Long memberId, Long chatroomId) {
        return new MemberChatroomCreate.Request(memberId, chatroomId);
    }

    public static MemberChatroomCreate.Response memberChatroomCreateResponse(Long id, Long memberId, Long chatroomId) {
        return new MemberChatroomCreate.Response(id, memberId, chatroomId);
    }

    public static MemberChatroomDelete.Request memberChatroomDeleteRequest(Long memberId, Long chatroomId) {
        return new MemberChatroomDelete.Request(memberId, chatroomId);
    }

    public static MemberChatroomRead.Request memberChatroomReadRequest(Long memberId) {
        return MemberChatroomRead.Request.of(memberId);
    }

    public static List<MemberChatroomRead.Response> memberChatroomReadResponses() {
        return List.of(
                new MemberChatroomRead.Response(1L, 1L, "첫 번째 채팅방의 투표글 제목"),
                new MemberChatroomRead.Response(2L, 2L, "두 번째 채팅방의 투표글 제목")
        );
    }
}
