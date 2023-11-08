-- v3__create_author_entity.sql
create table author (
    version bigint not null,
    id uuid not null,
    name varchar(255) unique not null,
    birth_date timestamp,
    sex char(1) not null,
    created_date timestamp,
    last_modified_date timestamp,
    deleted_date timestamp,
    primary key (id),

);
create or replace function on_update_author()
returns trigger as $$
begin
    new.last_modified_date := current_timestamp;
    return new;
end;
$ language plpgsql;

create or replace function on_create_author()
returns trigger as $$
begin
    new.created_date := current_timestamp;
    return new;
end;
$ language plpgsql;

create or replace function on_soft_delete_author()
returns trigger as $$
begin
    new.deleted_date := current_timestamp;
    return new;
end;
$ language plpgsql;

create trigger author_pre_update_trigger
before update on author
for each row
execute function on_update_author();

create trigger author_pre_insert_trigger
before insert on author
for each row
execute function on_create_author();

create trigger author_soft_delete_trigger
before delete on author
for each row
execute function on_soft_delete_author();
