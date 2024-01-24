package com.example.briefingcommon.entity.enums;

import java.util.Arrays;

import com.example.briefingcommon.common.exception.ChattingException;
import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GptModel {
    GPT_3_5_TURBO("gpt-3.5-turbo"),
    GPT_4("gpt-4");

    @JsonValue private final String value;

    @JsonCreator
    public static GptModel from(final String model) {
        return Arrays.stream(values())
                .filter(value -> value.getValue().equals(model))
                .findAny()
                .orElseThrow(() -> new ChattingException(ErrorCode.NOT_FOUND_MODEL));
    }
}
