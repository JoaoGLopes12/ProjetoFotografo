CREATE DATABASE fotosdb;
\c fotosdb

CREATE TABLE genero (
    id SERIAL PRIMARY KEY,
    descricao TEXT NOT NULL
);

CREATE TABLE local (
    id SERIAL PRIMARY KEY,
    nome TEXT NOT NULL
);

CREATE TABLE fotografo (
    id SERIAL PRIMARY KEY,
    nome TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    senha TEXT NOT NULL
);

CREATE TABLE foto (
    id SERIAL PRIMARY KEY,
    titulo TEXT NOT NULL,
    descricao TEXT,
    arquivo TEXT NOT NULL,
    genero_id INTEGER NOT NULL,
    local_id INTEGER NOT NULL,
    fotografo_id INTEGER NOT NULL,
    FOREIGN KEY (genero_id) REFERENCES genero(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (local_id) REFERENCES local(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (fotografo_id) REFERENCES fotografo(id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO genero (descricao) VALUES 
('Paisagem'), 
('Retrato'), 
('Urbana');

INSERT INTO local (nome) VALUES 
('Parque da Cidade'), 
('Centro Histórico'), 
('Estúdio A');

INSERT INTO fotografo (nome, email, senha) VALUES 
('João Gabriel', 'joao@example.com', '123'),
('Gabriel Ramos', 'gabriel@example.com', '123');

INSERT INTO foto (titulo, descricao, arquivo, genero_id, local_id, fotografo_id) VALUES 
('Amanhecer no Parque', 'Foto tirada no nascer do sol', 'parque.jpg', 1, 1, 1);
