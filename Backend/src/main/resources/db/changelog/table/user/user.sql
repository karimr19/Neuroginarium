--liquibase formatted sql

--changeset karimr19:user_table runOnChange:true
CREATE TABLE IF NOT EXISTS user
(
    id         BIGINT NOT NULL PRIMARY KEY,
    first_name TEXT   NOT NULL,
    last_name  TEXT   NOT NULL,
    password   TEXT   NOT NULL,
    email      TEXT   NOT NULL,
    rating     BIGINT NOT NULL
);