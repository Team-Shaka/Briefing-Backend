package briefing.briefing.implement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import briefing.briefing.domain.Article;
import briefing.briefing.domain.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleCommandService {
    private final ArticleRepository articleRepository;

    public List<Article> createAll(final List<Article> articles) {
        return articleRepository.saveAll(articles);
    }
}
