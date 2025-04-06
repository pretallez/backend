package com.pretallez.common.response;

import org.springframework.http.HttpStatusCode;

public interface ResCode {

    HttpStatusCode getHttpStatusCode();
    Integer getCode();
    String getDescription();

    default String getFormattedDescription(Object... args) {
        return String.format(getDescription(), args);
    }

    default String getMessage() {
        if (this instanceof Enum<?>) {
            return ((Enum<?>) this).name();
        }
        return "Unknown";
    }
}
