package com.example.briefingcommon.common.presentation.converter;

import com.example.briefingcommon.entity.enums.GptModel;
import org.springframework.core.convert.converter.Converter;


import lombok.NonNull;

public class GptModelRequestConverter implements Converter<String, GptModel> {

    @Override
    public GptModel convert(@NonNull final String model) {
        return GptModel.from(model);
    }
}
