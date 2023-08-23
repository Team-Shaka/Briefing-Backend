drop table if exists briefing;
drop table if exists article;
drop table if exists briefing_article;
drop table if exists chatting;
drop table if exists message;

create table briefing
(
    id         bigserial primary key,
    type       varchar(255)  not null,
    rank       integer       not null,
    title      varchar(255)  not null,
    subtitle   varchar(255)  not null,
    content    varchar(1000) not null,
    created_at timestamp(6),
    updated_at timestamp(6)
);

create table article
(
    id         bigserial primary key,
    press      varchar(255),
    title      varchar(255) not null,
    url        varchar(1024),
    created_at timestamp(6),
    updated_at timestamp(6)
);

create table briefing_article
(
    id          bigserial primary key,
    briefing_id bigint not null,
    article_id  bigint not null,
    created_at  timestamp(6),
    updated_at  timestamp(6)
);

create table chatting
(
    id         bigserial primary key,
    title      varchar(255),
    created_at timestamp(6),
    updated_at timestamp(6)
);

create table message
(
    id          bigserial primary key,
    chatting_id bigint        not null,
    role        varchar(255),
    content     varchar(1024) not null,
    created_at  timestamp(6)
);

