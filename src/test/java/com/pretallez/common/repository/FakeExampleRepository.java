package com.pretallez.common.repository;

import com.pretallez.model.entity.Example;
import com.pretallez.repository.ExampleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeExampleRepository implements ExampleRepository {

    private static final Logger log = LoggerFactory.getLogger(FakeExampleRepository.class);
    private final Map<Long, Example> storage = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Optional<Example> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void save(Example example) {
        long id = sequence.incrementAndGet();
        setPrivateField(example, "id", id);
        storage.put(id, example);
    }

    private void setPrivateField(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error(e.getMessage());
        }
    }

}
