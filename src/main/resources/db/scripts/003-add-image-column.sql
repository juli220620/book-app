--liquibase formatted sql
--changeset juli220620:add-image-column

alter table "books".book add column image_id varchar;
