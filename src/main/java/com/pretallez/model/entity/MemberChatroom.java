package com.pretallez.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "MemberChatroom", uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "chatroom_id"}))
public class MemberChatroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "chatroom_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Chatroom chatroom;

    private MemberChatroom(Member member, Chatroom chatroom) {
        this.member = member;
        this.chatroom = chatroom;
    }

    public static MemberChatroom of(Member member, Chatroom chatroom) {
        return new MemberChatroom(member, chatroom);
    }

}
