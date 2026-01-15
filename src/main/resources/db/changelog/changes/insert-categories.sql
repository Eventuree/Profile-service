-- liquibase formatted sql

--changeset vbanasevych:insert-default-interests
INSERT INTO interest_categories (name, slug)
VALUES ('Спорт та активний відпочинок', 'sport_active'),
       ('Ігри та хобі', 'games_hobbies'),
       ('Соціальні та міські заходи', 'social_city'),
       ('Волонтерство', 'volunteering'),
       ('Подорожі та поїздки', 'travel'),
       ('Освіта та розвиток', 'education') ON CONFLICT (name) DO NOTHING;