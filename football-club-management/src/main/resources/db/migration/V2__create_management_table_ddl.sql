CREATE TABLE IF NOT EXISTS management(
    management_id BIGINT primary key auto_increment,
    name varchar(50) NOT NULL,
    contact varchar(50) NOT NULL
);