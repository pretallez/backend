package com.pretallez.common.exception;

import com.pretallez.common.response.ResCode;

public class RedisException extends BaseException {
	public RedisException(ResCode resCode, Object... args) {
		super(resCode, args);
	}

	public RedisException(ResCode resCode, Throwable cause, Object... args) {
		super(resCode, cause, args);
	}
}
