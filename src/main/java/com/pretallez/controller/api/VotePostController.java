package com.pretallez.controller.api;

import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.response.ResSuccessCode;
import com.pretallez.model.dto.VotePostCreate;
import com.pretallez.service.VotePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class VotePostController {

    private final VotePostService votePostService;

    @PostMapping("/vote-posts")
    public CustomApiResponse<VotePostCreate.Response> createVotePost(@RequestBody VotePostCreate.Request votePostCreateRequest) {
        Long writerId = 1L;
        VotePostCreate.Response votePostCreateResponse = votePostService.addVotePost(writerId, votePostCreateRequest);
        return CustomApiResponse.OK(ResSuccessCode.CREATED, votePostCreateResponse);
    }

}
