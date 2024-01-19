package briefing.briefing.implement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import briefing.briefing.domain.BriefingArticle;
import briefing.briefing.domain.repository.BriefingArticleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BriefingArticleCommandService {
    private final BriefingArticleRepository briefingArticleRepository;

    public List<BriefingArticle> createAll(final List<BriefingArticle> briefingArticles) {
        return briefingArticleRepository.saveAll(briefingArticles);
    }
}
