# BankingSystemApi

Este é um sistema bancário que permite aos usuários criar contas, realizar transações e visualizar detalhes das transações.

## Tecnologias Utilizadas

- Java 21
- Spring
- MariaDB
- Gradle
- AWS EC2
- AWS RDS
- Swagger para documentação da API

## Como executar localmente

1. Clone este repositório.
2. Execute `./gradlew build` para construir o projeto.
3. Execute `java -jar build/libs/BankingSystemSpring.jar` para iniciar o servidor.

## Deploy na AWS

O deploy é feito automaticamente na AWS EC2 sempre que há um push na branch `develop`. O processo de deploy inclui a construção do projeto com Gradle, copiando o JAR resultante para o servidor EC2 e reiniciando o serviço Spring Boot.

O banco de dados é uma instância RDS, onde o Spring se Encarrega de fazer as manipulações necessárias

## Documentação da API

A documentação completa da API está disponível [aqui](http://18.189.140.162:8080/swagger-ui/index.html#/).

# Endpoints da API

A API fornece uma série de endpoints para gerenciamento de contas de usuário e transações. Abaixo estão os principais endpoints disponíveis:

## Contas de Usuário

### POST /api/useraccount
- **Descrição**: Cria uma nova conta de usuário.
- **Parâmetros**: N/A
- **Corpo da Requisição**: Detalhes da conta do usuário a ser criada.
- **Resposta de Sucesso**: Detalhes da conta do usuário criada.

## Transações em Conta

### POST /api/transactions/withdraw/{accountId}
- **Descrição**: Realiza uma operação de saque em uma conta.
- **Parâmetros**:
  - `accountId`: ID da conta para saque.
- **Corpo da Requisição**: Valor a ser sacado.
- **Resposta de Sucesso**: Detalhes da transação realizada.

### POST /api/transactions/transfer
- **Descrição**: Realiza uma transferência entre contas.
- **Corpo da Requisição**: Detalhes da transação incluindo conta de origem, chave PIX da conta destino e valor.
- **Resposta de Sucesso**: Confirmação da transferência.

### POST /api/transactions/deposit/{accountId}
- **Descrição**: Deposita um valor em uma conta específica.
- **Parâmetros**:
  - `accountId`: ID da conta para depósito.
- **Corpo da Requisição**: Valor a ser depositado.
- **Resposta de Sucesso**: Detalhes da transação de depósito.

### GET /api/transactions/{transactionId}
- **Descrição**: Obtém detalhes de uma transação específica.
- **Parâmetros**:
  - `transactionId`: ID da transação.
- **Resposta de Sucesso**: Detalhes da transação solicitada.

### GET /api/transactions/account/{accountId}
- **Descrição**: Gera um Extrato da conta trazendo o saldo atual e listando todas as transações realizadas por uma conta específica.
    Este endpoint permite filtrar as transações por um período específico, utilizando os parâmetros de consulta opcionais startDate e endDate
- **Parâmetros**:
  - `accountId`: ID da conta.
  - `startDate` (opcional): Data de início para filtrar transações. Formato esperado: AAAA-MM-DD.
  - `endDate` (opcional): Data final para filtrar transações. Formato esperado: AAAA-MM-DD.
- **Resposta de Sucesso**: Lista de transações realizadas pela conta.
