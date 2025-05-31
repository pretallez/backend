package com.pretallez.unit.chat.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.pretallez.domain.chat.entity.ChatRoom;
import com.pretallez.domain.chat.exception.ChatErrorCode;
import com.pretallez.domain.chat.exception.ChatException;
import com.pretallez.domain.posting.entity.Board;
import com.pretallez.domain.posting.entity.VotePost;

class ChatRoomTest {

	@Test
	void 정상적인_투표글로_채팅방을_생성한다() {
		// given
		Board mockBoard = mock(Board.class);
		when(mockBoard.getTitle()).thenReturn("투표글 제목");

		VotePost mockVotePost = mock(VotePost.class);
		when(mockVotePost.getBoard()).thenReturn(mockBoard);

		// when
		ChatRoom sut = ChatRoom.createFromVotePost(mockVotePost);

		// then
		assertThat(sut.getVotePost()).isEqualTo(mockVotePost);
		assertThat(sut.getBoardTitle()).isEqualTo("투표글 제목");
		assertThat(sut.getNotice()).isEqualTo("");
		assertThat(sut.getCreatedAt()).isNotNull();
	}

	@Test
	void 투표글이_존재하지_않으면_예외를_던진다() {
		// when
		ChatException ex = assertThrows(ChatException.class, () ->
			ChatRoom.createFromVotePost(null)
		);

		// then
		assertThat(ex.getResCode()).isEqualTo(ChatErrorCode.VOTE_POST_REQUIRED);
	}
}