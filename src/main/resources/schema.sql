create table if not exists TASK
(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    description varchar(255),
    is_completed boolean
);