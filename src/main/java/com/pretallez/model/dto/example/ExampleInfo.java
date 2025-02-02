package com.pretallez.model.dto.example;

import com.pretallez.model.entity.Example;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ExampleInfo {


    //TODO : reflection 이 어떻게 되는지 모르겠어서 우선 public 으로 두었습니다
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    @Getter
    public static class Response{

        private String name;
        private String nickname;
        private LocalDateTime createdAt;

        private Response(Example example) {
            this.name = example.getName();
            this.nickname = example.getNickname();
            this.createdAt = example.getCreatedAt();
        }

        public static Response from(Example example) {
            return new Response(example);
        }

    }

}
