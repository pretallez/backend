package com.pretallez.service.impls;

import com.pretallez.model.dto.memberchatroom.MemberChatroomCreate;
import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.Member;
import com.pretallez.model.entity.MemberChatroom;
import com.pretallez.repository.MemberChatroomRepository;
import com.pretallez.service.ChatroomService;
import com.pretallez.service.MemberChatroomService;
import com.pretallez.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberChatroomServiceImpl implements MemberChatroomService {

    private final ChatroomService chatroomService;
    private final MemberService memberService;

    private final MemberChatroomRepository memberChatroomRepository;

    @Override
    public MemberChatroomCreate.Response addMemberToChatroom(MemberChatroomCreate.Request memberChatroomCreateRequest) {
        Member foundMember = memberService.getMemberOrThrow(memberChatroomCreateRequest.getMemberId());
        Chatroom foundChatroom = chatroomService.getChatroomOrThrow(memberChatroomCreateRequest.getChatroomId());

        MemberChatroom memberChatroom = MemberChatroomCreate.Request.toEntity(foundMember, foundChatroom);

        try {
            MemberChatroom savedMemberChatroom = memberChatroomRepository.save(memberChatroom);
            return MemberChatroomCreate.Response.fromEntity(savedMemberChatroom);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException (String.format("해당 회원[%d]은 이미 채팅방[%d]에 참가하였습니다.", foundMember.getId(), foundChatroom.getId()), e);
        }
    }
}
