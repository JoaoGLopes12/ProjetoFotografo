-- NÃO USE ESTA LINHA AQUI: \c fotosdb

CREATE TABLE fotografo (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(64) NOT NULL
);

CREATE TABLE genero (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL
);

CREATE TABLE local (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL
);

CREATE TABLE foto (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    descricao VARCHAR(500),
    arquivo VARCHAR(255) NOT NULL,
    fotografo_id INTEGER NOT NULL REFERENCES fotografo(id),
    genero_id INTEGER NOT NULL REFERENCES genero(id),
    local_id INTEGER NOT NULL REFERENCES local(id)
);
