package briefing.briefing.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import briefing.briefing.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {}
