package com.pretallez.common.repository;

import com.pretallez.model.entity.Chatroom;
import com.pretallez.repository.ChatroomRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class FakeChatroomRepository implements ChatroomRepository {

    private final Map<Long, Chatroom> entities = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Chatroom save(Chatroom chatroom) {
        boolean exists = entities.values().stream()
                .anyMatch(existingChatroom -> existingChatroom.getVotePost().getId().equals(chatroom.getVotePost().getId()));

        if (exists) {
            throw new DataIntegrityViolationException(String.format("ID [%d]에 해당하는 채팅방이 이미 존재합니다.", chatroom.getVotePost().getId()));
        }

        long id = idGenerator.getAndIncrement();
        ReflectionTestUtils.setField(chatroom, "id", id);
        entities.put(id, chatroom);
        return chatroom;
    }

    @Override
    public boolean existsByVotePostId(Long votePostId) {
        return entities.values().stream()
                .anyMatch(chatroom -> chatroom.getVotePost().getId().equals(votePostId));
    }
}
