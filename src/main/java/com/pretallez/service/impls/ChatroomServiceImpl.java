package com.pretallez.service.impls;

import com.pretallez.common.exception.CustomApiException;
import com.pretallez.common.exception.EntityNotFoundException;
import com.pretallez.common.response.ResErrorCode;
import com.pretallez.model.dto.chatroom.ChatroomCreate;
import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.VotePost;
import com.pretallez.repository.ChatroomRepository;
import com.pretallez.repository.VotePostRepository;
import com.pretallez.service.ChatroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatroomServiceImpl implements ChatroomService {

    private final ChatroomRepository chatroomRepository;
    private final VotePostRepository votePostRepository;

    @Override
    public ChatroomCreate.Response addChatroom(ChatroomCreate.Request chatroomCreateRequest) {
        VotePost foundVotePost = votePostRepository.findById(chatroomCreateRequest.getVotePostId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("ID [%d]에 해당하는 투표 게시글을 찾을 수 없습니다.", chatroomCreateRequest.getVotePostId())));

        if (chatroomRepository.existsByVotePostId(foundVotePost.getId())) {
            throw new DataIntegrityViolationException(String.format("ID [%d]에 해당하는 채팅방이 이미 존재합니다.", foundVotePost.getId()));
        }

        Chatroom savedChatroom = chatroomRepository.save(Chatroom.of(foundVotePost));
        return ChatroomCreate.Response.fromEntity(savedChatroom);
    }
}
