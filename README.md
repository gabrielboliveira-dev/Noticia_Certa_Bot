# 📰 Notícia Certa Bot

✨ **Agregador de Notícias Inteligente e Personalizado via Telegram**

---

## 🚀 Sobre o Projeto

O **Notícia Certa Bot** é um sistema robusto de agregação e recomendação de notícias, projetado para operar através de uma interface conversacional no Telegram. Ele resolve o desafio de manter os usuários informados com conteúdo relevante, filtrando e priorizando notícias com base em seus interesses. O projeto foi desenvolvido com foco em alta escalabilidade, testabilidade e desacoplamento, aplicando rigorosamente os princípios de **Clean Architecture**, **SOLID** e **Clean Code**.

---

## 🛠️ Tecnologias e Ferramentas

Este projeto utiliza uma stack moderna e amplamente adotada no ecossistema Java:

*   **Linguagem:** Java 17
*   **Framework:** Spring Boot 3.2.4
    *   *Justificativa:* Escolha padrão para aplicações Java robustas, oferecendo um ecossistema completo e facilidade de desenvolvimento.
*   **Banco de Dados:** PostgreSQL
    *   *Justificativa:* Banco de dados relacional de código aberto, conhecido por sua confiabilidade, robustez e capacidade de lidar com grandes volumes de dados.
*   **Mensageria:** RabbitMQ
    *   *Justificativa:* Broker de mensagens para comunicação assíncrona, essencial para o sistema de notificações proativas (Breaking News).
*   **Interface:** Telegram Bots API (via `telegrambots-spring-boot-starter`)
    *   *Justificativa:* Permite a construção de bots interativos e ricos em funcionalidades para a plataforma Telegram.
*   **Agregação de Feeds:** ROME
    *   *Justificativa:* Biblioteca para parsing e geração de feeds RSS/Atom, utilizada na coleta de notícias.
*   **Persistência:** Spring Data JPA / Hibernate
    *   *Justificativa:* Facilita a interação com o banco de dados, abstraindo a complexidade do JDBC e do SQL.
*   **Contêineres:** Docker & Docker Compose
    *   *Justificativa:* Para orquestração e gerenciamento fácil dos serviços de infraestrutura (PostgreSQL, RabbitMQ) em ambiente de desenvolvimento.
*   **Build Tool:** Apache Maven
    *   *Justificativa:* Gerenciamento de dependências e ciclo de vida do projeto.
*   **Auxiliares:** Lombok, JAXB API
    *   *Justificativa:* Lombok reduz o boilerplate code; JAXB API é necessária para compatibilidade com algumas bibliotecas em Java 9+.
*   **Testes:** JUnit 5, Mockito, Testcontainers
    *   *Justificativa:* Ferramentas padrão para testes unitários e de integração, garantindo a qualidade e a robustez do código.
- **CI/CD:** GitHub Actions
    *   *Justificativa:* Pipeline automatizado para validação de build e testes a cada novo commit ou Pull Request.

---

## 🏗️ Arquitetura

O projeto segue a **Clean Architecture**, dividindo o sistema em camadas concêntricas para garantir separação de responsabilidades, testabilidade e manutenibilidade:

1.  **Enterprise Business Rules (Domain):** Contém as entidades de negócio (`Article`, `User`) e interfaces de repositório, sem dependências externas.
2.  **Application Business Rules (Application):** Define os casos de uso (`IngestArticleUseCase`, `SubscribeUserUseCase`, `RecommendArticlesUseCase`) e interfaces de gateway, orquestrando a lógica de negócio.
3.  **Interface Adapters (Infrastructure):** Implementa os gateways e adaptadores para frameworks externos (Telegram Bot, Spring Data JPA, RabbitMQ, RSS Fetcher).
4.  **Frameworks & Drivers:** Camada mais externa, composta pelos frameworks e ferramentas de infraestrutura.

Além disso, o projeto adere aos princípios **SOLID** e **Clean Code**, promovendo um código legível, flexível e fácil de estender.

---

## ✨ Funcionalidades Implementadas

O Notícia Certa Bot oferece as seguintes funcionalidades principais:

*   **Agregação Multi-fonte (RSS):**
    *   Coleta e normalização de notícias via Feeds RSS.
    *   *Status:* Implementado e funcional para RSS. A agregação via APIs REST está estruturada, mas sem implementação explícita.
*   **Bot Interativo no Telegram:**
    *   Interface conversacional para interação com o usuário.
    *   **Comandos Suportados:**
        *   `/start`: Inicia o bot, registra o usuário e fornece uma mensagem de boas-vindas.
        *   `/news`: Lista as 5 notícias mais recentes disponíveis.
        *   `/subscribe <tópico>`: Permite ao usuário adicionar um tópico de interesse para personalização de recomendações.
        *   `/recommend`: Apresenta uma lista de notícias recomendadas com base nos tópicos de interesse do usuário.
    *   *Status:* Implementado para os comandos básicos. A "interface completa" para gestão avançada de feeds/preferências e funcionalidades como paginação ainda pode ser expandida.
*   **Algoritmo de Recomendação:**
    *   Motor de recomendação baseado em tópicos de interesse do usuário.
    *   *Status:* Implementado com uma estratégia básica de filtragem por palavras-chave no título/fonte do artigo que correspondem aos tópicos do usuário. A sofisticação pode ser aprimorada para incluir histórico de leitura e técnicas mais avançadas.
*   **Notificações Proativas (Breaking News):**
    *   Sistema assíncrono para alertas urgentes em background via RabbitMQ.
    *   *Status:* Estruturado e integrado para envio de notificações de "breaking news" quando novos artigos são ingeridos.
*   **Gestão de Assinaturas:**
    *   Controle de usuários e personalização de tópicos de interesse.
    *   *Status:* Implementado para registro de usuário e adição de tópicos de interesse.

---

## ⚙️ Como Rodar o Projeto Localmente

Siga estes passos para configurar e executar o Notícia Certa Bot em sua máquina local.

### Pré-requisitos

*   Java 17 JDK
*   Docker & Docker Compose
*   Apache Maven

### 1. Clonar o Repositório

```sh
git clone [URL_DO_SEU_REPOSITORIO]
cd noticia-certa-bot
```

### 2. Subir a Infraestrutura com Docker Compose

Este comando iniciará os contêineres do PostgreSQL (banco de dados) e RabbitMQ (broker de mensagens) em segundo plano.

```sh
docker-compose up -d
```

Verifique se os contêineres estão rodando:

```sh
docker-compose ps
```

Ambos devem estar com status `Up`.

### 3. Configurar Variáveis de Ambiente e Arquivos de Configuração

#### a. Obter o Token do Telegram Bot

1.  No Telegram, procure por **@BotFather**.
2.  Crie um novo bot usando `/newbot` e siga as instruções para definir um nome e um `username` (ex: `@NoticiaCertaBot`).
3.  O BotFather fornecerá um **token HTTP API** (uma longa sequência de caracteres). Guarde este token.

#### b. Criar o Arquivo `.env`

Na **raiz do seu projeto** (a mesma pasta onde está o `pom.xml` e o `compose.yaml`), crie um novo arquivo chamado `.env` e adicione a seguinte linha, substituindo `SEU_TOKEN_DO_BOT_AQUI` pelo token que você obteve do BotFather:

```
TELEGRAM_BOT_TOKEN=SEU_TOKEN_DO_BOT_AQUI
```

**Importante:** Adicione `.env` ao seu `.gitignore` para evitar que o token seja versionado:

```
# .gitignore
.env
```

#### c. Verificar `src/main/resources/application.properties`

O arquivo `application.properties` já deve estar configurado para ler o token do `.env` e se conectar aos serviços do Docker Compose. Verifique se ele contém as seguintes configurações (os valores devem corresponder ao seu `compose.yaml`):

```properties
# Application Name
spring.application.name=noticia-certa-bot

# Telegram Bot Configuration
telegram.bot.username=NoticiaCertaBot # Substitua pelo username real do seu bot
telegram.bot.token=${TELEGRAM_BOT_TOKEN}

# Database Configuration (PostgreSQL)
spring.datasource.url=jdbc:postgresql://localhost:5432/noticiacerta_db
spring.datasource.username=user
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true

# RabbitMQ Configuration
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

### 4. Compilar e Rodar a Aplicação

1.  **Limpar e Instalar Dependências:**

    ```sh
    ./mvnw clean install -U
    ```

    O flag `-U` força o Maven a verificar atualizações, garantindo que todas as dependências sejam baixadas corretamente.

2.  **Executar a Aplicação:**

    ```sh
    ./mvnw spring-boot:run
    ```

    Aguarde até que a aplicação inicie completamente. Você deverá ver mensagens de log indicando que o Spring Boot foi inicializado e o bot do Telegram está rodando.

### 5. Testes Manuais (via Telegram)

Com o bot rodando, interaja com ele no Telegram:

*   Envie `/start`: O bot deve responder com uma mensagem de boas-vindas e registrar você.
*   Envie `/news`: O bot deve listar as últimas notícias (se houver dados no DB).
*   Envie `/subscribe tecnologia`: O bot deve confirmar sua inscrição no tópico "tecnologia".
*   Envie `/recommend`: O bot deve apresentar notícias recomendadas com base nos seus tópicos de interesse.
*   Envie um comando não reconhecido: O bot deve responder com a mensagem de erro padrão.

---

## 🚦 Endpoints Principais

Este projeto é primariamente um **Bot do Telegram** e sua interação principal se dá através de comandos enviados diretamente na plataforma Telegram. Não há endpoints REST públicos para as funcionalidades centrais do bot.

---

## ✅ Demonstração de Boas Práticas

O projeto Notícia Certa Bot é um exemplo prático da aplicação de diversas boas práticas de desenvolvimento de software:

*   **Clean Architecture:** Separação clara de responsabilidades entre as camadas de Domínio, Aplicação e Infraestrutura.
*   **Princípios SOLID:** Código projetado para ser flexível, extensível e de fácil manutenção.
*   **Clean Code:** Foco em código legível, com nomes significativos e funções coesas.
*   **Gerenciamento de Dependências:** Uso do `spring-boot-starter-parent` para gerenciamento de versões e dependências.
*   **Variáveis de Ambiente:** Utilização de arquivos `.env` e variáveis de ambiente para gerenciar configurações sensíveis (como tokens), garantindo segurança e flexibilidade.
*   **Testcontainers:** Uso de contêineres Docker para testes de integração robustos e isolados com PostgreSQL e RabbitMQ.

---