package com.pretallez.common.fixture;

import com.pretallez.domain.member.entity.Member;
import com.pretallez.domain.member.enums.MannerLevel;
import org.springframework.test.util.ReflectionTestUtils;

public class MemberFixture {

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

    public static Member fakeMember(Long id) {
        String email = "test@test.com";
        String nickname = "testNickname";
        String name = "testName";
        MannerLevel mannerLevel = MannerLevel.BEGINNER;
        String phone = "01012345678";
        String gender = "M";
        Integer point = 0;
        Member savedMember = Member.of(email, nickname, name, mannerLevel, phone, gender, point);
        ReflectionTestUtils.setField(savedMember, "id", id);

        return savedMember;
    }
}
