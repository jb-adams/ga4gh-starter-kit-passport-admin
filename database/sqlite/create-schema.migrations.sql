CREATE TABLE passport_user (
    id TEXT PRIMARY KEY,
    username TEXT UNIQUE,
    first_name TEXT,
    last_name TEXT,
    email TEXT UNIQUE,
    password_salt TEXT UNIQUE,
    password_hash TEXT
);

CREATE TABLE passport_visa (
    id TEXT PRIMARY KEY,
    visa_name TEXT UNIQUE
);

CREATE TABLE passport_visa_assertion (
    id INTEGER PRIMARY KEY,
    user_id TEXT,
    visa_id TEXT,
    status TEXT,
    FOREIGN KEY (user_id) REFERENCES passport_user(id),
    FOREIGN KEY (visa_id) REFERENCES passport_visa(id)
);
