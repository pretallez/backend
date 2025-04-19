package com.pretallez.domain.member.entity;

import com.pretallez.domain.member.enums.MannerLevel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "Member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Column(name = "name", nullable = false, length = 10)
    private String name;

    @Column(name = "manner_level", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private MannerLevel mannerLevel;

    @Column(name = "phone_num", nullable = false)
    private String phoneNum;

    @Column(name = "gender", nullable = false, length = 1)
    private String gender;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private Member(String email, String nickname, String name, MannerLevel mannerLevel, String phoneNum, String gender, Integer point) {
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.mannerLevel = mannerLevel;
        this.phoneNum = phoneNum;
        this.gender = gender;
        this.point = point;
        this.createdAt = LocalDateTime.now();
    }

    public static Member of(String email, String nickname, String name, MannerLevel mannerLevel, String phoneNum, String gender, Integer point) {
        return new Member(email, nickname, name, mannerLevel, phoneNum, gender, point);
    }
}
