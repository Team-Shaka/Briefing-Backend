package briefing.briefing.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import briefing.briefing.api.BriefingConverter;
import briefing.briefing.application.dto.BriefingRequestDTO;
import briefing.briefing.application.dto.BriefingRequestParam;
import briefing.briefing.application.dto.BriefingResponseDTO;
import briefing.briefing.application.service.ArticleCommandService;
import briefing.briefing.application.service.BriefingArticleCommandService;
import briefing.briefing.application.service.BriefingCommandService;
import briefing.briefing.application.service.BriefingQueryService;
import briefing.briefing.domain.Article;
import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.BriefingArticle;
import briefing.common.enums.APIVersion;
import briefing.member.domain.Member;
import briefing.scrap.application.ScrapQueryService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BriefingFacade {
    private final ScrapQueryService scrapQueryService;
    private final BriefingQueryService briefingQueryService;
    private final BriefingCommandService briefingCommandService;
    private final ArticleCommandService articleCommandService;
    private final BriefingArticleCommandService briefingArticleCommandService;
    private static final APIVersion version = APIVersion.V1;

    @Transactional(readOnly = true)
    public BriefingResponseDTO.BriefingPreviewListDTO findBriefings(
            BriefingRequestParam.BriefingPreviewListParam params) {
        List<Briefing> briefingList = briefingQueryService.findBriefings(params, version);
        return BriefingConverter.toBriefingPreviewListDTO(params.getDate(), briefingList);
    }

    @Transactional(readOnly = true)
    public BriefingResponseDTO.BriefingDetailDTO findBriefing(final Long id, Member member) {
        Boolean isScrap =
                Optional.ofNullable(member)
                        .map(m -> scrapQueryService.existsByMemberIdAndBriefingId(m.getId(), id))
                        .orElseGet(() -> Boolean.FALSE);

        Boolean isBriefingOpen = false;
        Boolean isWarning = false;

        return BriefingConverter.toBriefingDetailDTO(
                briefingQueryService.findBriefing(id, version), isScrap, isBriefingOpen, isWarning);
    }

    @Transactional
    public void createBriefing(final BriefingRequestDTO.BriefingCreate request) {
        final List<Article> articles =
                request.getArticles().stream().map(BriefingConverter::toArticle).toList();

        Briefing createdBriefing =
                briefingCommandService.create(BriefingConverter.toBriefing(request));
        List<Article> createdArticles = articleCommandService.createAll(articles);

        final List<BriefingArticle> briefingArticles =
                createdArticles.stream()
                        .map(article -> new BriefingArticle(createdBriefing, article))
                        .toList();

        briefingArticleCommandService.createAll(briefingArticles);
    }

    @Transactional
    public BriefingResponseDTO.BriefingUpdateDTO updateBriefing(
            Long id, final BriefingRequestDTO.BriefingUpdateDTO request) {
        Briefing briefing = briefingQueryService.findBriefing(id, APIVersion.V1);
        Briefing updatedBriefing =
                briefingCommandService.update(
                        briefing, request.getTitle(), request.getSubTitle(), request.getContent());
        return BriefingConverter.toBriefingUpdateDTO(updatedBriefing);
    }
}
