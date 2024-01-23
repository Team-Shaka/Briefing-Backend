package com.example.briefingcommon.domain.repository.article;

import com.example.briefingcommon.entity.BriefingArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



public interface BriefingArticleRepository extends JpaRepository<BriefingArticle, Long> {}
