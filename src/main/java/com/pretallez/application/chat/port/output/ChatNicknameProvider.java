package com.pretallez.application.chat.port.output;

import java.util.Map;
import java.util.Set;

public interface ChatNicknameProvider {

	Map<Long, String> getNicknames(Set<Long> memberIds);
}
