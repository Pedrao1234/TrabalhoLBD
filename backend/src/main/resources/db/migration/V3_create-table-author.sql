-- v3__create_author_entity.sql
create table author (
    version bigint not null,
    id uuid not null,
    name varchar(255) not null,
    birth_date timestamp,
    sex char(1) not null,
    created_date timestamp,
    last_modified_date timestamp,
    deleted_date timestamp,
    primary key (id),

);