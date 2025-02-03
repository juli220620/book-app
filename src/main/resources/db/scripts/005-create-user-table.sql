--liquibase formatted sql
--changeset juli220620:create-user-table

create table "administration".user (
    id bigserial not null primary key,
    username varchar(256) not null unique,
    password varchar(256) not null
);