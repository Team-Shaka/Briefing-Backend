package briefing.base;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseDateTimeEntity is a Querydsl query type for BaseDateTimeEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseDateTimeEntity extends EntityPathBase<BaseDateTimeEntity> {

    private static final long serialVersionUID = 1261212710L;

    public static final QBaseDateTimeEntity baseDateTimeEntity = new QBaseDateTimeEntity("baseDateTimeEntity");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QBaseDateTimeEntity(String variable) {
        super(BaseDateTimeEntity.class, forVariable(variable));
    }

    public QBaseDateTimeEntity(Path<? extends BaseDateTimeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseDateTimeEntity(PathMetadata metadata) {
        super(BaseDateTimeEntity.class, metadata);
    }

}

