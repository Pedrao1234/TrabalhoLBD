create or replace function on_create_reader() 
returns TRIGGER as $$ 
begin 
    insert into log_register (name_table, id_entity, created_date) values ('reader', new.id, CURRENT_DATE);
	return new;
end 
$$ language plpgsql;

create trigger pre_create_reader
before insert on reader
for each row 
execute function on_create_reader();

create or replace function on_update_reader() 
returns trigger as $$
begin 
	update log_register
	set last_modified_date = CURRENT_DATE
	where id_entity = new.id;
	
	return new;
end
$$ language plpgsql;

create trigger pre_update_reader
before update on reader
for each row
execute function on_update_reader();

create or replace function on_delete_reader() 
returns trigger as $$
begin 
	update log_register
	set deleted_date = CURRENT_DATE
	where id_entity = old.id;
	return old;
end
$$ language plpgsql;

create trigger pre_delete_reader
before delete on reader
for each row
execute function on_delete_reader();