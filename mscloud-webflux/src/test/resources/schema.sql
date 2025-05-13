CREATE TABLE authors (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255)
);


CREATE TABLE book (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author_id BIGINT NOT NULL,
    publish_time DATETIME NOT NULL
);

