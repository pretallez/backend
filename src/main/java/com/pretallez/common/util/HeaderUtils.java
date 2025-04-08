package com.pretallez.common.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HeaderUtils {
	private static final String AUTH_HEADER_PREFIX = "Basic";
	private static final String BASIC_DELIMITER = ":";

	/**
	 * Basic 인증 헤더와 Content-Type 헤더를 포함한 Map을 반환한다.
	 *
	 * @param secretKey 인증에 사용할 secretKey
	 * @return          헤더 Map
	 */
	public static Map<String, String> basicAuthHeaders(String secretKey) {
		String credentials = secretKey + BASIC_DELIMITER;
		String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

		return Map.of(
			HttpHeaders.AUTHORIZATION, AUTH_HEADER_PREFIX + " " + encoded,
			HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
		);
	}
}
