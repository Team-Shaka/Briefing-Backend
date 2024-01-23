package com.example.briefingapi.briefing.implement.service;

import java.util.List;

import com.example.briefingcommon.domain.repository.article.BriefingArticleRepository;
import com.example.briefingcommon.entity.BriefingArticle;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BriefingArticleCommandService {
    private final BriefingArticleRepository briefingArticleRepository;

    public List<BriefingArticle> createAll(final List<BriefingArticle> briefingArticles) {
        return briefingArticleRepository.saveAll(briefingArticles);
    }
}
