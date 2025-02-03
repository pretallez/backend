package com.pretallez.util.fixture;

import com.pretallez.model.dto.example.ExampleCreate;
import com.pretallez.model.entity.Example;

public class Fixture {

    public static Example example() {
        String fakeName = "김성호";
        String fakeNickName = "ksngh";
        ExampleCreate.Request request = ExampleCreate.Request.of(fakeName, fakeNickName);
        return Example.create(request);
    }
}
