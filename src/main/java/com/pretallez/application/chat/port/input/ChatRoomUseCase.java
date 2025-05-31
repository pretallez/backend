package com.pretallez.application.chat.port.input;

public interface ChatRoomUseCase {
	// Command
	void openChatRoomForVotePost(Long votePostId);
	void closeChatRoom(Long chatRoomId);
}
