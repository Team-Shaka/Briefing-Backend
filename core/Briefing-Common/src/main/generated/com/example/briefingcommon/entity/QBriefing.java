package com.example.briefingcommon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBriefing is a Querydsl query type for Briefing
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBriefing extends EntityPathBase<Briefing> {

    private static final long serialVersionUID = 1768151593L;

    public static final QBriefing briefing = new QBriefing("briefing");

    public final QBaseDateTimeEntity _super = new QBaseDateTimeEntity(this);

    public final ListPath<Article, QArticle> articles = this.<Article, QArticle>createList("articles", Article.class, QArticle.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<com.example.briefingcommon.entity.enums.GptModel> gptModel = createEnum("gptModel", com.example.briefingcommon.entity.enums.GptModel.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> ranks = createNumber("ranks", Integer.class);

    public final StringPath subtitle = createString("subtitle");

    public final EnumPath<com.example.briefingcommon.entity.enums.TimeOfDay> timeOfDay = createEnum("timeOfDay", com.example.briefingcommon.entity.enums.TimeOfDay.class);

    public final StringPath title = createString("title");

    public final EnumPath<com.example.briefingcommon.entity.enums.BriefingType> type = createEnum("type", com.example.briefingcommon.entity.enums.BriefingType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QBriefing(String variable) {
        super(Briefing.class, forVariable(variable));
    }

    public QBriefing(Path<? extends Briefing> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBriefing(PathMetadata metadata) {
        super(Briefing.class, metadata);
    }

}

