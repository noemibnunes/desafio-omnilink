# Desafio Técnico - Omnilink
Desenvolvimento de uma API para gerenciamento de cliente e veículos.

## Tecnologias Utilizadas

- **Backend**: Spring Boot (Java 17)
- **Banco de Dados**: MySQL rodando em container Docker
- **Configuração de Ambiente**: `.env`
- **Build Tool**: Maven
- **Testes**: JUnit + Mockito
- **Autenticação**: JWT
- **Outros**: Swagger/OpenAPI, Docker Compose, etc.

##  Pré-requisitos

Antes de rodar o projeto, certifique-se que você possui:

- Java 17
- Docker & Docker Compose instalados
- IDE como VS Code, IntelliJ IDEA, ou Eclipse

# Estrutura do projeto

**src/**: Código-fonte da aplicação.

**Dockerfile / docker-compose.yml**: Arquivos para subir apenas o MySQL em contêiner.

**.env**: Variáveis de ambiente para configuração da aplicação, incluindo banco de dados, portas e outras chaves necessárias.

**pom.xml**: Configuração do Maven, gerenciando dependências e plugins.

# Execução

**Passo a Passo para Rodar o Projeto**

### 1. Clonar o repositório
```
git clone https://github.com/noemibnunes/desafio-omnilink
cd desafio-omnilink
```

### 2. Criar arquivo de variáveis de ambiente
```
cp .env.example .env
```

### 3. Subir o banco de dados com Docker
```
docker-compose up -d
```

### 4. Acessar documentação (Swagger)
```
http://localhost:8080/swagger-ui/index.html#/
```