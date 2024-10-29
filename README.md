<h1 align="center">
  Meu Pet
</h1>

<p align="center">
 <img src="https://img.shields.io/static/v1?label=Tipo&message=Plataforma Web&color=8257E5&labelColor=000000" alt="Plataforma Web" />
</p>

## Descrição do Projeto

Com o crescimento excessivo de animais em situação de rua, viabilizamos um projeto para impactar a situação desses animais presente na Região Metropolitana do Recife.
O projeto "Meu Pet" visa facilitar a adoção, resgate e cuidados de animais em situação de abandono. A plataforma conecta usuários, ONGs, e prestadores de serviços, criando uma rede de apoio para animais necessitados.

## Banco de Dados

### Cadastro de Usuário
- **Campos:**
  - Nome completo
  - Email
  - Senha
  - Telefone

### Cadastro de ONG
- **Campos:**
  - Nome fantasia
  - CNPJ
  - Email
  - Endereço
  - Senha

### Cadastro de Animais para Resgate
- **Campos:**
  - Foto
  - Situação do cão
  - Localização do cão (sem pré-requisito)

### Denuncias
- **Campos:**
  - informacao_do_denunciante
  - localizacao
  - animal_envolvido
  - qtds_animais_envolvidos
  - descricao
  - foto
  - video
  - data_denuncia

### Animais para adoção
- **Campos:**
  - nome
  - tipo
  - porte
  - personalidade
  - vacinado
  - castrado
  - adotado
  - foto
  - localizacao
  
### Servicos parceiros
- **Campos**
  - nome
  - localizacao
  - latitude
  - longitude
  - telefone
  - horario_atendimento

## Funcionalidades

- **Cadastro de ONGs:** Levantar dados de custos para a prefeitura e cadastrar animais disponíveis para adoção.
- **Cadastro de Usuarios:** Os usuarios podem estar adotando os animais, denunciando maus tratos, solicitando resgates.
- **Catálogo de Animais para Adoção:** Sistema de "match" entre animais e adotantes.
- **Denúncia:** Funcionalidade para reportar maus-tratos.
- **Resgate:** Organizar e facilitar resgates de animais em situação de risco.
- **Serviços para Seu Pet:** Divulgação de clínicas e vetenários parceiros para consultas, castrações e vacinas.
- **Divulgação de Eventos e Campanhas:** Informar sobre eventos de adoção, campanhas de castração, e clínicas parceiras.
- **Visualização de Áreas Geográficas:** Melhoria na logística através do cadastro de prestadores de serviços.

## Upgrades Futuros

- **Cadastros para Prestação de Serviços:** Expansão das funcionalidades para incluir serviços adicionais.

## Stakeholders

- Prefeitura
- ONGs
- Voluntários
- Prestadores de Serviços
- Adotantes

## Funcionalidades Futuras Propostas

1. **Geolocalização de Animais Perdidos e Resgatados:** Registro da localização exata para facilitar buscas e resgates.
2. **Integração com Redes Sociais:** Compartilhamento de informações sobre animais para aumentar a visibilidade.
3. **Cadastro Completo de Animais:** Perfis detalhados incluindo histórico médico e necessidades especiais.
4. **Sistema de Notificação em Tempo Real:** Alertas sobre novos animais e emergências de resgate.
5. **Sistema de Denúncias de Maus-Tratos:** Registro de denúncias com opção de anexar fotos e localização.
6. **Raçãometro e Doações Diretas:** Permitir doações de alimentos e itens diretamente para ONGs.
7. **Campanhas de Castração e Vacinação:** Organização de mutirões com prefeituras e veterinários.
8. **Sistema de Adoção Online:** Formalização de adoções com documentação gerada automaticamente.
9. **Acompanhamento Pós-Adoção:** Garantir cuidados necessários e incentivar atualizações periódicas.
10. **Mapeamento de Clínicas Veterinárias e Abrigos:** Mapa interativo para facilitar o atendimento.
11. **Parcerias com Serviços de Transporte:** Facilitar logística para resgates e adoções.
12. **Sistema de Apadrinhamento:** Permitir que pessoas ajudem com custos de cuidados de animais.
13. **Classificação de Urgência para Resgates:** Classificar casos que precisam de resposta imediata.
14. **Sistema de Busca por Características:** Filtros para ajudar na busca de animais por características específicas.
15. **Colaboração com ONGs e Voluntários:** Gerenciamento de voluntários para apoio em resgates e cuidados.

## Contribuições

Para contribuir com o projeto, por favor, envie um pull request ou abra uma issue no repositório. Agradecemos seu interesse em ajudar a melhorar a vida dos animais!

## Tecnologias
 
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Mysql](https://www.mysql.com/)

## Práticas adotadas

- SOLID
- Testes automatizados
- Consultas com filtros dinâmicos usando o Query By Example
- API reativa na web e na camada de banco
- Uso de DTOs para a API
- Injeção de Dependências
- Trello
- Auditoria sobre criação e atualização da entidade

## Como Executar

### Localmente
- Clonar repositório git:
```
git clone https://github.com/ellydamicaella/AnimalAlert.git
```
- Construir o projeto:
```
./mvnw clean package
```
- Executar:
```
java -jar target/meupet-0.0.1-SNAPSHOT.jar
```

A API poderá ser acessada em [localhost:8080](http://localhost:8080).
O Swagger poderá ser visualizado em [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Usando Docker

- Clonar repositório git
- Construir o projeto:
```
./mvnw clean package
```
- Construir a imagem:
```
./mvnw spring-boot:build-image
```
- Executar o container:
```
docker run --name meupet -p 8080:8080  -d meupet:0.0.1-SNAPSHOT
```

A API poderá ser acessada em [localhost:8080](http://localhost:8080).
O Swagger poderá ser visualizado em [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Métodos
Requisições para a API devem seguir os padrões:
| Método | Descrição |
|---|---|
| `GET` | Retorna informações de um ou mais registros. |
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

## Limites (Throttling)
Existe o limite de `#` requisições por minuto por aplicação+usuário.

Você pode acompanhar esses limites nos `headers`: `X-RateLimit-Limit`, `X-RateLimit-Remaining` enviados em todas as respostas da API.

Ações de `listar` exibem `#` registros por página. Não é possível alterar este número.

Por questões de segurança, todas as requisições serão feitas através do protocolo `HTTPS`.

## API Endpoints

A ser feito
