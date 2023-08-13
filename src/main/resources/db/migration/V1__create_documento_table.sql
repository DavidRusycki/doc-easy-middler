CREATE TABLE IF NOT EXISTS tbdocumento (
	uuid UUID PRIMARY KEY,
    hash VARCHAR(64) NOT NULL,
    status SMALLINT NOT NULL,
    content BYTEA
);
