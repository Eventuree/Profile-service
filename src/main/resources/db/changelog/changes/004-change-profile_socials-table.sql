-- liquibase formatted sql

--changeset vbanasevych:change-profile-socials-table
ALTER TABLE profile_socials DROP COLUMN network_name CASCADE;