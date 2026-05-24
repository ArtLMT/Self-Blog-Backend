-- 1. Enable the extension to generate UUIDs
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 2. Users table
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role VARCHAR(255) NOT NULL,
    language_preference VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE
);

-- 3. Initial admin user
INSERT INTO users (username, password, email, role, language_preference, created_at) 
VALUES ('admin', '$2a$10$XFMfk.9I2W5fG.4f/7XmOeJk.5Z0lZ0lZ0lZ0lZ0lZ0lZ0lZ0lZ0l', 'admin@selfblog.com', 'ADMIN', 'EN', now() at time zone 'utc');

-- 4. Refresh Tokens table
CREATE TABLE refresh_tokens (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    token VARCHAR(255) NOT NULL UNIQUE,
    user_id UUID,
    expiry_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_refresh_tokens_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
