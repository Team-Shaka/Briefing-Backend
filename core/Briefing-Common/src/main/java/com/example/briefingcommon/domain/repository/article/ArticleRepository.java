package com.example.briefingcommon.domain.repository.article;

import com.example.briefingcommon.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ArticleRepository extends JpaRepository<Article, Long> {}
