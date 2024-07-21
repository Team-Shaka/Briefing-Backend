package com.example.briefingapi.briefing.business;

import java.util.List;
import java.util.Optional;

import com.example.briefingapi.briefing.implement.service.ArticleCommandAdapter;
import com.example.briefingapi.briefing.implement.service.BriefingCommandAdapter;
import com.example.briefingapi.briefing.implement.service.BriefingQueryAdapter;
import com.example.briefingapi.briefing.presentation.dto.BriefingRequestDTO;
import com.example.briefingapi.briefing.presentation.dto.BriefingRequestParam;
import com.example.briefingapi.briefing.presentation.dto.BriefingResponseDTO;
import com.example.briefingapi.scrap.implement.ScrapQueryAdapter;
import com.example.briefingcommon.entity.Article;
import com.example.briefingcommon.entity.Briefing;
import com.example.briefingcommon.entity.Member;
import com.example.briefingcommon.entity.enums.APIVersion;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BriefingService {
    private final ScrapQueryAdapter scrapQueryAdapter;
    private final BriefingQueryAdapter briefingQueryAdapter;
    private final BriefingCommandAdapter briefingCommandAdapter;
    private final ArticleCommandAdapter articleCommandAdapter;
    private static final APIVersion version = APIVersion.V1;

    @Transactional(readOnly = true)
    public BriefingResponseDTO.BriefingPreviewListDTO findBriefings(
            BriefingRequestParam.BriefingPreviewListParam params) {
        List<Briefing> briefingList = briefingQueryAdapter.findBriefings(params, version);
        return BriefingMapper.toBriefingPreviewListDTO(params.getDate(), briefingList);
    }

    @Transactional
    public BriefingResponseDTO.BriefingDetailDTO findBriefing(final Long id, Member member) {
        briefingCommandAdapter.increaseViewCountById(id);
        Boolean isScrap =
                Optional.ofNullable(member)
                        .map(m -> scrapQueryAdapter.existsByMemberIdAndBriefingId(m.getId(), id))
                        .orElseGet(() -> Boolean.FALSE);

        Boolean isBriefingOpen = false;
        Boolean isWarning = false;

        return BriefingMapper.toBriefingDetailDTO(
                briefingQueryAdapter.findBriefing(id, version), isScrap, isBriefingOpen, isWarning);
    }

    @Transactional
    public void createBriefing(final BriefingRequestDTO.BriefingCreate request) {
        Briefing createdBriefing =
                briefingCommandAdapter.create(BriefingMapper.toBriefing(request));

        final List<Article> articles =
                request.getArticles().stream()
                        .map(articleCreateDto -> {
                            Article article = BriefingMapper.toArticle(articleCreateDto);
                            article.setBriefing(createdBriefing);
                            return article;
                        }).toList();

        List<Article> createdArticles = articleCommandAdapter.createAll(articles);
        createdBriefing.setArticles(createdArticles);
    }

    @Transactional
    public BriefingResponseDTO.BriefingUpdateDTO updateBriefing(
            Long id, final BriefingRequestDTO.BriefingUpdateDTO request) {
        Briefing briefing = briefingQueryAdapter.findBriefing(id, APIVersion.V1);
        Briefing updatedBriefing =
                briefingCommandAdapter.update(
                        briefing, request.getTitle(), request.getSubTitle(), request.getContent());
        return BriefingMapper.toBriefingUpdateDTO(updatedBriefing);
    }
}
