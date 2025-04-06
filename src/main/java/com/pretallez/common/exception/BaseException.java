package com.pretallez.common.exception;

import com.pretallez.common.response.ResCode;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
	private final ResCode resCode;
	private final String description;

	protected BaseException(ResCode resCode, Object... args) {
		super(resCode.getFormattedDescription(args));
		this.resCode = resCode;
		this.description = getMessage();
	}
}
