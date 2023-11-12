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