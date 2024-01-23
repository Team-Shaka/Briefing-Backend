package com.example.briefingapi.briefing.business;

import java.util.List;
import java.util.Optional;

import com.example.briefingapi.briefing.implement.service.ArticleCommandService;
import com.example.briefingapi.briefing.implement.service.BriefingArticleCommandService;
import com.example.briefingapi.briefing.implement.service.BriefingCommandService;
import com.example.briefingapi.briefing.implement.service.BriefingQueryService;
import com.example.briefingapi.briefing.presentation.dto.BriefingRequestDTO;
import com.example.briefingapi.briefing.presentation.dto.BriefingRequestParam;
import com.example.briefingapi.briefing.presentation.dto.BriefingResponseDTO;
import com.example.briefingapi.scrap.implement.ScrapQueryService;
import com.example.briefingcommon.entity.Article;
import com.example.briefingcommon.entity.Briefing;
import com.example.briefingcommon.entity.BriefingArticle;
import com.example.briefingcommon.entity.Member;
import com.example.briefingcommon.entity.enums.APIVersion;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


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
