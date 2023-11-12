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
  primary key(id),
  foreign key(reader_id) references reader (id)
);
