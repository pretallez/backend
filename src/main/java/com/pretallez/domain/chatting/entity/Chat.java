package com.pretallez.domain.chatting.entity;

import java.time.LocalDateTime;

import com.pretallez.domain.chatting.enums.MessageType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "chatroom_id", nullable = false)
    private Long chatroomId;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @Column(name = "message_type", nullable = false, length = 30)
    private MessageType messageType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private Chat(Long memberId, Long chatroomId, String content, MessageType messageType, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.chatroomId = chatroomId;
        this.content = content;
        this.messageType = messageType;
        this.createdAt = createdAt;
    }

    public static Chat of(Long memberId, Long chatroomId, String content, MessageType messageType, LocalDateTime createdAt) {
        return new Chat(memberId, chatroomId, content, messageType, createdAt);
    }
}
