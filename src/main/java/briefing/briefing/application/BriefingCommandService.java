package briefing.briefing.application;

import java.util.List;
import java.util.Optional;

import briefing.exception.ErrorCode;
import briefing.exception.handler.BriefingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import briefing.briefing.api.BriefingConverter;
import briefing.briefing.application.dto.BriefingRequestDTO;
import briefing.briefing.domain.Article;
import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.BriefingArticle;
import briefing.briefing.domain.repository.ArticleRepository;
import briefing.briefing.domain.repository.BriefingArticleRepository;
import briefing.briefing.domain.repository.BriefingRepository;
import lombok.RequiredArgsConstructor;

import javax.swing.text.html.Option;

@Service
@Transactional
@RequiredArgsConstructor
public class BriefingCommandService {

    private final BriefingRepository briefingRepository;
    private final ArticleRepository articleRepository;
    private final BriefingArticleRepository briefingArticleRepository;

    public void createBriefing(final BriefingRequestDTO.BriefingCreate request) {
        final Briefing briefing = BriefingConverter.toBriefing(request);
        final List<Article> articles =
                request.getArticles().stream().map(BriefingConverter::toArticle).toList();

        briefingRepository.save(briefing);
        articleRepository.saveAll(articles);

        final List<BriefingArticle> briefingArticles =
                articles.stream().map(article -> new BriefingArticle(briefing, article)).toList();
        briefingArticleRepository.saveAll(briefingArticles);
    }

    public Briefing updateBriefing(Long id, final BriefingRequestDTO.BriefingUpdateDTO request){

        Briefing briefing = briefingRepository.findById(id).orElseThrow(() -> new BriefingException(ErrorCode.NOT_FOUND_BRIEFING));

        briefing.updateBriefing(request.getTitle(),request.getSubTitle(),request.getContent());

        return briefing;
    }
}
