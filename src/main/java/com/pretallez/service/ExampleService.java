package com.pretallez.service;

import com.pretallez.model.entity.Example;

public interface ExampleService {

    Example getById(Long id);

    void save(Example example);
}
