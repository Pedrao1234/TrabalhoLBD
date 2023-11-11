-- v1__create_book_entity.sql
create table book (
    version bigint not null,
    id uuid not null,
    rent_id uuid,
    title varchar(255) not null,
    release_date timestamp,
    publisher varchar(255) not null,
    summary varchar(255) not null,
    status varchar(255) NOT NULL,
    created_date timestamp,
    last_modified_date timestamp,
    deleted_date timestamp,
    primary key (id),
    foreign key (rent_id) references rent (id)

);
