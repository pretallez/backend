package com.pretallez.domain.chatting.service.memberchatroom;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pretallez.domain.chatting.entity.Chatroom;
import com.pretallez.domain.member.entity.Member;
import com.pretallez.domain.chatting.entity.MemberChatroom;
import com.pretallez.common.exception.EntityException;
import com.pretallez.common.enums.error.EntityErrorCode;
import com.pretallez.domain.chatting.service.chatroom.ChatroomService;
import com.pretallez.domain.member.service.MemberService;
import com.pretallez.domain.chatting.dto.memberchatroom.ChatroomMembersRead;
import com.pretallez.domain.chatting.dto.memberchatroom.MemberChatroomCreate;
import com.pretallez.domain.chatting.dto.memberchatroom.MemberChatroomDelete;
import com.pretallez.domain.chatting.dto.memberchatroom.MemberChatroomsRead;
import com.pretallez.domain.chatting.repository.memberchatroom.MemberChatroomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberChatroomServiceImpl implements MemberChatroomService {

    private final ChatroomService chatroomService;
    private final MemberService memberService;

    private final MemberChatroomRepository memberChatroomRepository;

    @Override
    @Transactional
    public MemberChatroomCreate.Response addMemberToChatroom(MemberChatroomCreate.Request memberChatroomCreateRequest) {
        Member foundMember = memberService.getMember(memberChatroomCreateRequest.getMemberId());
        Chatroom foundChatroom = chatroomService.getChatroom(memberChatroomCreateRequest.getChatroomId());

        MemberChatroom memberChatroom = MemberChatroomCreate.Request.toEntity(foundMember, foundChatroom);

        try {
            MemberChatroom savedMemberChatroom = memberChatroomRepository.save(memberChatroom);
            return MemberChatroomCreate.Response.fromEntity(savedMemberChatroom);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException (String.format("해당 회원[%d]은 이미 채팅방[%d]에 참가하였습니다.", foundMember.getId(), foundChatroom.getId()), e);
        }
    }

    @Override
    @Transactional
    public void removeMemberFromChatroom(MemberChatroomDelete.Request memberChatroomDeleteRequest) {
        Member foundMember = memberService.getMember(memberChatroomDeleteRequest.getMemberId());
        Chatroom foundChatroom = chatroomService.getChatroom(memberChatroomDeleteRequest.getChatroomId());

        MemberChatroom foundMemberChatroom = getMemberChatroom(foundMember, foundChatroom);
        memberChatroomRepository.delete(foundMemberChatroom);
    }

    @Override
    public MemberChatroom getMemberChatroom(Member member, Chatroom chatroom) {
        return memberChatroomRepository.findByMemberAndChatroom(member, chatroom)
                .orElseThrow(() -> new EntityException(EntityErrorCode.MEMBER_CHATROOM_NOT_FOUND, member.getId(), chatroom.getId()));
    }

    @Override
    public List<MemberChatroomsRead.Response> getMemberChatrooms(Long memberId) {
        return memberChatroomRepository.findChatroomsByMemberId(memberId);
    }

    @Override
    @Transactional
    public List<ChatroomMembersRead.Response> getChatroomMembers(Long chatroomId) {
        return memberChatroomRepository.findMembersByChatroomId(chatroomId);
    }

    @Override
    public boolean checkMemberInChatroom(Long memberId, Long chatroomId) {
        validateInput(memberId, chatroomId);

        boolean exists = memberChatroomRepository.existsMemberInChatroom(memberId, chatroomId);
        if (!exists) {
            throw new EntityException(EntityErrorCode.MEMBER_CHATROOM_NOT_FOUND, memberId, chatroomId);
        }
        return true;
    }

    private void validateInput(Long memberId, Long chatroomId) {
        if (memberId == null || chatroomId == null) {
            throw new IllegalArgumentException("Member ID and Chatroom ID cannot be null");
        }
    }
}
