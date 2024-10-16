CREATE TABLE IF NOT EXISTS stadium(
    stadium_id BIGINT primary key AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    type varchar(50) NOT NULL,
    address_id BIGINT NOT NULL,
    club_id BIGINT NOT NULL,

    FOREIGN KEY (address_id) REFERENCES address(address_id),
    FOREIGN KEY (club_id) REFERENCES club(club_id)
);