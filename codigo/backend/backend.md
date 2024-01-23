# Guia para Rodar um Backend Local Spring Boot

Este guia explica como configurar e executar um projeto Spring Boot em sua máquina local.

## Pré-requisitos

- Java JDK 8 ou superior instalado
- Maven instalado (opcional, se o projeto tiver um wrapper Maven)
- IDE de sua escolha (IntelliJ IDEA, Eclipse, VSCode, etc)

## Passos

### 1. Clonar o Repositório

Primeiro, clone o repositório do seu projeto Spring Boot:

```bash
git clone [URL_DO_REPOSITORIO]
cd [NOME_DO_PROJETO]
```

### 2. Construir o Projeto

Com Maven:

```bash
mvn clean install
```

Ou, se o projeto tiver um wrapper Maven:

```bash
./mvnw clean install
```

### 3. Executar o Projeto

Você pode executar o projeto de várias maneiras:

#### Via Linha de Comando

```bash
mvn clean install
mvn spring-boot:run
```

#### Via IDE

Abra o projeto na sua IDE e execute a classe principal que contém o método `main`.

### 4. Verificar

Após iniciar o servidor, você pode verificar se está funcionando acessando:

```
http://localhost:8080
```

Substitua `8080` pela porta configurada no seu projeto, se diferente.

## Documentação API

Use o postman para rodar com o conjunto teste já disponibilizado.

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/23984859-b387a219-a85c-46c2-b346-58361feec02e?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D23984859-b387a219-a85c-46c2-b346-58361feec02e%26entityType%3Dcollection%26workspaceId%3Dc8644162-2466-4521-9929-5675ed3adf7f)