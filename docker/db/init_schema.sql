-- Drop tables if they exist
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

-- Users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT (now() at time zone 'utc')
);

-- Posts table
CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    title_en VARCHAR(255) NOT NULL,
    title_vi VARCHAR(255) NOT NULL,
    content_en TEXT NOT NULL,
    content_vi TEXT NOT NULL,
    published BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT (now() at time zone 'utc'),
    updated_at TIMESTAMP DEFAULT (now() at time zone 'utc')
);

-- Initial admin user (password: password123)
INSERT INTO users (username, password, email, role) 
VALUES ('admin', '$2a$10$XFMfk.9I2W5fG.4f/7XmOeJk.5Z0lZ0lZ0lZ0lZ0lZ0lZ0lZ0lZ0l', 'admin@selfblog.com', 'ADMIN');
