package com.pretallez.common.fixture;

import com.pretallez.model.dto.VotePostCreate;
import com.pretallez.model.dto.board.BoardCreate;
import com.pretallez.model.dto.chatroom.ChatroomCreate;
import com.pretallez.model.dto.memberchatroom.MemberChatroomCreate;
import com.pretallez.model.dto.memberchatroom.MemberChatroomDelete;
import com.pretallez.model.entity.*;
import com.pretallez.model.enums.BoardType;
import com.pretallez.model.enums.MannerLevel;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

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

    public static Board board(Member member) throws IllegalAccessException, NoSuchFieldException {
        String title = "[펜싱그라운드] 재밌게 펜싱하실 분 구해요";
        String content = "안녕하세요! 펜싱을 좋아하시는 분들, 혹은 펜싱에 관심 있으신 분들 모두 환영합니다! \uD83C\uDFAF\n" +
                "저희는 펜싱을 즐기며 함께 운동하고 싶은 분들을 모집하고 있습니다.\n" +
                "펜싱은 단순한 스포츠를 넘어 전략과 순발력, 그리고 집중력을 키울 수 있는 멋진 운동이에요.\n" +
                "초보자부터 고수까지 모두 함께 즐길 수 있도록 기본적인 지도도 제공하고 있으니 걱정하지 마세요!\n" +
                "\n" +
                "\uD83D\uDCC5 모임 일정\n" +
                "일시: 매주 토요일 오후 2시\n" +
                "\n" +
                "장소: 서울시 강남구 펜싱그라운드\n" +
                "\n" +
                "참가비: 1회 10,000원 (장비 대여 포함)\n" +
                "\n" +
                "\uD83D\uDCA1 이런 분들 오세요!\n" +
                "펜싱을 처음 해보시는 분\n" +
                "\n" +
                "운동 친구를 찾고 계신 분\n" +
                "\n" +
                "새로운 취미를 시작하고 싶으신 분\n" +
                "\n" +
                "스트레스 풀고 싶으신 분\n" +
                "\n" +
                "✨ 특전\n" +
                "첫 참가자에게는 무료 음료 제공!\n" +
                "\n" +
                "정기적으로 참여하시는 분들께는 특별 혜택도 준비되어 있습니다.\n" +
                "\n" +
                "함께 즐겁게 땀 흘리며 펜싱 실력을 키워보아요!\n" +
                "관심 있으신 분들은 아래로 연락주세요. \uD83D\uDE0A\n" +
                "\n" +
                "\uD83D\uDCDE 문의: 010-1234-5678\n" +
                "\uD83D\uDCE7 이메일: fencingground@example.com\n" +
                "\n" +
                "많은 참여 부탁드립니다!\n" +
                "펜싱그라운드에서 뵐게요~ \uD83E\uDD3A";
        BoardType boardType = BoardType.RENTAL_SERVICE;
        Board board = Board.of(member, title, content, boardType);
        Field idField = Board.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(board, 1L);
        return board;
    }

    public static FencingClub fencingClub() {
        String type = "SABRE";
        String contact = "0212345678";
        String address = "seoul";
        String description = "fencing club";
        String gearExist = "Y";
        return FencingClub.of(type, contact, address, description, gearExist);
    }

    public static Member member() throws NoSuchFieldException, IllegalAccessException {
        String email = "test@test.com";
        String nickname = "testNickname";
        String name = "testName";
        MannerLevel mannerLevel = MannerLevel.BEGINNER;
        String phone = "01012345678";
        String gender = "M";
        Integer point = 0;
        Member savedMember = Member.of(email, nickname, name, mannerLevel, phone, gender, point);
        Field idField = Member.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(savedMember, 1L);
        return savedMember;
    }

    public static MemberChatroom memberChatroom(Member member, Chatroom chatroom) {
        return MemberChatroom.of(member, chatroom);
    }

    public static ChatroomCreate.Request chatroomCreateRequest(Long votePostId) {
        return ChatroomCreate.Request.of(votePostId);
    }

    public static ChatroomCreate.Response chatroomCreateResponse(Long id, Long votePostId) {
        return ChatroomCreate.Response.of(id, votePostId);
    }

    public static MemberChatroomCreate.Request memberChatroomCreateRequest(Long memberId, Long chatroomId) {
        return MemberChatroomCreate.Request.of(memberId, chatroomId);
    }

    public static MemberChatroomCreate.Response memberChatroomCreateResponse(Long id, Long memberId, Long chatroomId) {
        return MemberChatroomCreate.Response.of(id, memberId, chatroomId);
    }

    public static MemberChatroomDelete.Request memberChatroomDeleteRequest(Long memberId, Long chatroomId) {
        return MemberChatroomDelete.Request.of(memberId, chatroomId);
    }

    public static BoardCreate.Request boardCreateRequest() {
        String title = "[펜싱그라운드] 재밌게 펜싱하실 분 구해요";
        String content = "안녕하세요! 펜싱을 좋아하시는 분들, 혹은 펜싱에 관심 있으신 분들 모두 환영합니다! \uD83C\uDFAF\n" +
                "저희는 펜싱을 즐기며 함께 운동하고 싶은 분들을 모집하고 있습니다.\n" +
                "펜싱은 단순한 스포츠를 넘어 전략과 순발력, 그리고 집중력을 키울 수 있는 멋진 운동이에요.\n" +
                "초보자부터 고수까지 모두 함께 즐길 수 있도록 기본적인 지도도 제공하고 있으니 걱정하지 마세요!\n" +
                "\n" +
                "\uD83D\uDCC5 모임 일정\n" +
                "일시: 매주 토요일 오후 2시\n" +
                "\n" +
                "장소: 서울시 강남구 펜싱그라운드\n" +
                "\n" +
                "참가비: 1회 10,000원 (장비 대여 포함)\n" +
                "\n" +
                "\uD83D\uDCA1 이런 분들 오세요!\n" +
                "펜싱을 처음 해보시는 분\n" +
                "\n" +
                "운동 친구를 찾고 계신 분\n" +
                "\n" +
                "새로운 취미를 시작하고 싶으신 분\n" +
                "\n" +
                "스트레스 풀고 싶으신 분\n" +
                "\n" +
                "✨ 특전\n" +
                "첫 참가자에게는 무료 음료 제공!\n" +
                "\n" +
                "정기적으로 참여하시는 분들께는 특별 혜택도 준비되어 있습니다.\n" +
                "\n" +
                "함께 즐겁게 땀 흘리며 펜싱 실력을 키워보아요!\n" +
                "관심 있으신 분들은 아래로 연락주세요. \uD83D\uDE0A\n" +
                "\n" +
                "\uD83D\uDCDE 문의: 010-1234-5678\n" +
                "\uD83D\uDCE7 이메일: fencingground@example.com\n" +
                "\n" +
                "많은 참여 부탁드립니다!\n" +
                "펜싱그라운드에서 뵐게요~ \uD83E\uDD3A";
        BoardType boardType = BoardType.RENTAL_SERVICE;
        return new BoardCreate.Request(title, content, boardType);
    }

    public static VotePostCreate.Request votePostCreateRequest(FencingClub fencingClub) {
        BoardCreate.Request boardCreateRequest = boardCreateRequest();
        Integer maxCapacity = 20;
        Integer minCapacity = 5;
        Integer totalAmount = 70000;
        LocalDateTime trainingDate = LocalDateTime.now().plusMonths(1);
        return new VotePostCreate.Request(boardCreateRequest, fencingClub, maxCapacity, minCapacity, totalAmount, trainingDate);
    }

    public static VotePostCreate.Response votePostCreateResponse() {
        Long votePostId = 1L;
        return new VotePostCreate.Response(votePostId);
    }

}
