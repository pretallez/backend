package com.pretallez.service.impls;

import com.pretallez.common.exception.EntityNotFoundException;
import com.pretallez.model.dto.chatroom.ChatroomCreate;
import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.VotePost;
import com.pretallez.repository.ChatroomRepository;
import com.pretallez.service.ChatroomService;
import com.pretallez.service.VotePostService;
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
            Chatroom savedChatroom = chatroomRepository.save(Chatroom.of(foundVotePost));
            return ChatroomCreate.Response.fromEntity(savedChatroom);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException (String.format("ID [%d]에 해당하는 채팅방이 이미 존재합니다.", foundVotePost.getId()), e);
        }
    }

    @Override
    public Chatroom getChatroom(Long chatroomId) {
        return chatroomRepository.findById(chatroomId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("ID [%d]에 해당하는 채팅방을 찾을 수 없습니다.", chatroomId)));
    }

    @Override
    public void removeChatroom() {

    }
}
