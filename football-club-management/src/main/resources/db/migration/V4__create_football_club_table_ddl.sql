CREATE TABLE IF NOT EXISTS club(
    club_id BIGINT primary key auto_increment,
    name varchar(60) NOT NULL,
    email varchar(100) NOT NULL,
    abbreviation varchar(5) NOT NULL,
    phone_number varchar(15) NOT NULL,
    logo varchar(50) NOT NULL,
    website varchar(50) NOT NULL,
    management_id BIGINT NOT NULL,
    level_id BIGINT NOT NULL,
    address_id BIGINT NOT NULL,

    FOREIGN KEY (address_id) REFERENCES address(address_id),
    FOREIGN KEY (management_id) REFERENCES management(management_id),
    FOREIGN KEY (level_id) REFERENCES level(level_id)
);