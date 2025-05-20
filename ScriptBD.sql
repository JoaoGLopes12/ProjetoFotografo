
--DATA BASE NAME: dbprimefaces

CREATE TABLE pessoa
(
  	id serial NOT NULL,
  	nome text NOT NULL,
  	datanascimento Date NOT NULL,
	idade integer NOT NULL,
  	email text NOT NULL, 	
  	senha text NOT NULL,  
	fumante boolean,
	cidadania text,
  	observacao text,
  constraint pk_tbl_pessoa primary key (id)
);

-- Tabela: Fotografo
CREATE TABLE fotografo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

-- Tabela: Genero
CREATE TABLE genero (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL
);

-- Tabela: Local
CREATE TABLE local (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

-- Tabela: Foto
CREATE TABLE foto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    arquivo VARCHAR(255) NOT NULL,
    fotografo_id INT NOT NULL,
    genero_id INT NOT NULL,
    local_id INT NOT NULL,
    FOREIGN KEY (fotografo_id) REFERENCES fotografo(id) ON DELETE CASCADE,
    FOREIGN KEY (genero_id) REFERENCES genero(id),
    FOREIGN KEY (local_id) REFERENCES local(id)
);

