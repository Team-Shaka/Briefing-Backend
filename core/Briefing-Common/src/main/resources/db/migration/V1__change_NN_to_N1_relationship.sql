ALTER TABLE article ADD COLUMN briefing_id BIGINT;

-- 기존 briefing_article 데이터를 기반으로 article 테이블의 briefing_id 업데이트
UPDATE article a
JOIN briefing_article ba ON a.id = ba.article_id
SET a.briefing_id = ba.briefing_id;

-- briefing_article 테이블 제거
DROP TABLE briefing_article;