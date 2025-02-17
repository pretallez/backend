package com.pretallez.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Chatroom")
public class Chatroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "votepost_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private VotePost votePost;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

}
