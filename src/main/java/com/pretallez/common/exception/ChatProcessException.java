package com.pretallez.common.exception;

import com.pretallez.common.response.ResCode;

public class ChatProcessException extends BaseException {
	public ChatProcessException(ResCode resCode, Object... args) {
		super(resCode, args);
	}

	public ChatProcessException(ResCode resCode, Throwable cause, Object... args) {
		super(resCode, cause, args);
	}
}
