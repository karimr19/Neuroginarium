--liquibase formatted sql

--changeset karimr19:email_token_sequence
CREATE SEQUENCE IF NOT EXISTS email_token_seq;