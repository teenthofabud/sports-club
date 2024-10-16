CREATE TABLE IF NOT EXISTS address(
    address_id BIGINT primary key auto_increment,
    street varchar(100) NOT NULL,
    city varchar(60) NOT NULL,
    state varchar(60) NOT NULL,
    zip varchar(10) NOT NULL,
    type varchar(14) NOT NULL
);