package com.pretallez.repository.impls;

import com.pretallez.model.entity.Example;
import com.pretallez.repository.ExampleRepository;
import com.pretallez.repository.jpa.ExampleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ExampleRepositoryImpl implements ExampleRepository {

    private final ExampleJpaRepository exampleJpaRepository;

    @Override
    public void save(Example example) {
        exampleJpaRepository.save(example);
    }

    @Override
    public Optional<Example> findById(Long id) {
        return exampleJpaRepository.findById(id);
    }
}
