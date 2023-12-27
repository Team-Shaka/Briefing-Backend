package briefing.briefing.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import briefing.briefing.domain.BriefingArticle;

public interface BriefingArticleRepository extends JpaRepository<BriefingArticle, Long> {}
