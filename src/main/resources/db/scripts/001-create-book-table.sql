--liquibase formatted sql
--changeset juli220620:create-book-table

create table "books".book (
    id bigserial not null primary key,
    name varchar(256) not null,
    author varchar(256) not null,
    description varchar(256)
);