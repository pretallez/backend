package com.pretallez.service.impls;

import com.pretallez.common.exception.CustomApiException;
import com.pretallez.common.response.ResErrorCode;
import com.pretallez.model.entity.Example;
import com.pretallez.repository.ExampleRepository;
import com.pretallez.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExampleServiceImpls implements ExampleService {

    private final ExampleRepository exampleRepository;

    @Override
    public Example getById(Long id) {
        return exampleRepository.findById(id)
                .orElseThrow(
                        ()->new CustomApiException(ResErrorCode.NOT_FOUND, "해당하는 Example을 찾을 수 없습니다.")
                );
    }

    @Override
    public void save(Example example) {
        exampleRepository.save(example);
    }

}
