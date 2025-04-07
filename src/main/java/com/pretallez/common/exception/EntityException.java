package com.pretallez.common.exception;

import com.pretallez.common.response.ResCode;

public class EntityException extends BaseException {
    public EntityException(ResCode resCode, Object... args) {
        super(resCode, args);
    }
}
