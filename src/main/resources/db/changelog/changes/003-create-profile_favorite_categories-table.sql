-- liquibase formatted sql

--changeset vbanasevych:create-profile_favorite_categories-table
DROP TABLE IF EXISTS interest_categories CASCADE;
DROP TABLE IF EXISTS user_interests CASCADE;

CREATE TABLE IF NOT EXISTS profile_favorite_categories (
    profile_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    FOREIGN KEY (profile_id) REFERENCES user_profiles(id)
);