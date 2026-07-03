# Movimentos Manuais API

API REST desenvolvida para o desafio técnico Java - BNP Paribas & Antlia.

A aplicação permite consultar produtos, consultar COSIFs por produto, listar movimentos manuais e incluir novos movimentos manuais.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web / Web MVC
- Spring Data JPA
- Bean Validation
- H2 Database
- Lombok
- JUnit
- Mockito
- MockMvc
- Maven

## Arquitetura

O projeto foi organizado em camadas:

```text
controller
dto
entity
exception
repository
service
service.impl
config
```

## Regras implementadas

- Listagem automática dos movimentos manuais cadastrados.
- Consulta de produtos ativos.
- Consulta de COSIFs ativos por produto.
- Inclusão de movimento manual.
- Geração automática do número de lançamento.
- O número de lançamento é gerado com base no último lançamento do mesmo mês e ano, somando `+1`.
- Campo `COD_USUARIO` preenchido automaticamente com `TESTE`.
- Campo `DAT_MOVIMENTO` preenchido automaticamente com a data/hora da gravação.
- Validação dos campos obrigatórios.
- Tratamento global de erros.
- Configuração global de CORS para integração com o frontend Angular.

## Como rodar a aplicação

### Pré-requisitos

- Java 21 instalado
- Maven configurado ou execução via IntelliJ IDEA

### Executando pela IDE

Execute a classe principal:

```text
MovimentosManuaisApiApplication
```

A API será iniciada em:

```text
http://localhost:8080
```

## Banco de dados H2

A aplicação usa banco H2 em memória.

Console H2:

```text
http://localhost:8080/h2-console
```

Dados de conexão:

```text
JDBC URL: jdbc:h2:mem:movimentosdb
User Name: sa
Password: deixe vazio
```

## Endpoints

### Listar produtos

```http
GET /api/produtos
```

Exemplo de resposta:

```json
[
  {
    "codProduto": "0001",
    "desProduto": "Produto Corrente"
  }
]
```

### Listar COSIFs por produto

```http
GET /api/produtos/{codProduto}/cosifs
```

Exemplo:

```http
GET /api/produtos/0001/cosifs
```

Exemplo de resposta:

```json
[
  {
    "codProduto": "0001",
    "codCosif": "11000000001",
    "codClassificacao": "110001",
    "descricaoCombo": "11000000001 - 110001"
  }
]
```

### Listar movimentos manuais

```http
GET /api/movimentos-manuais
```

Exemplo de resposta:

```json
[
  {
    "mes": 7,
    "ano": 2026,
    "numeroLancamento": 1,
    "codProduto": "0001",
    "desProduto": "Produto Corrente",
    "codCosif": "11000000001",
    "codClassificacao": "110001",
    "descricao": "Movimento inicial de teste",
    "dataMovimento": "2026-07-03T10:30:00",
    "codUsuario": "TESTE",
    "valor": 1000.00
  }
]
```

### Incluir movimento manual

```http
POST /api/movimentos-manuais
Content-Type: application/json
```

Body:

```json
{
  "mes": 7,
  "ano": 2026,
  "codProduto": "0001",
  "codCosif": "11000000001",
  "valor": 2500.75,
  "descricao": "Movimento manual teste"
}
```

Exemplo de resposta:

```json
{
  "mes": 7,
  "ano": 2026,
  "numeroLancamento": 2,
  "codProduto": "0001",
  "desProduto": "Produto Corrente",
  "codCosif": "11000000001",
  "codClassificacao": "110001",
  "descricao": "Movimento manual teste",
  "dataMovimento": "2026-07-03T11:00:00",
  "codUsuario": "TESTE",
  "valor": 2500.75
}
```

## Testes

O projeto possui testes unitários e testes de controller.

### Services testados

- `ProdutoServiceImplTest`
- `ProdutoCosifServiceImplTest`
- `MovimentoManualServiceImplTest`

### Controllers testados

- `ProdutoControllerTest`
- `ProdutoCosifControllerTest`
- `MovimentoManualControllerTest`

Para executar os testes:

```bash
mvn test
```

Ou execute pela IDE.

## Frontend

O frontend Angular está em outro repositório:

```text
https://github.com/domegospel/movimentos-manuais-frontend
```

## Observações técnicas

- O banco H2 foi utilizado para facilitar a execução local do desafio.
- Foram utilizados DTOs para evitar exposição direta das entidades JPA.
- Os services foram implementados com interface e implementação.
- O tratamento de erros foi centralizado com `@RestControllerAdvice`.
- O CORS foi configurado globalmente para permitir chamadas do Angular em `http://localhost:4200`.
