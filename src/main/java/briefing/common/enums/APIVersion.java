package briefing.common.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonValue;

import briefing.exception.ErrorCode;
import briefing.exception.handler.BriefingException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum APIVersion {
V1("v1"),
V2("v2");

private final String version;

public static APIVersion findByValue(String type) {
	return Arrays.stream(values())
		.filter(value -> value.getVersion().equals(type))
		.findAny()
		.orElseThrow(() -> new BriefingException(ErrorCode.NOT_FOUND_TYPE));
}

@JsonValue
String getAPIVersion() {
	return this.getVersion();
}
}
