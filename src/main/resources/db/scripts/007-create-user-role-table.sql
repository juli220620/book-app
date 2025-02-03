--liquibase formatted sql
--changeset juli220620:create-user-role-table

create table "administration".user_role (
    id bigserial not null primary key,
    user_id bigint not null references "administration".user (id),
    role_id varchar(256) not null references "administration".role (id)
);