--liquibase formatted sql
--changeset juli220620:add-role-values

insert into "administration".role
values ('ADMIN'),
       ('USER');