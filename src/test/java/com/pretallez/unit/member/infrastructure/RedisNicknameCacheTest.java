package com.pretallez.unit.member.infrastructure;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.pretallez.domain.member.repository.MemberRepository;
import com.pretallez.infrastructure.member.cache.NicknameCacheStore;

class RedisNicknameCacheTest {

	StringRedisTemplate redisTemplate;
	ValueOperations<String, String> valueOperations;
	MemberRepository memberRepository;

	NicknameCacheStore sut;

	@BeforeEach
	void setUp() {
		redisTemplate = mock(StringRedisTemplate.class);
		valueOperations = mock(ValueOperations.class);
		memberRepository = mock(MemberRepository.class);

		sut = new NicknameCacheStore(redisTemplate, memberRepository);
	}

	@Test
	void 캐시에_모든_닉네임이_존재하면_DB조회없이_캐시값을_반환한다() {
		// given
		Set<Long> memberIds = Set.of(3L, 1L, 2L);
		List<String> keys = List.of("member:nickname:1", "member:nickname:2", "member:nickname:3");

		when(redisTemplate.opsForValue()).thenReturn(valueOperations);
		when(valueOperations.multiGet(keys)).thenReturn(List.of("임종엽", "김성호", "엄인용"));

		// when
		Map<Long, String> result = sut.getNicknames(memberIds);

		// then
		assertThat(result)
			.containsEntry(1L, "임종엽")
			.containsEntry(2L, "김성호")
			.containsEntry(3L, "엄인용");
	}

	@Test
	void 일부_닉네임이_캐시에_없으면_DB에서_조회하고_캐시에_저장한다() {
		// given
		Set<Long> memberIds = Set.of(1L, 2L, 3L);
		List<String> keys = List.of("member:nickname:1", "member:nickname:2", "member:nickname:3");

		when(redisTemplate.opsForValue()).thenReturn(valueOperations);
		when(valueOperations.multiGet(keys)).thenReturn(Arrays.asList("임종엽", null, null));

		when(memberRepository.findNicknamesByIds(Set.of(2L, 3L)))
			.thenReturn(Map.of(2L, "김성호", 3L, "엄인용"));

		// when
		Map<Long, String> result = sut.getNicknames(memberIds);

		// then
		assertThat(result)
			.containsEntry(1L, "임종엽")
			.containsEntry(2L, "김성호")
			.containsEntry(3L, "엄인용");

		verify(valueOperations).set("member:nickname:2", "김성호");
		verify(valueOperations).set("member:nickname:3", "엄인용");
	}
}