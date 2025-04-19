package com.pretallez.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.enums.success.ResSuccessCode;
import com.pretallez.domain.posting.dto.votepost.VotePostCreate;
import com.pretallez.domain.votepost.service.VotePostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class VotePostController {

    private final VotePostService votePostService;

    @PostMapping("/vote-posts")
    public CustomApiResponse<VotePostCreate.Response> createVotePost(@Valid @RequestBody VotePostCreate.Request votePostCreateRequest) {
        Long writerId = 1L;
        VotePostCreate.Response votePostCreateResponse = votePostService.addVotePost(writerId, votePostCreateRequest);
        return CustomApiResponse.OK(ResSuccessCode.CREATED, votePostCreateResponse);
    }

}
