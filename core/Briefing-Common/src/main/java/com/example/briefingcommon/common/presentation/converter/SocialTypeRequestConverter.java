package com.example.briefingcommon.common.presentation.converter;

import com.example.briefingcommon.entity.enums.SocialType;
import org.springframework.core.convert.converter.Converter;



public class SocialTypeRequestConverter implements Converter<String, SocialType> {
    @Override
    public SocialType convert(String source) {
        return SocialType.findByValue(source);
    }
}
