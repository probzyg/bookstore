--liquibase formatted sql

--changeset probzyg:1

create table book(
                     title varchar(255) PRIMARY KEY,
                     author_name varchar(255),
                     genre varchar(255)
)