# BankingSystemApi

Descrição breve do projeto, destacando sua finalidade e funcionalidades.

## Tecnologias Utilizadas

- Java 21
- Spring
- Swagger para documentação da API

## Documentação da API

A documentação completa da API está disponível [aqui](http://3.15.177.46:8080/swagger-ui/index.html#/).

# Endpoints da API

A API fornece uma série de endpoints para gerenciamento de contas de usuário e transações. Abaixo estão os principais endpoints disponíveis:

## User Account Controller

### POST /api/useraccount
- **Descrição**: Cria uma nova conta de usuário.
- **Parâmetros**: N/A
- **Corpo da Requisição**: Detalhes da conta do usuário a ser criada.
- **Resposta de Sucesso**: Detalhes da conta do usuário criada.

## Account Transaction Controller

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
- **Descrição**: Lista todas as transações realizadas por uma conta específica.
- **Parâmetros**:
  - `accountId`: ID da conta.
- **Resposta de Sucesso**: Lista de transações realizadas pela conta.


