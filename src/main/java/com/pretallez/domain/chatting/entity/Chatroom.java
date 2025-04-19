package com.pretallez.domain.chatting.entity;

import com.pretallez.domain.posting.entity.VotePost;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Chatroom")
public class Chatroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "votepost_id", unique = true, nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private VotePost votePost;

    @Column(name = "notice")
    private String notice;

    @Column(name = "board_title")
    private String boardTitle;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private Chatroom(VotePost votePost, String notice, String boardTitle) {
        this.votePost = votePost;
        this.notice = notice;
        this.boardTitle = boardTitle;
        this.createdAt = LocalDateTime.now();
    }

    public static Chatroom of(VotePost votePost, String notice, String boardTitle) {
        return new Chatroom(votePost, notice, boardTitle);
    }
}
