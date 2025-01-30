--liquibase formatted sql
--changeset juli220620:create-role-table

create table "administration".role (
    id varchar(256) not null primary key
)