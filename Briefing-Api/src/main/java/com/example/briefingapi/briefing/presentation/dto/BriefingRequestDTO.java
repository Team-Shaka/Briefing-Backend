package com.example.briefingapi.briefing.presentation.dto;

import java.util.List;

import com.example.briefingcommon.entity.enums.BriefingType;
import com.example.briefingcommon.entity.enums.GptModel;
import com.example.briefingcommon.entity.enums.TimeOfDay;
import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.Getter;

public class BriefingRequestDTO {

    @Getter
    public static class ArticleCreateDTO {
        String press;
        String title;
        String url;
    }

    @Getter
    public static class BriefingCreate {
        @JsonProperty("id")
        Integer ranks;

        @JsonProperty("keyword")
        String title;

        String subtitle;

        @JsonProperty("context")
        String content;

        List<ArticleCreateDTO> articles;
        GptModel gptModel = GptModel.GPT_4;
        TimeOfDay timeOfDay = TimeOfDay.MORNING;
        BriefingType briefingType = BriefingType.KOREA;
    }

    @Getter
    public static class BriefingUpdateDTO {
        String title;
        String subTitle;
        String content;
    }
}
