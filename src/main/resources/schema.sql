create table if not exists TASK
(
    id SERIAL PRIMARY KEY NOT NULL,
    description varchar(255),
    is_completed boolean
);
create table if not exists user_info(
    id SERIAL PRIMARY KEY NOT NULL,
    username varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    roles varchar(255) NOT NULL
);