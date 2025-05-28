package com.pretallez.domain.chat.exception;

import com.pretallez.common.exception.BaseException;
import com.pretallez.common.response.ResCode;

public class ChatException extends BaseException {

	public ChatException(ResCode resCode, Object... args) {
		super(resCode, args);

	}
	public ChatException(ResCode resCode, Throwable cause, Object... args) {
		super(resCode, cause, args);
	}
}
