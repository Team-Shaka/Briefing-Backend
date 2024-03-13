UPDATE article a
JOIN briefing_article ba ON a.id = ba.article_id
SET a.briefing_id = ba.briefing_id;