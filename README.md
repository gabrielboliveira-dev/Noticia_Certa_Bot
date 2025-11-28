# 📰 Notícia Certa Bot

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green)](https://spring.io/projects/spring-boot)
[![Telegram API](https://img.shields.io/badge/Telegram-Bot_API-blue)](https://core.telegram.org/bots/api)
[![Architecture](https://img.shields.io/badge/Architecture-Clean_Architecture-purple)](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

> **Agregador de Notícias Inteligente via Telegram**

O **Notícia Certa Bot** é um sistema robusto de agregação e recomendação de notícias que opera através de uma interface conversacional no Telegram. O projeto aplica rigorosamente os princípios de **Clean Architecture**, **SOLID** e **Clean Code**, visando alta escalabilidade, testabilidade e desacoplamento entre regras de negócio e infraestrutura.

## 🚀 Principais Funcionalidades

* **Agregação Multi-fonte:** Coleta e normalização de notícias via APIs REST e Feeds RSS em tempo real.
* **Bot Interativo:** Interface completa no Telegram para leitura, configuração de feed e gestão de preferências.
* **Algoritmo de Recomendação:** Motor inteligente que prioriza artigos com base no histórico de leitura e interesses do usuário.
* **Notificações Proativas (Breaking News):** Sistema assíncrono (RabbitMQ) para alertas urgentes em background.
* **Gestão de Assinaturas:** Controle de usuários e personalização de tópicos.

## 🏗️ Arquitetura

Este projeto segue a **Clean Architecture** (Arquitetura Limpa), dividindo o sistema em camadas concêntricas:

1.  **Enterprise Business Rules (Entities):** O núcleo do domínio (ex: `Article`, `User`, `Preference`). Sem dependências externas.
2.  **Application Business Rules (Use Cases):** Orquestração lógica (ex: `RecommendArticles`, `ProcessNewsFeed`).
3.  **Interface Adapters:** Controladores, Presenters e Gateways que convertem dados.
4.  **Frameworks & Drivers (Infrastructure):** Banco de Dados, Web Frameworks, Telegram API, RabbitMQ.

## 🛠️ Tech Stack

* **Linguagem:** Java 17
* **Framework:** Spring Boot 3
* **Banco de Dados:** PostgreSQL (Persistência)
* **Mensageria:** RabbitMQ
* **Interface:** Telegram Bots API
* **Testes:** JUnit 5, Mockito, Testcontainers
* **Infra:** Docker, Docker Compose
* **Outros:** Lombok, MapStruct, Spring Security, JWT, Swagger UI.

## ⚙️ Pré-requisitos

* Java 17 JDK
* Docker & Docker Compose
* Maven

## 🔧 Como Rodar Localmente

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/noticia-certa-bot.git](https://github.com/seu-usuario/noticia-certa-bot.git)
    cd noticia-certa-bot
    ```

2.  **Suba a infraestrutura (Postgres & RabbitMQ):**
    ```bash
    docker-compose up -d
    ```

3.  **Configure as Variáveis de Ambiente:**
    Crie um arquivo `.env` ou configure no seu `application.yml`:
    * `TELEGRAM_BOT_TOKEN`
    * `DB_URL`, `DB_USER`, `DB_PASS`

4.  **Execute a aplicação:**
    ```bash
    ./mvnw spring-boot:run
    ```

## 🧪 Testes

O projeto utiliza uma estratégia de testes abrangente:

* **Testes Unitários:** Focados nas Regras de Negócio e Casos de Uso.
* **Testes de Integração:** Utilizando **Testcontainers** para validar a integração com Banco de Dados e RabbitMQ em ambiente isolado.

```bash
./mvnw test
```

## 🤝 Contribuição

Este é um projeto focado em estudo e aprimoramento técnico. Sugestões de refatoração visando Clean Code são bem-vindas.
