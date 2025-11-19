/* Tabela Usuario */
CREATE TABLE usuario (
                         id uuid NOT NULL PRIMARY KEY,
                         login varchar(20) NOT NULL,
                         senha varchar(300) NOT NULL,
                         email varchar(255) NOT NULL,
                         roles text[]
);

/* Tabela Autor */
CREATE TABLE autor (
                       id uuid NOT NULL PRIMARY KEY,
                       nome varchar(100) NOT NULL,
                       data_nascimento date NOT NULL,
                       nacionalidade varchar(50) NOT NULL,
                       data_cadastro timestamp,
                       data_atualizacao timestamp,
                       id_usuario uuid,


                       CONSTRAINT fk_autor_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

/* Tabela Livro */
CREATE TABLE livro (
                       id uuid NOT NULL PRIMARY KEY,
                       isbn varchar(20) NOT NULL,
                       titulo varchar(150) NOT NULL,
                       data_publicacao date NOT NULL,
                       genero varchar(30) NOT NULL,
                       preco numeric(18, 2),
                       data_cadastro timestamp,
                       data_atualizacao timestamp,
                       id_autor uuid,
                       id_usuario uuid,


                       CONSTRAINT uk_livro_isbn UNIQUE (isbn),
                       CONSTRAINT fk_livro_autor FOREIGN KEY (id_autor) REFERENCES autor(id),
                       CONSTRAINT fk_livro_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id),


                       CONSTRAINT chk_livro_genero CHECK (genero IN ('FICCAO', 'FANTASIA', 'MISTERIO', 'ROMANCE', 'BIOGRAFIA', 'CIENCIA'))
);