CREATE TABLE IF NOT EXISTS users
(
    id                BIGSERIAL PRIMARY KEY,
    username          VARCHAR(255) NOT NULL UNIQUE,
    first_name        VARCHAR(255),
    last_name         VARCHAR(255),
    email             VARCHAR(255),
    photo_url         VARCHAR(255),
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
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS user_interests
(
    user_id     BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_interests_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_user_interests_category FOREIGN KEY (category_id) REFERENCES interest_categories (id)
);

CREATE TABLE IF NOT EXISTS ratings
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

-- test
-- ID=1: Іван
INSERT INTO users (username, first_name, last_name, email, photo_url, gender, age, bio, location)
VALUES ('ivanovivan', 'Іван', 'Іванов', 'ivanivanov@gmail.com', 'https://i.pravatar.cc/300?img=11', 'MALE', 30, 'Мандрівник, фотограф', 'Київ/Україна');

-- ID=2: Анна
INSERT INTO users (username, first_name, last_name, email, photo_url, gender, age, bio, location)
VALUES ('annas', 'Анна', 'Шевченко', 'anna@test.com', 'https://i.pravatar.cc/300?img=5', 'FEMALE', 24, 'Люблю каву і котів', 'Львів/Україна');

-- ID=3: Петро
INSERT INTO users (username, first_name, last_name, email, photo_url, gender, age, bio, location)
VALUES ('petro_dev', 'Петро', 'Бондаренко', 'petro@dev.com', 'https://i.pravatar.cc/300?img=3', 'MALE', 28, 'Java Developer', 'Одеса/Україна');