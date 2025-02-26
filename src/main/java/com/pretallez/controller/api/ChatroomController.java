package com.pretallez.controller.api;

import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.response.ResSuccessCode;
import com.pretallez.model.dto.chatroom.ChatroomCreate;
import com.pretallez.service.ChatroomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/chatroom")
public class ChatroomController {

    private final ChatroomService chatroomService;

    @PostMapping
    public CustomApiResponse<ChatroomCreate.Response> addChatroom(@Valid @RequestBody ChatroomCreate.Request params) {
        ChatroomCreate.Response response = chatroomService.addChatroom(params);
        return CustomApiResponse.OK(ResSuccessCode.CREATED, response);
    }

}
