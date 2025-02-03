package com.pretallez.service.impls;

import com.pretallez.model.entity.Example;
import com.pretallez.service.ExampleService;
import com.pretallez.util.fixture.Fixture;
import com.pretallez.util.repository.FakeExampleRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExampleServiceImplsTest {

    FakeExampleRepository fakeExampleRepository = new FakeExampleRepository();

    private final ExampleService exampleService = new ExampleServiceImpls(fakeExampleRepository);

    @Test
    void getById() {
        //given
        Long id = 1L;
        Example tempExample = Fixture.example();

        //when
        fakeExampleRepository.save(tempExample);
        Example example = exampleService.getById(id);

        //then
        assertAll(
                () -> assertEquals(tempExample.getName(), example.getName()),
                () -> assertEquals(tempExample.getNickname(), example.getNickname())
        );
    }

    @Test
    void save() {
        Example example = Fixture.example();
        exampleService.save(example);
    }
}