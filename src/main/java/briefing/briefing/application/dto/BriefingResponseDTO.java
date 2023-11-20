package briefing.briefing.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BriefingResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArticleResponseDTO{
        Long id;
        String press;
        String title;
        String url;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BriefingPreviewDTO{
        Long id;
        Integer ranks;
        String title;
        String subtitle;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BriefingDetailDTO{
        Long id;
        Integer ranks;
        String title;
        String subtitle;
        String content;
        LocalDate date;
        List<ArticleResponseDTO> articles;
        Boolean isScrap;
        Boolean isBriefingOpen;
        Boolean isWarning;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BriefingPreviewListDTO{
        LocalDateTime createdAt;
        List<BriefingPreviewDTO> briefings;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BriefingV2PreviewListDTO{
        LocalDateTime createdAt;
        String type;
        List<BriefingPreviewV2TempDTO> briefings;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BriefingPreviewV2TempDTO{
        Long id;
        Integer ranks;
        String title;
        String subtitle;
        Integer scrapCount;
    }
}
