CREATE TABLE IF NOT EXISTS user_sample (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    name varchar(255),
    email varchar(255),
    uuid UUID UNIQUE
);

CREATE TABLE IF NOT EXISTS user_sample_aud (
    id BIGINT NOT NULL,
    rev BIGINT NOT NULL REFERENCES revinfo(rev),
    revend BIGINT REFERENCES revinfo(rev),
    revtype SMALLINT,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    name varchar(255),
    name_mod boolean,
    email varchar(255),
    email_mod boolean,
    uuid UUID,
    uuid_mod boolean
);
