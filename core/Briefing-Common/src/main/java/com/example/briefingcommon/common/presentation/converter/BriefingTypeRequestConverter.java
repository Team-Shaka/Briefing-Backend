package com.example.briefingcommon.common.presentation.converter;

import com.example.briefingcommon.entity.enums.BriefingType;
import org.springframework.core.convert.converter.Converter;
import lombok.NonNull;

public class BriefingTypeRequestConverter implements Converter<String, BriefingType> {

    @Override
    public BriefingType convert(@NonNull final String type) {
        return BriefingType.from(type);
    }
}
