insert into user (id, username, password, active)
values (2,'q', '$2a$08$nef8mtWQO1bSNVwJPfUlDODq50nkjG84AEGUlArkNAWrZbEuMIU1m', true), (3,'w', '$2a$08$4wBXOPGphSAuJ4.V0NLy1.NeshecbyBQ.7CRT8L.BMjSNoIaVOsPS', true);

insert into user_role (user_id, roles) values (2, 'USER'), (3,'USER');

update hibernate_sequence set next_val= 4 where next_val=1