CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(10) NOT NULL,
    content TEXT NOT NULL,
    password VARCHAR(255) NOT NULL,
    view_count INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE
);