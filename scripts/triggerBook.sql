create or replace function on_create_book() 
returns TRIGGER as $$ 
begin 
    insert into log_register (name_table, id_entity, created_date) values ('book', new.id, CURRENT_DATE);
	return new;
end 
$$ language plpgsql;

create trigger pre_create_book
before insert on book
for each row 
execute function on_create_book();

create or replace function on_update_book() 
returns trigger as $$
begin 
	update log_register
	set last_modified_date = CURRENT_DATE
	where id_entity = new.id;
	
	return new;
end
$$ language plpgsql;

create trigger pre_update_book
before update on book
for each row
execute function on_update_book();

create or replace function on_delete_book() 
returns trigger as $$
begin 
	update log_register
	set deleted_date = CURRENT_DATE
	where id_entity = old.id;
	return old;
end
$$ language plpgsql;

create trigger pre_delete_book
before delete on book
for each row
execute function on_delete_book();