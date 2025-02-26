package com.pretallez.model.entity;

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
    @ManyToOne(fetch = FetchType.LAZY)
    private VotePost votePost;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private Chatroom(VotePost votePost) {
        this.votePost = votePost;
        this.createdAt = LocalDateTime.now();
    }

    public static Chatroom of(VotePost votePost) {
        return new Chatroom(votePost);
    }
}
