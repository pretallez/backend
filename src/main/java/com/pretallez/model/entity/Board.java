package com.pretallez.model.entity;

import com.pretallez.model.enums.BoardType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "writer_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "board_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    private Board(Member member, String title, String content, BoardType boardType) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.boardType = boardType;
        this.createdAt = LocalDateTime.now();
    }

    public static Board of(Member member, String title, String content, BoardType boardType) {
        return new Board(member, title, content, boardType);
    }

}
