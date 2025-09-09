# Desafio Técnico - Omnilink
Desenvolvimento de uma API para gerenciamento de cliente e veículos.

# Tecnologias utilizada

**Java 17**: Linguagem de programação principal.

**Spring Boot**: Framework para desenvolvimento da aplicação.

**MySQL**: Banco de dados relacional.

**Docker**: Contêiner para o banco de dados MySQL (opcional para facilitar o setup).

# Estrutura do projeto

**src/**: Código-fonte da aplicação.

**Dockerfile / docker-compose.yml**: Arquivos para subir apenas o MySQL em contêiner.

**.env**: Variáveis de ambiente para configuração da aplicação, incluindo banco de dados, portas e outras chaves necessárias.

**pom.xml**: Configuração do Maven, gerenciando dependências e plugins.

# Execução
**Usando Docker para o Banco de Dados**

- Certifique-se de ter o Docker instalado.

- Na raiz do projeto, execute:

  - docker-compose up -d

- Isso iniciará o contêiner do MySQL usando as configurações do docker-compose.yml.

**Executando a Aplicação**

- Certifique-se de ter Java 17 e Maven instalados. 
- Clone o repositório:
  - git clone https://github.com/noemibnunes/desafio-omnilink.git
  - cd desafio-omnilink