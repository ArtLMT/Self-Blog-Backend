-- 1. Enable the extension to generate UUIDs
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Drop tables in reverse order of foreign keys
DROP TABLE IF EXISTS users;

-- Users table
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT (now() at time zone 'utc')
);

-- Initial admin user (UUID is generated automatically)
INSERT INTO users (username, password, email, role) 
VALUES ('admin', '$2a$10$XFMfk.9I2W5fG.4f/7XmOeJk.5Z0lZ0lZ0lZ0lZ0lZ0lZ0lZ0lZ0l', 'admin@selfblog.com', 'ADMIN');