package com.pretallez.domain.chatting.exception;

import com.pretallez.common.exception.BaseException;
import com.pretallez.common.response.ResCode;

public class ChatProcessException extends BaseException {
	public ChatProcessException(ResCode resCode, Object... args) {
		super(resCode, args);
	}

	public ChatProcessException(ResCode resCode, Throwable cause, Object... args) {
		super(resCode, cause, args);
	}
}
