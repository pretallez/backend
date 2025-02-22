package com.pretallez.common.fixture;

import com.pretallez.model.dto.example.ExampleCreate;
import com.pretallez.model.entity.*;
import com.pretallez.model.enums.BoardType;
import com.pretallez.model.enums.MannerLevel;
import com.pretallez.repository.ChatroomRepository;
import com.pretallez.repository.MemberRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

public class Fixture {

    public static Example example() {
        String fakeName = "김성호";
        String fakeNickName = "ksngh";
        ExampleCreate.Request request = ExampleCreate.Request.of(fakeName, fakeNickName);
        return Example.create(request);
    }

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
}
