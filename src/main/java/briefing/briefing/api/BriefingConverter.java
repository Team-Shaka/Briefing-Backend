package briefing.briefing.api;

import briefing.briefing.application.dto.BriefingRequestDTO;
import briefing.briefing.application.dto.BriefingResponseDTO;
import briefing.briefing.domain.Article;
import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.BriefingType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class BriefingConverter {

    public static BriefingResponseDTO.BriefingPreviewDTO toBriefingPreviewDTO(Briefing briefing){
        return BriefingResponseDTO.BriefingPreviewDTO.builder()
                .id(briefing.getId())
                .ranks(briefing.getRanks())
                .title(briefing.getTitle())
                .subtitle(briefing.getSubtitle())
                .scrapCount(briefing.getScrapCount())
                .build();
    }

    private static LocalDateTime getPreviewListDTOCreatedAt(final LocalDate date, List<Briefing> briefingList) {
        if(!briefingList.isEmpty()) {
            return briefingList.get(0).getCreatedAt();
        }
        if(date != null) {
            return date.atTime(3,0);
        }
        return LocalDateTime.now();
    }

    public static BriefingResponseDTO.BriefingPreviewListDTO toBriefingPreviewListDTO(final LocalDate date, List<Briefing> briefingList){
        final List<BriefingResponseDTO.BriefingPreviewDTO> briefingPreviewDTOList = briefingList.stream()
                .map(BriefingConverter::toBriefingPreviewDTO).toList();

        return BriefingResponseDTO.BriefingPreviewListDTO.builder()
                .createdAt(getPreviewListDTOCreatedAt(date, briefingList))
                .briefings(briefingPreviewDTOList)
                .build();
    }

    public static BriefingResponseDTO.ArticleResponseDTO toArticleResponseDTO(final Article article){
        return BriefingResponseDTO.ArticleResponseDTO.builder()
                .id(article.getId())
                .press(article.getPress())
                .title(article.getTitle())
                .url(article.getUrl())
                .build();
    }

    public static BriefingResponseDTO.BriefingDetailDTO toBriefingDetailDTO(
            Briefing briefing,
            Boolean isScrap,
            Boolean isBriefingOpen,
            Boolean isWarning
    ){

        List<BriefingResponseDTO.ArticleResponseDTO> articleResponseDTOList = briefing.getBriefingArticles().stream()
                .map(article -> toArticleResponseDTO(article.getArticle())).toList();

        return BriefingResponseDTO.BriefingDetailDTO.builder()
                .id(briefing.getId())
                .ranks(briefing.getRanks())
                .title(briefing.getTitle())
                .subtitle(briefing.getSubtitle())
                .content(briefing.getContent())
                .date(briefing.getCreatedAt().toLocalDate())
                .articles(articleResponseDTOList)
                .isScrap(isScrap)
                .isBriefingOpen(isBriefingOpen)
                .isWarning(isWarning)
                .scrapCount(briefing.getScrapCount())
                .gptModel(briefing.getGptModel())
                .timeOfDay(briefing.getTimeOfDay())
                .type(briefing.getType())
                .build();
    }

    public static Briefing toBriefing(BriefingRequestDTO.BriefingCreate request){
        return Briefing.builder()
                .type(request.getBriefingType())
                .ranks(request.getRanks())
                .title(request.getTitle())
                .subtitle(request.getSubtitle())
                .content(request.getContent())
                .gptModel(request.getGptModel())
                .timeOfDay(request.getTimeOfDay())
                .build();
    }

    public static Article toArticle(BriefingRequestDTO.ArticleCreateDTO request){
        return Article.builder()
                .press(request.getPress())
                .title(request.getTitle())
                .url(request.getUrl())
                .build();
    }


    public static BriefingResponseDTO.BriefingPreviewV2TempDTO toBriefingPreviewV2TempDTO(Long id){
        Integer rank = null;
        String title = null;
        String subTitle = null;
        Integer scrapCount = null;

        if (id.equals(346L)){
            rank = 1;
            title = "소셜 1";
            subTitle = "브리핑 부제목 1";
            scrapCount = 1234;
        }
        else if (id.equals(347L)){
            rank = 2;
            title = "소셜 2";
            subTitle = "브리핑 부제목 2";
            scrapCount = 123;
        }else if(id.equals(348L)){
            rank = 3;
            title = "소셜 3";
            subTitle = "브리핑 부제목 3";
            scrapCount = 13;
        }else if (id.equals(349L)){
            rank = 4;
            title = "소셜 4";
            subTitle = "브리핑 부제목 4";
            scrapCount = 12323;
        }else if (id.equals(350L)){
            rank = 5;
            title = "소셜 5";
            subTitle = "브리핑 부제목 5";
            scrapCount = 123;
        }

        return BriefingResponseDTO.BriefingPreviewV2TempDTO.builder()
                .id(id)
                .ranks(rank)
                .title(title)
                .subtitle(subTitle)
                .scrapCount(scrapCount)
                .build();
    }

    public static BriefingResponseDTO.BriefingV2PreviewListDTO toBriefingPreviewV2TempListDTO(final LocalDate date,  List<Long> temp, BriefingType briefingType){
        List<BriefingResponseDTO.BriefingPreviewV2TempDTO> tempDTOList = temp.stream()
                .map(
                        BriefingConverter::toBriefingPreviewV2TempDTO
                ).toList();

        return BriefingResponseDTO.BriefingV2PreviewListDTO.builder()
                .createdAt(date.atTime(3,0))
                .type(briefingType.getValue())
                .briefings(tempDTOList)
                .build();
    }
}
