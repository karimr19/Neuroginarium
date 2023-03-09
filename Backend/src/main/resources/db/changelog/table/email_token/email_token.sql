--liquibase formatted sql

--changeset karimr19:email_token_table runOnChange:true
CREATE TABLE IF NOT EXISTS email_token
(
    id    BIGINT NOT NULL PRIMARY KEY,
    email TEXT   NOT NULL,
    token TEXT   NOT NULL
);