package com.pretallez.model.entity;

import com.pretallez.model.enums.MannerLevel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
    private MannerLevel mannerLevel;

    @Column(name = "phone_num", nullable = false)
    private String phoneNum;

    @Column(name = "gender", nullable = false, length = 1)
    private String gender;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

}
