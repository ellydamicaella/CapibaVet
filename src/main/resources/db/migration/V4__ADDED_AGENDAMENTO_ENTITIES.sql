CREATE TABLE usuario (
    id CHAR(36) PRIMARY KEY,                        -- UUID como chave primária
    name VARCHAR(150) NOT NULL,                      -- Nome do usuário, obrigatório
    social_name VARCHAR(20),                        -- Nome social do usuário, opcional
    email VARCHAR(50) NOT NULL,                     -- Email do usuário, obrigatório
    password VARCHAR(50) NOT NULL,                  -- Senha do usuário, obrigatória
    phone_number VARCHAR(20) NOT NULL,               -- Número de telefone, obrigatório
    document VARCHAR(20) NOT NULL,  -- Documento (CPF ou CNPJ)
    documentType VARCHAR(3) NOT NULL,  -- Tipo do documento (CPF ou CNPJ)         -- Registro pessoal (por exemplo, CPF ou CNPJ), opcional
    dateOfBirth DATE NOT NULL,                     -- Data de nascimento, obrigatória
    created_at DATETIME,                             -- Data de criação
    updated_at DATETIME,                             -- Data de atualização
    moeda_capiba INT DEFAULT 0,                      -- Moeda 'Capiba', valor inicial 0
    profile_image LONGBLOB,                          -- Imagem de perfil (dados binários)
    UNIQUE (email)                                   -- Garantir que o email seja único
);

CREATE TABLE parceiro (
    id CHAR(36) PRIMARY KEY,  -- UUID como chave primária
    nome VARCHAR(150) NOT NULL,  -- Nome do parceiro
    email VARCHAR(50) NOT NULL,  -- Email do parceiro
    document VARCHAR(20) NOT NULL,  -- Documento (CPF ou CNPJ)
    documentType VARCHAR(3) NOT NULL,  -- Tipo do documento (CPF ou CNPJ)
    password VARCHAR(50) NOT NULL,  -- Senha do parceiro
    phone_number VARCHAR(20) NOT NULL,  -- Número de telefone do parceiro
    rua VARCHAR(50),  -- Endereço: rua
    numeroResidencial VARCHAR(10),  -- Endereço: número residencial
    cidade VARCHAR(100),  -- Cidade do parceiro
    CEP VARCHAR(20),  -- CEP do parceiro
    latitude DECIMAL(9, 6),  -- Latitude para localização
    longitude DECIMAL(9, 6),  -- Longitude para localização
    profileImage LONGBLOB,  -- Imagem de perfil do parceiro
    createdAt DATETIME,  -- Data de criação do registro
    updatedAt DATETIME  -- Data de atualização do registro

    -- Relacionamentos (caso sejam implementados em tabelas separadas no banco):
    -- (Tabelas de relacionamento "servico_prestado", "disponibilidade", "atendimento_marcado" devem ser criadas de acordo com a lógica do relacionamento)
);

CREATE TABLE servico (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    price DECIMAL(10, 2) NOT NULL,
    id_partner CHAR(36) NOT NULL,  -- Relacionamento com a tabela parceiro
    FOREIGN KEY (id_partner) REFERENCES parceiro(id)
);

CREATE TABLE disponibilidade (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Chave primária
    id_partner CHAR(36) NOT NULL,  -- Chave estrangeira para a tabela parceiro
    day_of_week ENUM('SUNDAY', 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY') NOT NULL,  -- Dia da semana
    start_time TIME NOT NULL,  -- Hora de início da disponibilidade
    end_time TIME NOT NULL,  -- Hora de término da disponibilidade

    -- Chave estrangeira para a tabela parceiro
    FOREIGN KEY (id_partner) REFERENCES parceiro(id)
);

CREATE TABLE animal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Chave primária
    name VARCHAR(255) NOT NULL,  -- Nome do animal
    porte ENUM('PEQUENO', 'MEDIO', 'GRANDE') NOT NULL,  -- Porte do animal (Enum)
    type ENUM('CACHORRO', 'GATO', 'OUTRO') NOT NULL,  -- Tipo de animal (Enum)
    sexo ENUM('M', 'F') NOT NULL,  -- Sexo do animal (Enum)
    id_user CHAR(36) NOT NULL,  -- Chave estrangeira para a tabela usuario
    FOREIGN KEY (id_user) REFERENCES usuario(id)  -- Relacionamento com a tabela usuario
);
--
CREATE TABLE atendimento_marcado (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Chave primária
    id_partner CHAR(36) NOT NULL,  -- Chave estrangeira para a tabela parceiro
    id_service BIGINT NOT NULL,  -- Chave estrangeira para a tabela serviço prestado
    id_user CHAR(36) NOT NULL,  -- Chave estrangeira para a tabela usuário
    id_animal BIGINT NOT NULL,  -- Chave estrangeira para a tabela animal
    appointment_date DATE NOT NULL,  -- Data do atendimento
    start_time TIME NOT NULL,  -- Hora de início do atendimento
    end_time TIME NOT NULL,  -- Hora de término do atendimento
    status ENUM('PENDENTE', 'CONFIRMADO', 'CANCELADO') NOT NULL,  -- Status do atendimento

    -- Chaves estrangeiras
    FOREIGN KEY (id_partner) REFERENCES parceiro(id),
    FOREIGN KEY (id_service) REFERENCES servico(id),
    FOREIGN KEY (id_user) REFERENCES usuario(id),
    FOREIGN KEY (id_animal) REFERENCES animal(id)
);

