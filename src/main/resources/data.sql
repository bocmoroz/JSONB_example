DROP DATABASE IF EXISTS sk_example_db;
DROP ROLE IF EXISTS sk_example_user;

CREATE ROLE sk_example_user WITH
  LOGIN
  SUPERUSER
  INHERIT
  CREATEDB
  CREATEROLE
  REPLICATION
  ENCRYPTED PASSWORD 'SCRAM-SHA-256$4096:Gk+02LJ941+vIIEUwTjWEA==$gpzsYoyrLze30H4WOgiO+89Jpd0cnMjBX5BmVJsw7SI=:FfTvGvmLDKYCQbE89qYE1inFAbIwVC+fcFCp3rHy5kU=';

CREATE DATABASE sk_example_db
    WITH
    OWNER = sk_example_user
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

\c sk_example_db;
SET SESSION AUTHORIZATION sk_example_user;

CREATE TABLE sk_example_table (
    id SERIAL,
    obj JSONB NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO sk_example_table (obj) VALUES (
    '{"current":0}'::JSONB
);