package com.pretallez.domain.chat.entity;

import java.time.LocalDateTime;

import com.pretallez.domain.chat.exception.ChatErrorCode;
import com.pretallez.domain.chat.exception.ChatException;
import com.pretallez.domain.posting.entity.VotePost;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "votepost_id", unique = true)
	@OneToOne(fetch = FetchType.LAZY)
	private VotePost votePost;

	private String notice;

	private String boardTitle;

	private LocalDateTime createdAt;

	private ChatRoom(VotePost votePost, String boardTitle) {
		this.votePost = votePost;
		this.notice = "";
		this.boardTitle = boardTitle;
		this.createdAt = LocalDateTime.now();
	}

	public static ChatRoom createFromVotePost(VotePost votePost) {
		if (votePost == null) {
			throw new ChatException(ChatErrorCode.VOTE_POST_REQUIRED);
		}

		return new ChatRoom(
			votePost,
			votePost.getBoard().getTitle()
		);
	}
}
