package com.example.briefingcommon.entity;

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

    private static final long serialVersionUID = 1037831131L;

    public static final QMember member = new QMember("member1");

    public final QBaseDateTimeEntity _super = new QBaseDateTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickName = createString("nickName");

    public final StringPath profileImgUrl = createString("profileImgUrl");

    public final EnumPath<com.example.briefingcommon.entity.enums.MemberRole> role = createEnum("role", com.example.briefingcommon.entity.enums.MemberRole.class);

    public final ListPath<Scrap, QScrap> scrapList = this.<Scrap, QScrap>createList("scrapList", Scrap.class, QScrap.class, PathInits.DIRECT2);

    public final StringPath socialId = createString("socialId");

    public final EnumPath<com.example.briefingcommon.entity.enums.SocialType> socialType = createEnum("socialType", com.example.briefingcommon.entity.enums.SocialType.class);

    public final EnumPath<com.example.briefingcommon.entity.enums.MemberStatus> status = createEnum("status", com.example.briefingcommon.entity.enums.MemberStatus.class);

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

