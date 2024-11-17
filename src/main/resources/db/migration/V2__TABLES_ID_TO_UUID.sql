CREATE TABLE usuario (
    id CHAR(36) PRIMARY KEY,  -- UUID como chave primária
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    createdAt DATETIME,
    updatedAt DATETIME,
    moedaCapiba INT DEFAULT 0
);

CREATE TABLE parceiro (
    id CHAR(36) PRIMARY KEY,  -- UUID como chave primária
    nome_fantasia VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    document VARCHAR(20) NOT NULL,  -- Para o document
    type ENUM('CPF', 'CNPJ') NOT NULL,  -- Para o tipo do documento
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    createdAt DATETIME,
    updatedAt DATETIME
);