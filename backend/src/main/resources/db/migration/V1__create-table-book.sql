-- v1__create_book_entity.sql
create table book (
    version bigint not null,
    id uuid not null,
    title varchar(255) not null,
    release_date timestamp,
    publisher varchar(255) not null,
    summary varchar(255) not null,
    created_date timestamp,
    last_modified_date timestamp,
    deleted_date timestamp,
    constraint pk_book primary key (id)
);
create or replace function on_update_book()
returns trigger as $$
begin
    new.last_modified_date := current_timestamp;
    return new;
end;
$ language plpgsql;
create or replace function on_create_book()
returns trigger as $$
begin
    new.created_date := current_timestamp;
    return new;
end;
$ language plpgsql;
create or replace function on_soft_delete_book()
returns trigger as $$
begin
    new.deleted_date := current_timestamp;
    return new;
end;
$ language plpgsql;

create trigger book_pre_update_trigger
before update on book
for each row
execute function on_update_book();

create trigger book_pre_insert_trigger
before insert on book
for each row
execute function on_create_book();

create trigger book_soft_delete_trigger
before delete on book
for each row
execute function on_soft_delete_book();

alter table book
add constraint fk_book_rent
foreign key (id) references rent (book_id);