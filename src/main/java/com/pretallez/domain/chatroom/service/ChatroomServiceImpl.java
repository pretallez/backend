package com.pretallez.domain.chatroom.service;

import com.pretallez.common.exception.EntityException;
import com.pretallez.common.response.error.EntityErrorCode;
import com.pretallez.domain.chatroom.dto.ChatroomCreate;
import com.pretallez.domain.chatroom.repository.ChatroomRepository;
import com.pretallez.common.entity.Chatroom;
import com.pretallez.common.entity.VotePost;
import com.pretallez.domain.votepost.service.VotePostService;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatroomServiceImpl implements ChatroomService {

    private final VotePostService votePostService;

    private final ChatroomRepository chatroomRepository;

    @Override
    public ChatroomCreate.Response addChatroom(ChatroomCreate.Request chatroomCreateRequest) {
        VotePost foundVotePost = votePostService.getVotePost(chatroomCreateRequest.getVotePostId());
        try {
            Chatroom savedChatroom = chatroomRepository.save(Chatroom.of(
                            foundVotePost,
                            chatroomCreateRequest.getNotice(),
                            chatroomCreateRequest.getBoardTitle()
                    ));
            return ChatroomCreate.Response.fromEntity(savedChatroom);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException (String.format("ID [%d]에 해당하는 채팅방이 이미 존재합니다.", foundVotePost.getId()), e);
        }
    }

    @Override
    public Chatroom getChatroom(Long chatroomId) {
        return chatroomRepository.findById(chatroomId)
                .orElseThrow(() -> new EntityException(EntityErrorCode.CHATROOM_NOT_FOUND, chatroomId));
    }

    @Override
    public void removeChatroom() {

    }
}
