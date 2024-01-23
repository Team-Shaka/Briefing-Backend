package com.example.briefingapi.briefing.implement.service;

import java.util.List;

import com.example.briefingcommon.domain.repository.article.ArticleRepository;
import com.example.briefingcommon.entity.Article;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleCommandService {
    private final ArticleRepository articleRepository;

    public List<Article> createAll(final List<Article> articles) {
        return articleRepository.saveAll(articles);
    }
}
