create table verification_code
(
    id         bigserial
        primary key,
    code       varchar(6)   not null,
    created_at timestamp(6) not null,
    person_id  bigint       not null
        constraint person_id_unique
            unique
        constraint person_id_references
            references person
);

create index person_id_index
    on verification_code (person_id);