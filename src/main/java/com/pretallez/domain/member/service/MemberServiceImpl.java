package com.pretallez.domain.member.service;

import com.pretallez.common.exception.EntityNotFoundException;
import com.pretallez.member.service.MemberService;
import com.pretallez.common.entity.Member;
import com.pretallez.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("ID [%d]에 해당하는 회원을 찾을 수 없습니다.", memberId)));
    }

    @Override
    public void addMember() {

    }

    @Override
    public void modifyMember() {

    }

    @Override
    public void removeMember() {

    }

    @Override
    public void getMembersWithPaging() {

    }
}
