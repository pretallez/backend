package com.pretallez.model.dto.example;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ExampleCreate {

    //TODO : reflection 에 따라 수정 예정
    @NoArgsConstructor
    @Getter
    public static class Request{

        @NotNull
        private String name;

        @NotNull
        private String nickname;

    }

}
