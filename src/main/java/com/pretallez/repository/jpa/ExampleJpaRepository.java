package com.pretallez.repository.jpa;

import com.pretallez.model.entity.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExampleJpaRepository extends JpaRepository<Example,Long> {
}
