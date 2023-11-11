create table log_register( 
	name_table varchar(255) not NULL,
	id_entity uuid primary key,
	created_date TIMESTAMP,
	last_modified_date TIMESTAMP,
	deleted_date TIMESTAMP
);