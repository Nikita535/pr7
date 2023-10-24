CREATE TABLE IF NOT EXISTS task (
     id SERIAL PRIMARY KEY,
     title VARCHAR(255),
    description TEXT,
    status VARCHAR(50)
    );