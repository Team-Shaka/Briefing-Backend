package briefing.briefing.domain;

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

    private static final long serialVersionUID = 63849586L;

    public static final QBriefing briefing = new QBriefing("briefing");

    public final briefing.base.QBaseDateTimeEntity _super = new briefing.base.QBaseDateTimeEntity(this);

    public final ListPath<BriefingArticle, QBriefingArticle> briefingArticles = this.<BriefingArticle, QBriefingArticle>createList("briefingArticles", BriefingArticle.class, QBriefingArticle.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<briefing.chatting.domain.GptModel> gptModel = createEnum("gptModel", briefing.chatting.domain.GptModel.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> ranks = createNumber("ranks", Integer.class);

    public final StringPath subtitle = createString("subtitle");

    public final EnumPath<TimeOfDay> timeOfDay = createEnum("timeOfDay", TimeOfDay.class);

    public final StringPath title = createString("title");

    public final EnumPath<BriefingType> type = createEnum("type", BriefingType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

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

