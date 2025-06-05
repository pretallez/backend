package com.pretallez.domain.chat.entity;

import com.pretallez.domain.member.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "chat_room_id"}))
public class Participation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@JoinColumn
	@ManyToOne(fetch = FetchType.LAZY)
	private ChatRoom chatRoom;

	private Participation(Member member, ChatRoom chatroom) {
		this.member = member;
		this.chatRoom = chatroom;
	}

	public static Participation join(Member member, ChatRoom chatRoom) {
		return new Participation(member, chatRoom);
	}
}
