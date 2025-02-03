package com.pretallez.model.entity;

import com.pretallez.model.dto.example.ExampleCreate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 생성은 정적 팩토리 메서드
// column name, 길이 명시
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA hibernate 구현체 사용시 필요
public class Example {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "nickname", length = 20, nullable = false)
    private String nickname;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    private Example(ExampleCreate.Request exampleCreateRequest) {
        this.name = exampleCreateRequest.getName();
        this.nickname = exampleCreateRequest.getNickname();
        this.createdAt = LocalDateTime.now();
    }

    public static Example create(ExampleCreate.Request exampleCreateRequest) {
        return new Example(exampleCreateRequest);
    }

}
