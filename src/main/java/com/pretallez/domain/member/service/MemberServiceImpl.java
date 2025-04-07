package com.pretallez.domain.member.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.pretallez.common.entity.Member;
import com.pretallez.common.exception.EntityException;
import com.pretallez.common.response.error.EntityErrorCode;
import com.pretallez.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityException(EntityErrorCode.MEMBER_NOT_FOUND, memberId));
    }

    @Override
    public String getNickname(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("memberID cannot be null");
        }

        String nickNameKey = buildRedisNicknameKey(memberId);
        String nickname;

        try {
            nickname = redisTemplate.opsForValue().get(nickNameKey);
            if (nickname != null) {
                log.debug("Cache hit for memberId: {}", memberId);
                return nickname;
            }

            log.info("Cache miss for memberId: {}", memberId);
            Member member = getMember(memberId);
            nickname = member.getNickname();

            if (nickname == null) {
                log.warn("Nickname is null for memberId: {}", memberId);
                nickname = ""; // 기본값 설정 또는 예외 처리 고려
            }

            redisTemplate.opsForValue().set(nickNameKey, nickname, 1, TimeUnit.HOURS);
            return nickname;
        } catch (Exception e) {
            log.error("Failed to retrieve nickname for memberId: {}. Error: {}", memberId, e);
            throw new RuntimeException("Unable to retrieve nickname", e); // 커스텀 예외로 변경 필요
        }
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

    private String buildRedisNicknameKey(Long memberId) {
        return String.format("member:%d:nickname", memberId);
    }
}
