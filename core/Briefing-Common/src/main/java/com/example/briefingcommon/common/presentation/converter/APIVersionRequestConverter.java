package com.example.briefingcommon.common.presentation.converter;

import com.example.briefingcommon.entity.enums.APIVersion;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

public class APIVersionRequestConverter implements Converter<String, APIVersion> {

    @Override
    public APIVersion convert(@NonNull final String source) {
        return APIVersion.findByValue(source);
    }
}
