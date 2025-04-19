package com.pretallez.common.enums.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.pretallez.common.response.ResCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResErrorCode implements ResCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 40000, "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 40100, "인증이 필요한 요청입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, 40300, "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, 40400, "요청하신 리소스를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 40500, "허용되지 않은 HTTP 메서드입니다."),
    CONFLICT(HttpStatus.CONFLICT, 40900, "요청이 충돌되었습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "서버 내부 오류가 발생했습니다."),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, 50300, "현재 서비스를 이용할 수 없습니다."),
    API_CALL_FAILED(HttpStatus.SERVICE_UNAVAILABLE, 50301, "외부 API 호출에 실패했습니다.");

    private final HttpStatusCode httpStatusCode;
    private final Integer code;
    private final String description;
}

