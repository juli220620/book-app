--liquibase formatted sql
--changeset juli220620:add-admin-creds

insert into "administration".user (username, password) values
    ('admin', '$2a$12$V65wMu5nRfil4Ia4dhGKBe49neFQMn0nYGHnKcgT698cioBh3ClNS');

insert into "administration".user_role (user_id, role_id) values (1, 'ADMIN');