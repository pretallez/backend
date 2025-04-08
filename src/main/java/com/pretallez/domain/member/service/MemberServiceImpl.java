package com.pretallez.domain.member.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.pretallez.common.entity.Member;
import com.pretallez.common.exception.EntityException;
import com.pretallez.common.exception.RedisException;
import com.pretallez.common.response.error.EntityErrorCode;
import com.pretallez.common.response.error.RedisErrorCode;
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
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityException(EntityErrorCode.MEMBER_NOT_FOUND, email));
    }

    @Override
    public String getNickname(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("memberId는 null일 수 없습니다.");
        }
        String nickNameKey = buildRedisNicknameKey(memberId);
        String nickname;

        try {
            nickname = redisTemplate.opsForValue().get(nickNameKey);
            if (nickname != null) {
                return nickname;
            }

            nickname = getMember(memberId).getNickname();
            if (nickname == null) {
                nickname = "Unknown";
            }
            redisTemplate.opsForValue().set(nickNameKey, nickname, 1, TimeUnit.HOURS);

            return nickname;
        } catch (Exception e) {
            throw new RedisException(RedisErrorCode.REDIS_CACHE_GET_NICKNAME_FAILED, e, memberId);
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
