CREATE TABLE usuario (
     id CHAR(36) PRIMARY KEY,  -- UUID como chave primária
     name VARCHAR(255) NOT NULL,
     social_name VARCHAR(255),  -- Campo para nome social, opcional
     email VARCHAR(255) NOT NULL UNIQUE,  -- Usando o email como campo único
     password VARCHAR(255) NOT NULL,
     phone_number VARCHAR(20) NOT NULL,  -- Número de telefone
     document VARCHAR(20) NOT NULL,  -- Para o document
     type ENUM('CPF', 'CNPJ') NOT NULL,  -- Para o tipo do documento
     date_of_birth DATE,  -- Data de nascimento
     createdAt DATETIME,  -- Data de criação
     updatedAt DATETIME,  -- Data de atualização
     moeda_capiba INT DEFAULT 0  -- Moeda Capiba com valor inicial 0
);

CREATE TABLE parceiro (
  id CHAR(36) PRIMARY KEY,  -- UUID como chave primária
  nome VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  document VARCHAR(20) NOT NULL,  -- Para o document
  type ENUM('CPF', 'CNPJ') NOT NULL,  -- Para o tipo do documento
  password VARCHAR(255) NOT NULL,
  phone_number VARCHAR(20) NOT NULL,
  createdAt DATETIME,
  updatedAt DATETIME
);