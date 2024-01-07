package briefing.briefing.application.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import briefing.briefing.domain.BriefingType;
import briefing.briefing.domain.TimeOfDay;
import briefing.chatting.domain.GptModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    public static class BriefingUpdateDTO{
        String title;
        String subTitle;
        String content;
    }
}
