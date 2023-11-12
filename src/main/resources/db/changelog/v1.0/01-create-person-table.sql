create table person
(
    id         bigserial
        primary key,
    created_at timestamp(6) not null,
    email      varchar(255) not null
        constraint unique_email
            unique,
    password   varchar(255) not null,
    username   varchar(255) not null
        constraint unique_username
            unique,
    verified   boolean      not null
);