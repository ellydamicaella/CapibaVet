## Contribuições

Para contribuir com o projeto, por favor, envie um pull request ou abra uma issue no repositório. Agradecemos seu interesse em ajudar a melhorar a vida dos animais!

## Tecnologias
 
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Mysql](https://www.mysql.com/)
- [Redis](https://redis.io/)
- [Docker](https://www.docker.com/)

## Práticas adotadas

- SOLID
- Testes automatizados
- Consultas com filtros dinâmicos usando o Query By Example
- API reativa na web e na camada de banco
- Uso de DTOs para a API
- Injeção de Dependências
- Auditoria sobre criação e atualização da entidade

## Como Executar

### Localmente

- Clonar repositório git por https
```
git clone https://github.com/MeuPet-Start/Back_MeuPet.git
```
- Clonar repositório git por ssh
```
git clone git@github.com:MeuPet-Start/Back_MeuPet.git
```
- Construir o projeto:
```
./mvnw clean package
```
- Executar:
```
java -jar target/meupet-0.0.1-SNAPSHOT.jar
```

- OBS: será necessário que o mysql esteja rodando na sua máquina local
- Então altere em application.properties as seguintes configuracoes
```
spring.datasource.url=(url do mysql ex:jdbc:mysql://localhost:3306/meupet)
spring.datasource.username=(seu username do mysql)
spring.datasource.password=(sua senha do mysql)
```

A API poderá ser acessada em [localhost:8080/api/v1](http://localhost:8080/api/v1).
O Swagger poderá ser visualizado em [localhost:8080/api/v1/doc](http://localhost:8080/api/v1/doc)

### Usando Docker

- Clonar repositório git por https
```
git clone https://github.com/MeuPet-Start/Back_MeuPet.git
```
- Clonar repositório git por ssh
```
git clone git@github.com:MeuPet-Start/Back_MeuPet.git
```
- Construir o projeto:
```
mvn clean package
```
- Construir a imagem e executar o container:
```
docker-compose up
```
- Após isso basta rodar o projeto na sua IDE ou buildar com:
````
mvn package
````
- E executar o arquivo jar
````
java -jar target/meupet-0.0.1-SNAPSHOT.jar
````
A API poderá ser acessada em [localhost:8080](http://localhost:8080/api/v1).
O Swagger poderá ser visualizado em [localhost:8080/api/v1/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Métodos
Requisições para a API devem seguir os padrões:
| Método | Descrição |
|---|---|
| `GET` | Retorna informações de um ou mais registros.|
| `POST` | Utilizado para criar um novo registro. |
| `PUT` | Atualiza dados de um registro ou altera sua situação. |
| `DELETE` | Remove um registro do sistema. |


## Respostas
| Código | Descrição |
|---|---|
| `200` | Requisição executada com sucesso (success).|
| `400` | Erros de validação ou os campos informados não existem no sistema.|
| `401` | Dados de acesso inválidos.|
| `404` | Registro pesquisado não encontrado (Not found).|
| `405` | Método não implementado.|
| `410` | Registro pesquisado foi apagado do sistema e não esta mais disponível.|
| `422` | Dados informados estão fora do escopo definido para o campo.|
| `429` | Número máximo de requisições atingido. (*aguarde alguns segundos e tente novamente*)|

[//]: # (## Limites &#40;Throttling&#41;)

[//]: # (Existe o limite de `#` requisições por minuto por aplicação+usuário.)

[//]: # ()
[//]: # (Você pode acompanhar esses limites nos `headers`: `X-RateLimit-Limit`, `X-RateLimit-Remaining` enviados em todas as respostas da API.)

[//]: # ()
[//]: # (Ações de `listar` exibem `#` registros por página. Não é possível alterar este número.)

[//]: # ()
[//]: # (Por questões de segurança, todas as requisições serão feitas através do protocolo `HTTPS`.)

# API Endpoints

## Base URL

````
http://localhost:8080/api/v1/
````

## User
Endpoints disponíveis no **User Controller**, responsável por gerenciar operações CRUD relacionadas à entidade `User`.

## Métodos Get:

### 1. Listar usuarios

- **Descrição**: Lista todos os usuarios com paginação.
- **Método**: `GET`
- **Endpoint**: `/user`
- **Headers**:
  - `Content-Type: application/json`

Exemplo de Resposta:

    Código 200 (OK):
```` 
[
  {
    "id": null,
    "name": "John Doe",
    "email": "jonhdoe@mail.com",
    "phoneNumber": "(11) 11 11111-1111",
    "document": "11111111111",
    "documentType": "CPF",
    "birthDate": "2005-12-22"
  },
  {
    ...
  },
   ...
]
````
### 2. Recupera usuario por ID

- **Descrição**: Recupera um usuario por ID.
- **Método**: `GET`
- **Endpoint**: `/user/{id}`
- **Headers**:
  - `Content-Type: application/json`

Exemplo de Resposta:

    Código 200 (OK):
````json
{
  "id": null,
  "name": "John Doe",
  "email": "jonhdoe@mail.com",
  "phoneNumber": "(11) 11111-1111",
  "document": "11111111111",
  "documentType": "CPF",
  "birthDate": "2005-12-22"
}
````
### 3. Recupera imagem do perfil do usuario

- **Descrição**: Recupera imagem do perfil do usuario no banco de dados.
- **Método**: `GET`
- **Endpoint**: `/user/image`
- **Headers**:
  - `Content-Type: multipart/form-data`
- **Parâmetros da URL**: `id` (UUID): O identificador único do usuário, que será associado à imagem.

Exemplo de Resposta:

    Código 200 (OK):

    O arquivo da imagem

## Métodos Post

### 1. Criar Usuário

- **Descrição**: Cria um novo usuário no sistema.
- **Método**: `POST`
- **Endpoint**: `/user`
- **Headers**:
  - `Content-Type: application/json`

**Corpo da Requisição**:
```json
{
  "name": "John Doe",
  "socialName": "",
  "password": "12312323",
  "email": "jonhdoe@mail.com",
  "phoneNumber": "(11) 11 11111-1111",
  "document": "11111111111",
  "documentType": "CPF",
  "birthDate": "2005-12-22"
}
````

Exemplo de Resposta:

    Código 201 (Created):
````json
{
  "status": "success",
  "message": "Email enviado ao usuario com sucesso, aguardando confirmação de conta"
}
````

Após essa requisição será enviada um email para o usuário confirmar a conta.

### 2. Upload da imagem para o usuário

- **Descrição**: Insere a imagem referente ao usuario no banco de dados.
- **Método**: `POST`
- **Endpoint**: `/user/upload-image/{id}`
- **Headers**:
  - `Content-Type: multipart/form-data`

**Corpo da Requisição**:
  ```
  Deve conter a imagem enviada como parte de um formulário 
  no formato multipart/form-data.
````
**Exemplo da Requisição**:
````
POST /user/upload-image/123e4567-e89b-12d3-a456-426614174000 HTTP/1.1
  Host: api.example.com
  Content-Type: multipart/form-data; boundary=---boundary123

-----boundary123
Content-Disposition: form-data; name="file"; filename="profile.jpg"
Content-Type: image/jpeg

[ARQUIVO BINÁRIO DA IMAGEM]
-----boundary123--
````

Exemplo de Resposta:

    Código 201 (Created):

    {
      "status": "success"
      "message": "Imagem enviada com sucesso"
    }


## Métodos Put

### 1. Atualiza Usuário

- **Descrição**: Atualiza um usuário no sistema.
- **Método**: `PUT`
- **Endpoint**: `/user/{id}`
- **Headers**:
  - `Content-Type: application/json`

**Corpo da Requisição**:
````json
{
  "name": "John Doe",
  "socialName": "",
  "password": "12312323",
  "email": "jonhdoe@mail.com",
  "phoneNumber": "(11) 11 11111-1111",
  "document": "11111111111",
  "documentType": "CPF",
  "birthDate": "2005-12-22"
}
````
Exemplo de Resposta:

    Código 204 (No Content)

## Métodos Delete

### 1. Deleta Usuário

- **Descrição**: Deleta um usuário no sistema.
- **Método**: `DELETE`
- **Endpoint**: `/user`
- **Parâmetros da URL**: `id` (UUID): O identificador único do usuário.
- **Headers**:
  - `Content-Type: application/json`

Exemplo de Resposta:

    Código 204 (No Content)

## Partner

Endpoints disponíveis no **Partner Controller**, responsável por gerenciar operações CRUD relacionadas à entidade `Partner`.

## Métodos Get:

### 1. Listar parceiros

- **Descrição**: Lista todos os parceiros com paginação.
- **Método**: `GET`
- **Endpoint**: `/partner`
- **Headers**:
  - `Content-Type: application/json`

Exemplo de Resposta:

    Código 200 (OK):
```` 
[
  {
    "id": null,
    "name": "Clinica Veterinária MeuVet",
    "email": "meuvet@mail.com",
    "phoneNumber": "(11) 11111-1111",
    "document": "11111111111111",
    "documentType": "CPNJ"
  },
  {
    ...
  },
   ...
]
 ````
### 2. Recupera parceiro por ID

- **Descrição**: Recupera parceiro por id.
- **Método**: `GET`
- **Endpoint**: `/partner/{id}`
- **Headers**:
  - `Content-Type: application/json`

Exemplo de Resposta:

    Código 200 (OK):

````json
{
  "id": null,
  "name": "Clinica Veterinária MeuVet",
  "email": "meuvet@mail.com",
  "phoneNumber": "(11) 11111-1111",
  "document": "11111111111",
  "documentType": "CPF"
}
````
### 3. Recupera imagem do perfil do parceiro

- **Descrição**: Recupera imagem do perfil do parceiro no banco de dados.
- **Método**: `GET`
- **Endpoint**: `/partner/image`
- **Headers**:
  - `Content-Type: multipart/form-data`
- **Parâmetros da URL**: `id` (UUID): O identificador único do parceiro, que será associado à imagem.

Exemplo de Resposta:

    Código 200 (OK):

    O arquivo da imagem

## Métodos Post

### 1. Criar parceiro

- **Descrição**: Cria um novo parceiro no sistema.
- **Método**: `POST`
- **Endpoint**: `/partner`
- **Headers**:
  - `Content-Type: application/json`

**Corpo da Requisição**:
```json
{
  "name": "Clinica Veterinária MeuVet",
  "password": "12312323",
  "email": "jonhdoe@mail.com",
  "phoneNumber": "(11) 11111-1111",
  "document": "11111111111",
  "documentType": "CNPJs"
}
````

Exemplo de Resposta:

    Código 201 (Created):
````json
{
  "status": "success",
  "message": "Email enviado ao parceiro com sucesso, aguardando confirmação de conta"
}
````

Após essa requisição será enviada um email para o parcerio confirmar a conta.

### 2. Upload da imagem para o usuário

- **Descrição**: Insere a imagem referente ao usuario no banco de dados.
- **Método**: `POST`
- **Endpoint**: `/user/upload-image/{id}`
- **Headers**:
  - `Content-Type: multipart/form-data`

**Corpo da Requisição**:
```
Deve conter a imagem enviada como parte de um formulário 
no formato multipart/form-data.
````
- **Exemplo da Requisição**:

````
POST /user/upload-image/123e4567-e89b-12d3-a456-426614174000 HTTP/1.1
  Host: api.example.com
  Content-Type: multipart/form-data; boundary=---boundary123

-----boundary123
Content-Disposition: form-data; name="file"; filename="profile.jpg"
Content-Type: image/jpeg

[ARQUIVO BINÁRIO DA IMAGEM]
-----boundary123--
````

Exemplo de Resposta:

````
Código 201 (Created):
{
  "status": "success"
  "message": "Imagem enviada com sucesso"
}
````

## Métodos Put

### 1. Atualiza parceiro

- **Descrição**: Atualiza um parceiro no sistema.
- **Método**: `POST`
- **Endpoint**: `/partner/{id}`
- **Headers**:
  - `Content-Type: application/json`

**Corpo da Requisição**:
````json
{
  "name": "Clinica Veterinária MeuVet",
  "password": "12312323",
  "email": "meuvet@mail.com",
  "phoneNumber": "(11) 11111-1111",
  "document": "11111111111111",
  "documentType": "CNPJ"
}
````
Exemplo de Resposta:

    Código 204 (No Content)

## Métodos Delete

### 1. Deleta parceiro

- **Descrição**: Deleta um parceiro do sistema.
- **Método**: `DELETE`
- **Endpoint**: `/partner`
- **Parâmetros da URL**: `id` (UUID): O identificador único do parceiro.
- **Headers**:
  - `Content-Type: application/json`

**Corpo da Requisição**:
````json
{
  "name": "Clinica Veterinária MeuVet",
  "password": "12312323",
  "email": "meuvet@mail.com",
  "phoneNumber": "(11) 11111-1111",
  "document": "11111111111111",
  "documentType": "CNPJ"
}
````
Exemplo de Resposta:

    Código 204 (No Content)

## Authenticable

Endpoints disponíveis no **Authenticable Controller**, responsável por gerenciar operações CRUD relacionadas à entidade `Partner, e User`.

## Métodos Get:

### 1. Recupera tanto usuário quanto parceiro.

- **Descrição**: Recupera autenticable por email.
- **Método**: `GET`
- **Endpoint**: `/autenticable`
- **Headers**:
  - `Content-Type: application/json`

Exemplo de Resposta:

    Código 200 (OK):

````json
{
  "name": "John Doe",
  "moedaCapiba": 0
}
````

## Métodos Post:

### 1. Criar conta

- **Descrição**: Cria conta tanto do usuário quanto do parceiro.
- **Método**: `POST`
- **Endpoint**: `/autenticable/createAccount/{token}`
- **Headers**:
  - `Content-Type: application/json`

Exemplo de Resposta:

    Código 201 (OK):
    
    {   
      "status": "success",
      "message": ""entidade criada com sucesso""
    }

# Password-recovery

Endpoints disponíveis no **Password-recovery Controller**, responsável por gerenciar operações relacionadas à recuperação de senha das entidades `Partner, e User`.

# Métodos Post:

### 1. Solicita recuperação de senha.

- **Descrição**: Solicita recuperação de senha pelo email.
- **Método**: `POST`
- **Endpoint**: `/password-recover/request`
- **Parâmetros da requisição**: `email` : "string".
- **Headers**:
  - `Content-Type: application/json`

Exemplo de Resposta:

    Código 201 (OK):
    
    {   
      "status": "success",
      "message": "Email enviado com sucesso!"
    }

### 2. Altera a senha do usuario.

- **Descrição**: Altera senha do usuario no sistema.
- **Método**: `POST`
- **Endpoint**: `/password-recover/reset`
- **Parâmetros da requisição**: `token` "string".
- **Headers**:
  - `Content-Type: application/json`

**Corpo da Requisição**:
````json
{
  "password": "string"
}
````
Exemplo de Resposta:

    Código 201 (OK):
    
    {   
      "status": "success",
      "message": "Senha alterada com sucesso!"
    }

# Auth

Endpoints disponíveis no **Auth Controller**, responsável por gerenciar operações relacionadas à recuperação de senha das entidades `Partner, e User`.

# Métodos Post:

### 1. Login.

- **Descrição**: Loga o usuario.
- **Método**: `POST`
- **Endpoint**: `/auth/login`
- **Headers**:
  - `Content-Type: application/json`
  
**Corpo da Requisição**:
````json
{
  "email": "string",
  "password": "string"
}
````
Exemplo de Resposta:

    Código 201 (OK):
    
    {   
      "token": "string"
    }
