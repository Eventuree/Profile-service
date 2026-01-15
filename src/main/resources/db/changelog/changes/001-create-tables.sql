-- liquibase formatted sql

--changeset vbanasevych:create-tables-v2
CREATE TABLE IF NOT EXISTS user_profiles
(
    id                BIGSERIAL PRIMARY KEY,
    user_id           BIGINT NOT NULL UNIQUE,
    first_name        VARCHAR(255),
    last_name         VARCHAR(255),
    email             VARCHAR(255),
    photo_url         VARCHAR(500),
    gender            VARCHAR(50),
    age               SMALLINT,
    bio               TEXT,
    is_profile_active BOOLEAN   DEFAULT TRUE,
    location          VARCHAR(255),
    last_profile_edit TIMESTAMP,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS interest_categories
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    slug VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS user_interests
(
    profile_id  BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (profile_id, category_id),
    CONSTRAINT fk_ui_profile FOREIGN KEY (profile_id) REFERENCES user_profiles (id) ON DELETE CASCADE,
    CONSTRAINT fk_ui_category FOREIGN KEY (category_id) REFERENCES interest_categories (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ratings
(
    id               BIGSERIAL PRIMARY KEY,
    rated_profile_id BIGINT   NOT NULL,
    rater_profile_id BIGINT   NOT NULL,
    event_id         BIGINT   NOT NULL,
    score            SMALLINT NOT NULL CHECK (score >= 1 AND score <= 5),
    comment          TEXT,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_ratings_rated FOREIGN KEY (rated_profile_id) REFERENCES user_profiles (id),
    CONSTRAINT fk_ratings_rater FOREIGN KEY (rater_profile_id) REFERENCES user_profiles (id)
);