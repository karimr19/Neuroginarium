--liquibase formatted sql

--changeset karimr19:moderator_queue_order_item_sequence
CREATE SEQUENCE IF NOT EXISTS moderator_queue_order_item_seq;