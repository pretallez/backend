package com.pretallez.common.util;

import java.util.Arrays;

public class EnumUtils {

	public static <T extends Enum<T>> T findByNameOrThrow(Class<T> enumClass, String name) {
		return Arrays.stream(enumClass.getEnumConstants())
			.filter(e -> e.name().equals(name))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("알수없는 Enum 명입니다. " + name));
	}
}

