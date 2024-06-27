-- Drop tables if they exist
DROP TABLE IF EXISTS article_disagree;
DROP TABLE IF EXISTS article_comment;
DROP TABLE IF EXISTS article_agree;
DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS users;

-- Create table for users
CREATE TABLE users
(
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    created_at        TIMESTAMP(6),
    modified_at       TIMESTAMP(6),
    nickname          VARCHAR(20)  NOT NULL,
    profile_image_url VARCHAR(150),
    email             VARCHAR(255) NOT NULL UNIQUE,
    password          VARCHAR(255) NOT NULL,
    point             INTEGER      NOT NULL,
    role              ENUM('ADMIN', 'USER') NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Create table for articles
CREATE TABLE article
(
    id             BIGINT         NOT NULL AUTO_INCREMENT,
    created_at     TIMESTAMP(6),
    modified_at    TIMESTAMP(6),
    article_status ENUM('HONORED', 'PENDING') NOT NULL,
    content        VARCHAR(16000) NOT NULL,
    title          VARCHAR(255)   NOT NULL,
    views_count    BIGINT         NOT NULL,
    author_id      BIGINT         NOT NULL,
    agree_count    BIGINT         DEFAULT 0 NOT NULL,
    disagree_count BIGINT         DEFAULT 0 NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (author_id) REFERENCES users (id)
) ENGINE=InnoDB;

-- Create table for article agreements
CREATE TABLE article_agree
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    article_id BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY `unique_user_article` (`article_id`,`user_id`),
    FOREIGN KEY (article_id) REFERENCES article (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;

-- Create table for article comments
CREATE TABLE article_comment
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    created_at  TIMESTAMP(6),
    modified_at TIMESTAMP(6),
    content     VARCHAR(255),
    parent_path VARCHAR(255),
    article_id  BIGINT NOT NULL,
    author_id   BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (article_id) REFERENCES article (id),
    FOREIGN KEY (author_id) REFERENCES users (id)
) ENGINE=InnoDB;

-- Create table for article disagreements
CREATE TABLE article_disagree
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    article_id BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    UNIQUE KEY `unique_user_article` (`article_id`,`user_id`),
    PRIMARY KEY (id),
    FOREIGN KEY (article_id) REFERENCES article (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;
