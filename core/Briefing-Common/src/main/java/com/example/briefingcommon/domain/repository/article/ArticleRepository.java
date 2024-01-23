package com.example.briefingcommon.domain.repository.article;

import com.example.briefingcommon.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



public interface ArticleRepository extends JpaRepository<Article, Long> {}
