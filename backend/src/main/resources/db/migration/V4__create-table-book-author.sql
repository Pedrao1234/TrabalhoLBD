CREATE TABLE book_author (
  version BIGINT NOT NULL,
  id UUID NOT NULL,
  book_id UUID NOT NULL,
  author_id UUID NOT NULL,
  status varchar(255) NOT NULL,
  created_date TIMESTAMP,
  last_modified_date TIMESTAMP,
  deleted_date TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (book_id) REFERENCES book (id),
  FOREIGN KEY (author_id) REFERENCES author (id)
);