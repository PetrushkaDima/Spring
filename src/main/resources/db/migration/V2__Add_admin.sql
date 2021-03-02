insert into user (id, username, password, active) values (1, 'admin', '$2a$08$nxV6Y9OhK9mxIpkZUADm9eY64dqSzVOp.mlC78lVVhk0t4YTo2JvC', true);

insert into user_role (user_id, roles) values (1, 'USER'), (1,'ADMIN');