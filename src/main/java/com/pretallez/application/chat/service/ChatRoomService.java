package com.pretallez.application.chat.service;

import org.springframework.stereotype.Service;

import com.pretallez.application.chat.port.input.ChatRoomUseCase;
import com.pretallez.domain.chat.entity.ChatRoom;
import com.pretallez.domain.chat.exception.ChatErrorCode;
import com.pretallez.domain.chat.exception.ChatException;
import com.pretallez.domain.chat.repository.ChatRoomRepository;
import com.pretallez.domain.posting.entity.VotePost;
import com.pretallez.domain.votepost.repository.VotePostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService implements ChatRoomUseCase {

	private final ChatRoomRepository chatRoomRepository;
	private final VotePostRepository votePostRepository;

	@Override
	public void openChatRoomForVotePost(Long votePostId) {
		VotePost foundVotePost = getVotePost(votePostId);
		ChatRoom chatRoom = ChatRoom.createFromVotePost(foundVotePost);

		chatRoomRepository.save(chatRoom);
	}

	@Override
	public void closeChatRoom(Long chatRoomId) {
		ChatRoom foundChatRoom = getChatRoom(chatRoomId);

		chatRoomRepository.delete(foundChatRoom);
	}

	private ChatRoom getChatRoom(Long chatRoomId) {
		return chatRoomRepository.findById(chatRoomId)
			.orElseThrow(() -> new ChatException(ChatErrorCode.CHATROOM_NOT_FOUND, chatRoomId));
	}

	private VotePost getVotePost(Long votePostId) {
		return votePostRepository.findById(votePostId)
			.orElseThrow(() -> new ChatException(ChatErrorCode.VOTEPOST_NOT_FOUND, votePostId));
	}
}
