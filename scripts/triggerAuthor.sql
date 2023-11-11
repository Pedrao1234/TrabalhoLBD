create or replace function on_create_author() 
returns TRIGGER as $$ 
begin 
    insert into log_register (name_table, id_entity, created_date) values ('author', new.id, CURRENT_DATE);
	return new;
end 
$$ language plpgsql;

create trigger pre_create_author
before insert on author
for each row 
execute function on_create_author();

create or replace function on_update_author() 
returns trigger as $$
begin 
	update log_register
	set last_modified_date = CURRENT_DATE
	where id_entity = new.id;
	
	return new;
end
$$ language plpgsql;

create trigger pre_update_author
before update on author
for each row
execute function on_update_author();

create or replace function on_delete_author() 
returns trigger as $$
begin 
	update log_register
	set deleted_date = CURRENT_DATE
	where id_entity = old.id;
	return old;
end
$$ language plpgsql;

create trigger pre_delete_author
before delete on author
for each row
execute function on_delete_author();