package com.pretallez.controller.api;

import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.response.ResSuccessCode;
import com.pretallez.model.dto.chatroom.ChatroomCreate;
import com.pretallez.service.ChatroomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/chatrooms")
public class ChatroomController {

    private final ChatroomService chatroomService;

    @PostMapping
    public CustomApiResponse<ChatroomCreate.Response> addChatroom(@Valid @RequestBody ChatroomCreate.Request chatroomCreateRequest) {
        ChatroomCreate.Response chatroomCreateResponse = chatroomService.addChatroom(chatroomCreateRequest);
        return CustomApiResponse.OK(ResSuccessCode.CREATED, chatroomCreateResponse);
    }
}
