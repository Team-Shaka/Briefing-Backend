package briefing.briefing.api;

import briefing.briefing.application.dto.BriefingRequestDTO;
import briefing.briefing.application.dto.BriefingResponseDTO;
import briefing.briefing.domain.Article;
import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.BriefingType;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public class BriefingConverter {

    public static BriefingResponseDTO.BriefingPreviewDTO toBriefingPreviewDTO(Briefing briefing){
        return BriefingResponseDTO.BriefingPreviewDTO.builder()
                .id(briefing.getId())
                .ranks(briefing.getRanks())
                .title(briefing.getTitle())
                .subtitle(briefing.getSubtitle())
                .build();
    }

    public static BriefingResponseDTO.BriefingPreviewListDTO toBriefingPreviewListDTO(final LocalDate date, List<Briefing> briefingList){
        final List<BriefingResponseDTO.BriefingPreviewDTO> briefingPreviewDTOList = briefingList.stream()
                .map(BriefingConverter::toBriefingPreviewDTO).toList();

        return BriefingResponseDTO.BriefingPreviewListDTO.builder()
                .createdAt(date.atTime(3,0))
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

    public static BriefingResponseDTO.BriefingDetailDTO toBriefingDetailDTO(Briefing briefing, Boolean isScrap){

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
                .build();
    }

    public static Briefing toBriefing(BriefingRequestDTO.BriefingCreate request){
        return Briefing.builder()
                .type(BriefingType.KOREA)
                .ranks(request.getRanks())
                .title(request.getTitle())
                .subtitle(request.getSubtitle())
                .content(request.getContent())
                .build();
    }

    public static Article toArticle(BriefingRequestDTO.ArticleCreateDTO request){
        return Article.builder()
                .press(request.getPress())
                .title(request.getTitle())
                .url(request.getUrl())
                .build();
    }
}
