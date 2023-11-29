package briefing.briefing.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBriefingArticle is a Querydsl query type for BriefingArticle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBriefingArticle extends EntityPathBase<BriefingArticle> {

    private static final long serialVersionUID = 37002276L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBriefingArticle briefingArticle = new QBriefingArticle("briefingArticle");

    public final briefing.base.QBaseDateTimeEntity _super = new briefing.base.QBaseDateTimeEntity(this);

    public final QArticle article;

    public final QBriefing briefing;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QBriefingArticle(String variable) {
        this(BriefingArticle.class, forVariable(variable), INITS);
    }

    public QBriefingArticle(Path<? extends BriefingArticle> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBriefingArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBriefingArticle(PathMetadata metadata, PathInits inits) {
        this(BriefingArticle.class, metadata, inits);
    }

    public QBriefingArticle(Class<? extends BriefingArticle> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new QArticle(forProperty("article")) : null;
        this.briefing = inits.isInitialized("briefing") ? new QBriefing(forProperty("briefing")) : null;
    }

}

