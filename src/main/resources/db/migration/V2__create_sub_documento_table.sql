CREATE TABLE IF NOT EXISTS tbsubdocumento (
	uuid UUID PRIMARY KEY,
    uuid_documento UUID NOT NULL,
    content BYTEA NOT NULL
);

ALTER TABLE tbsubdocumento ADD CONSTRAINT fk_tbdocumento FOREIGN KEY (uuid_documento) REFERENCES tbdocumento(uuid) ON DELETE CASCADE;
