create table screen
(
    id         bigserial
        primary key,
    created_at timestamp(6) not null,
    delete_at  timestamp(6),
    file_name  varchar(64)  not null
        constraint unique_file_name
            unique,
    person_id  bigint
        constraint person_id_references
            references person,
    constraint delete_at__person_id__id_unique
        unique (delete_at, person_id, id)
);