--liquibase formatted sql
--changeset oschein:1

CREATE TABLE IF NOT EXISTS book (
    id bigserial primary key not null,
    name varchar(50) not null,
    author varchar(50) not null,
    gender varchar(10) not null,
    theme varchar(20),
    description varchar(200),
    book_case varchar(10) not null,
    library varchar(10) not null
    access_link varchar(100)
    
)