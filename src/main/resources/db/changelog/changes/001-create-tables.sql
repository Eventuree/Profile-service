CREATE TABLE users
(
    id                BIGSERIAL PRIMARY KEY,
    photo_url         VARCHAR(255),
    gender            VARCHAR(50),
    age               SMALLINT,
    bio               TEXT,
    is_profile_active BOOLEAN   DEFAULT TRUE,
    location          VARCHAR(255),
    last_profile_edit TIMESTAMP,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE interest_categories
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(100)
);

CREATE TABLE user_interests
(
    user_id     BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_interests_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_user_interests_category FOREIGN KEY (category_id) REFERENCES interest_categories (id)
);

CREATE TABLE ratings
(
    id            BIGSERIAL PRIMARY KEY,
    rated_user_id BIGINT   NOT NULL,
    rater_user_id BIGINT   NOT NULL,
    event_id      BIGINT   NOT NULL,
    score         SMALLINT NOT NULL,
    comment       TEXT,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_ratings_rated_user FOREIGN KEY (rated_user_id) REFERENCES users (id),
    CONSTRAINT fk_ratings_rater_user FOREIGN KEY (rater_user_id) REFERENCES users (id)
);