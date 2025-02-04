package com.pretallez.controller.api;

import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.response.ResCode;
import com.pretallez.common.response.ResSuccessCode;
import com.pretallez.model.dto.example.ExampleCreate;
import com.pretallez.model.dto.example.ExampleInfo;
import com.pretallez.model.entity.Example;
import com.pretallez.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ExampleController {

    private final ExampleService exampleService;

    @GetMapping("/example/{exampleId}")
    public CustomApiResponse<ExampleInfo.Response> getExample(@PathVariable Long exampleId){
        ExampleInfo.Response response = ExampleInfo.Response.from(exampleService.getById(exampleId));
        return CustomApiResponse.OK(ResSuccessCode.READ,response);
    }

    @PostMapping("/example")
    public CustomApiResponse<Void> createExample(@RequestBody ExampleCreate.Request request){
        exampleService.save(Example.create(request));
        return CustomApiResponse.OK(ResSuccessCode.CREATED);
    }

}
