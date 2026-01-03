CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    experience BIGINT NOT NULL DEFAULT 0,
    strength INT NOT NULL DEFAULT 0,
    resistance INT NOT NULL DEFAULT 0,
    intelligence INT NOT NULL DEFAULT 0,
    discipline INT NOT NULL DEFAULT 0,
    faith INT NOT NULL DEFAULT 0,
    charisma INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    last_login_at TIMESTAMP
);