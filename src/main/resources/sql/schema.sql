CREATE TABLE IF NOT EXISTS  URLSTOREQUEST(
    id   VARCHAR(60) DEFAULT RANDOM_UUID() PRIMARY KEY,
    url VARCHAR NOT NULL
);