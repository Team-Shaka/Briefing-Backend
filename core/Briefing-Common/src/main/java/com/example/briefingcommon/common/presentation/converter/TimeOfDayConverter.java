package com.example.briefingcommon.common.presentation.converter;

import com.example.briefingcommon.entity.enums.TimeOfDay;
import org.springframework.core.convert.converter.Converter;


import lombok.NonNull;

public class TimeOfDayConverter implements Converter<String, TimeOfDay> {
    @Override
    public TimeOfDay convert(@NonNull final String source) {
        return TimeOfDay.findByValue(source);
    }
}
