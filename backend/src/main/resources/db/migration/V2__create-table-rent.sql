CREATE TABLE rent (
  version BIGINT NOT NULL,
  id UUID NOT NULL,
  reader_id UUID,
  rent_date TIMESTAMP NOT NULL,
  devolution_date TIMESTAMP,
  status VARCHAR(255) NOT NULL,
  created_date TIMESTAMP,
  last_modified_date TIMESTAMP,
  deleted_date TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (reader_id) REFERENCES reader (id)
);

create or replace function on_update_rent()
returns trigger as $$
begin
    new.last_modified_date := current_timestamp;
    return new;
end;
$ language plpgsql;
create or replace function on_create_rent()
returns trigger as $$
begin
    new.created_date := current_timestamp;
    return new;
end;
$ language plpgsql;
create or replace function on_soft_delete_rent()
returns trigger as $$
begin
    new.deleted_date := current_timestamp;
    return new;
end;
$ language plpgsql;

create trigger rent_pre_update_trigger
before update on rent
for each row
execute function on_update_rent();

create trigger rent_pre_insert_trigger
before insert on rent
for each row
execute function on_create_rent();

create trigger rent_soft_delete_trigger
before delete on rent
for each row
execute function on_soft_delete_rent();