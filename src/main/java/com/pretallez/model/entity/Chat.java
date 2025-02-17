package com.pretallez.model.entity;

import com.pretallez.model.enums.MessageType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "member_chatroom_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private MemberChatroom memberChatroom;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @Column(name = "message_type", nullable = false, length = 30)
    private MessageType messageType;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

}
