package com.pretallez.common.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ScoreUtil {

	public static double buildScore(LocalDateTime timestamp, Long id) {
		return timestamp.toInstant(ZoneOffset.UTC).toEpochMilli() + id;
	}
}
