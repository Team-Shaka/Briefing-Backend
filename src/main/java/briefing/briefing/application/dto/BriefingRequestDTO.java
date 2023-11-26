package briefing.briefing.application.dto;

import briefing.briefing.domain.TimeOfDay;
import briefing.chatting.domain.GptModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

public class BriefingRequestDTO {

    @Getter
    public static class ArticleCreateDTO{
        String press;
        String title;
        String url;
    }

    @Getter
    public static class BriefingCreate{
        @JsonProperty("id") Integer ranks;
        @JsonProperty("keyword") String title;
        String subtitle;
        @JsonProperty("context") String content;
        List<ArticleCreateDTO> articles;
        GptModel gptModel = GptModel.GPT_3_5_TURBO;
        TimeOfDay timeOfDay = TimeOfDay.MORNING;
    }
}
