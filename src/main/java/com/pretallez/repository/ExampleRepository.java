package com.pretallez.repository;

import com.pretallez.model.entity.Example;

import java.util.Optional;

public interface ExampleRepository {

    void save(Example example);

    Optional<Example> findById(Long id);
}
