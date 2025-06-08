-- Drop all existing tables
DROP TABLE IF EXISTS cart_items CASCADE;
DROP TABLE IF EXISTS order_items CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS game_genres CASCADE;
DROP TABLE IF EXISTS games CASCADE;
DROP TABLE IF EXISTS genres CASCADE;
DROP TABLE IF EXISTS developers CASCADE;
DROP TABLE IF EXISTS publishers CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Create tables
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    registration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE developers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    country VARCHAR(100),
    founded_year INTEGER,
    description TEXT,
    website_url VARCHAR(255)
);

CREATE TABLE publishers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    country VARCHAR(100),
    description TEXT,
    founded_year INTEGER,
    website_url VARCHAR(255)
);

CREATE TABLE genres (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE games (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(2000),
    price DECIMAL(10,2) NOT NULL,
    image_url VARCHAR(255),
    developer_id BIGINT REFERENCES developers(id),
    publisher_id BIGINT REFERENCES publishers(id)
);

CREATE TABLE game_genres (
    game_id BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,
    PRIMARY KEY (game_id, genre_id),
    FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    status VARCHAR(20) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_items (
    id SERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id),
    game_id BIGINT NOT NULL REFERENCES games(id),
    quantity INTEGER NOT NULL,
    price_per_unit DECIMAL(10,2) NOT NULL,
    total_price DECIMAL(10,2) NOT NULL
);

CREATE TABLE cart_items (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    game_id BIGINT NOT NULL REFERENCES games(id),
    quantity INTEGER NOT NULL DEFAULT 1,
    price DECIMAL(10,2) NOT NULL,
    UNIQUE (user_id, game_id)
); 