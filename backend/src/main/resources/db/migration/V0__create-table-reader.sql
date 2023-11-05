create table reader (
  id uuid primary key,
  version bigint not null,
  name varchar(255) not null,
  date_birth timestamp,
  cpf varchar(255) not null,
  created_date timestamp,
  last_modified_date timestamp,
  deleted_date timestamp
);


alter table reader
  add constraint reader_cpf_unique unique (cpf);

create or replace function update_created_date()
  returns trigger as $$
begin
  new.created_date := current_timestamp;
  return new;
end;
$ language plpgsql;

create trigger reader_created_date_trigger
  before insert on reader
  for each row
  execute function update_created_date();

create or replace function update_last_modified_date()
  returns trigger as $$
begin
  new.last_modified_date := current_timestamp;
  return new;
end;
$ language plpgsql;

create trigger reader_last_modified_date_trigger
  before update on reader
  for each row
  execute function update_last_modified_date();

create or replace function soft_delete_reader()
  returns trigger as $$
begin
  new.deleted_date := current_timestamp;
  return new;
end;
$ language plpgsql;

create trigger reader_soft_delete_trigger
  before delete on reader
  for each row
  execute function soft_delete_reader();
