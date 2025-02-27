package com.pretallez.service.impls;

import com.pretallez.common.exception.EntityNotFoundException;
import com.pretallez.model.dto.chatroom.ChatroomCreate;
import com.pretallez.model.dto.memberchatroom.MemberChatroomCreate;
import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.Member;
import com.pretallez.model.entity.MemberChatroom;
import com.pretallez.model.entity.VotePost;
import com.pretallez.repository.ChatroomRepository;
import com.pretallez.repository.MemberChatroomRepository;
import com.pretallez.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final MemberChatroomRepository memberChatroomRepository;

    @Override
    public ChatroomCreate.Response addChatroom(ChatroomCreate.Request chatroomCreateRequest) {
        VotePost foundVotePost = getVotePostOrThrow(chatroomCreateRequest.getVotePostId());

        try {
            Chatroom savedChatroom = chatroomRepository.save(Chatroom.of(foundVotePost));
            return ChatroomCreate.Response.fromEntity(savedChatroom);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException (String.format("ID [%d]에 해당하는 채팅방이 이미 존재합니다.", foundVotePost.getId()), e);
        }
    }

    @Override
    public MemberChatroomCreate.Response addMemberToChatroom(MemberChatroomCreate.Request memberChatroomCreateRequest) {
        Member foundMember = getMemberOrThrow(memberChatroomCreateRequest.getMemberId());
        Chatroom foundChatroom = getChatroomOrThrow(memberChatroomCreateRequest.getChatroomId());

        MemberChatroom memberChatroom = MemberChatroomCreate.Request.toEntity(foundMember, foundChatroom);

        try {
            MemberChatroom savedMemberChatroom = memberChatroomRepository.save(memberChatroom);
            return MemberChatroomCreate.Response.fromEntity(savedMemberChatroom);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException (String.format("해당 회원[%d]은 이미 채팅방[%d]에 참가하였습니다.", foundMember.getId(), foundChatroom.getId()), e);
        }
    }

    private VotePost getVotePostOrThrow(Long votePostId) {
        return votePostRepository.findById(votePostId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("ID [%d]에 해당하는 투표 게시글을 찾을 수 없습니다.", votePostId)));
    }

    private Member getMemberOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("ID [%d]에 해당하는 회원을 찾을 수 없습니다.", memberId)));
    }

    private Chatroom getChatroomOrThrow(Long chatroomId) {
        return chatroomRepository.findById(chatroomId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("ID [%d]에 해당하는 채팅방을 찾을 수 없습니다.", chatroomId)));
    }
}
