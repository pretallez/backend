package com.pretallez.infrastructure.member.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.pretallez.domain.member.repository.MemberRepository;
import com.pretallez.application.chat.port.output.ChatNicknameProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NicknameCacheStore implements ChatNicknameProvider {
	private static final String KEY_PREFIX = "member:nickname:";

	private final StringRedisTemplate redisTemplate;
	private final MemberRepository memberRepository;

	@Override
	public Map<Long, String> getNicknames(Set<Long> memberIds) {
		Map<Long, String> nicknameStore = new HashMap<>();
		Set<Long> missedStore = new HashSet<>();

		List<Long> ids = memberIds.stream()
			.sorted()
			.toList();

		List<String> keys = ids.stream()
			.map(this::key)
			.toList();

		List<String> cached = redisTemplate.opsForValue().multiGet(keys);

		for (int i = 0; i < ids.size(); i++) {
			Long id = ids.get(i);
			String nickname = cached.get(i);

			if (nickname != null) {
				nicknameStore.put(id, nickname);
			} else {
				missedStore.add(id);
			}
		}

		if (!missedStore.isEmpty()) {
			Map<Long, String> dbNicknames = memberRepository.findNicknamesByIds(missedStore);
			dbNicknames.forEach((id, nickname) -> {
				redisTemplate.opsForValue().set(key(id), nickname);
				nicknameStore.put(id, nickname);
			});
		}

		return nicknameStore;
	}

	private String key(Long id) {
		return KEY_PREFIX + id;
	}
}
