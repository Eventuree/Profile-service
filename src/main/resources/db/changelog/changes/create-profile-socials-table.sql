-- liquibase formatted sql

--changeset vbanasevych:create-profile-socials-table
CREATE TABLE IF NOT EXISTS profile_socials (
    profile_id BIGINT NOT NULL,
    network_name VARCHAR(50) NOT NULL,
    url VARCHAR(500) NOT NULL,
    PRIMARY KEY (profile_id, network_name),
    CONSTRAINT fk_socials_profile FOREIGN KEY (profile_id) REFERENCES user_profiles(id) ON DELETE CASCADE
    );