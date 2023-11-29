package briefing.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 2051544022L;

    public static final QMember member = new QMember("member1");

    public final briefing.base.QBaseDateTimeEntity _super = new briefing.base.QBaseDateTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickName = createString("nickName");

    public final StringPath profileImgUrl = createString("profileImgUrl");

    public final EnumPath<MemberRole> role = createEnum("role", MemberRole.class);

    public final ListPath<briefing.scrap.domain.Scrap, briefing.scrap.domain.QScrap> scrapList = this.<briefing.scrap.domain.Scrap, briefing.scrap.domain.QScrap>createList("scrapList", briefing.scrap.domain.Scrap.class, briefing.scrap.domain.QScrap.class, PathInits.DIRECT2);

    public final StringPath socialId = createString("socialId");

    public final EnumPath<SocialType> socialType = createEnum("socialType", SocialType.class);

    public final EnumPath<MemberStatus> status = createEnum("status", MemberStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

