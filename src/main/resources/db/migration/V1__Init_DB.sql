create table hibernate_sequence (next_val bigint) engine=InnoDB;

insert into hibernate_sequence values ( 1 );

create table message (
    id bigint not null,
    image varchar(255),
    tag varchar(255),
    text varchar(255),
    tittle varchar(255),
    user_id bigint,
    primary key (id)
) engine=InnoDB;

create table user (
    id bigint not null,
    activation_code varchar(255),
    active bit not null,
    email varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id)
) engine=InnoDB;

create table user_role (
    user_id bigint not null,
    roles varchar(255)
) engine=InnoDB;

alter table message add constraint message_user_fk foreign key (user_id) references user (id);

alter table user_role add constraint user_role_user_fk foreign key (user_id) references user (id);