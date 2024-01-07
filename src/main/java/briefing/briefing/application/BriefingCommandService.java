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

        // throw 부분을 컨트롤러에서 validator annotation을 사용하는 것이 더 좋을지??
        Briefing briefing = briefingRepository.findById(id).orElseThrow(() -> new BriefingException(ErrorCode.NOT_FOUND_BRIEFING));


        // 이 코드 더 깔끔하게 리팩토링이 가능할지??
        Optional.ofNullable(request.getContent()).ifPresent(briefing::setContent);
        Optional.ofNullable(request.getTitle()).ifPresent(briefing::setTitle);
        Optional.ofNullable(request.getSubTitle()).ifPresent(briefing::setSubtitle);

        return briefing;
    }
}
