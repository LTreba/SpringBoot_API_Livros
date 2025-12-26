# 📚 API de Livros e Autores

Projeto utilizado para aplicação de conceitos de **JPA**, **REST**, **JWT**, **OAuth2**, com uso de **Docker**, **AWS** e **RabbitMQ**.

---

## 📌 Especificação do Projeto
# Cadastro de Autor

## Descrição
Deseja-se cadastrar os autores de livros, bem como realizar suas atualizações, consultas e permitir sua exclusão.

## Atores
- Somente o **Gerente** pode cadastrar, atualizar e remover Autores.
- O usuário **Operador** poderá somente consultar os dados dos Autores.

## Campos solicitados pelo Negócio

### Dados que deverão ser guardados
- Nome *
- Data de Nascimento *
- Nacionalidade *

\* Campos com (*) são obrigatórios

## Campos Lógicos
Dados não solicitados pela equipe de negócio, mas são de controle da aplicação e auditoria:
- ID - UUID
- Data Cadastro
- Data Ultima Atualização
- Usuário Ultima Atualização

## Regras de Negócio
- Não permitir cadastrar um Autor com mesmo Nome, Data de Nascimento e Nacionalidade.
- Não permitir excluir um Autor que possuir algum livro.

## Contrato da API

### Cadastrar novo Autor

#### Requisição
- URI: /autores
- Método: POST

**Body:**
```json
{
  "nome": "string",
  "dataNascimento": "date",
  "nacionalidade": "string"
}
```

#### Respostas

**1. Sucesso**  
Código: 201 - Created  
Header: Location - URI do recurso criado

**2. Erro de Validação**  
Código: 422 - Unprocessable Entity  

```json
{
  "status": 422,
  "message": "Erro de Validação",
  "errors": [
    { "field": "nome", "error": "Nome é obrigatório" }
  ]
}
```

**3. Autor Duplicado**  
Código: 409 - Conflict  

```json
{
  "status": 409,
  "message": "Registro Duplicado",
  "errors": []
}
```

### Visualizar Detalhes do Autor

#### Requisição
- URI: /autores/{ID}
- Método: GET

#### Respostas

**1. Sucesso**  
Código: 200 - OK  

```json
{
  "id": "uuid",
  "nome": "string",
  "dataNascimento": "date",
  "nacionalidade": "string"
}
```

**2. Erro**  
Código: 404 - Not Found

### Excluir Autor

#### Requisição
- URI: /autores/{ID}
- Método: DELETE

#### Respostas

**1. Sucesso**  
Código: 204 - No Content

**2. Erro**  
Código: 400 - Bad Request  

```json
{
  "status": 400,
  "message": "Erro na exclusão: registro está sendo utilizado.",
  "errors": []
}
```

### Pesquisar Autores

#### Requisição
- URI: /autores
- Query Params: nome, nacionalidade
- Método: GET

#### Respostas

**1. Sucesso**  
Código: 200 - OK  

```json
[
  {
    "id": "uuid",
    "nome": "string",
    "dataNascimento": "date",
    "nacionalidade": "string"
  }
]
```

### Atualizar Autor

#### Requisição
- URI: /autores/{ID}
- Método: PUT

**Body:**
```json
{
  "nome": "string",
  "dataNascimento": "date",
  "nacionalidade": "string"
}
```

#### Respostas

**1. Sucesso**  
Código: 204 - No Content

**2. Erro de Validação**  
Código: 422 - Unprocessable Entity  

```json
{
  "status": 422,
  "message": "Erro de Validação",
  "errors": [
    { "field": "nome", "error": "Nome é obrigatório" }
  ]
}
```

**3. Autor Duplicado**  
Código: 409 - Conflict  

```json
{
  "status": 409,
  "message": "Registro Duplicado",
  "errors": []
}
```


# Cadastro de Livros

## Descrição
Deseja-se cadastrar os livros, bem como realizar suas atualizações, consultas e permitir sua exclusão.  
Ao consultar um livro, deverá ser disponibilizado alguns filtros de pesquisa para busca paginada.

**Campos de busca:**
- Título
- Gênero
- ISBN
- Nome do autor
- Ano de publicação

---

## Atores
Operador e Gerente podem consultar, cadastrar, atualizar e remover Livros.

---

## Campos solicitados pelo Negócio

### Dados que deverão ser guardados
- ISBN *
- Título *
- Data de Publicação *
- Gênero
- Preço
- Autor *

\* Campos com (*) são obrigatórios

---

## Campos Lógicos
Dados não solicitados pela equipe de negócio, mas são de controle da aplicação e auditoria:
- ID - UUID
- Data Cadastro
- Data Última Atualização
- Usuário Última Atualização

---

## Regras de Negócio
- Não permitir cadastrar um Livro com mesmo ISBN que outro.
- Se a data de publicação for a partir de 2020, deverá ter o preço informado obrigatoriamente.
- Data de publicação não pode ser uma data futura.

---

## Contrato API

### Cadastrar novo Livro

#### Requisição
- URI: /livros
- Método: POST

**Body:**
```json
{
  "isbn": "string",
  "titulo": "string",
  "dataPublicacao": "date",
  "genero": "enum",
  "preco": number,
  "id_autor": "uuid"
}
```

#### Respostas

**1. Sucesso**  
Código: 201 - Created  
Header: Location - URI do recurso criado

**2. Erro de Validação**  
Código: 422 - Unprocessable Entity  

```json
{
  "status": 422,
  "message": "Erro de Validação",
  "errors": [
    { "field": "titulo", "error": "Campo obrigatório" }
  ]
}
```

**3. ISBN Duplicado**  
Código: 409 - Conflict  

```json
{
  "status": 409,
  "message": "Isbn Duplicado",
  "errors": []
}
```

---

### Visualizar Detalhes do Livro

#### Requisição
- URI: /livros/{ID}
- Método: GET

#### Respostas

**1. Sucesso**  
Código: 200 - OK  

```json
{
  "id": "uuid",
  "isbn": "string",
  "titulo": "string",
  "dataPublicacao": "date",
  "genero": "enum",
  "preco": number,
  "autor": {
    "nome": "string",
    "dataNascimento": "date",
    "nacionalidade": "string"
  }
}
```

**2. Erro**  
Código: 404 - Not Found

---

### Excluir Livro

#### Requisição
- URI: /livros/{ID}
- Método: DELETE

#### Respostas

**1. Sucesso**  
Código: 204 - No Content

**2. Erro**  
Código: 404 - Livro inexistente

---

### Pesquisar Livros

#### Requisição
- URI: /livros
- Query Params: isbn, titulo, nome autor, genero, ano de publicação
- Método: GET

#### Respostas

**1. Sucesso**  
Código: 200 - OK  

```json
[
  {
    "id": "uuid",
    "isbn": "string",
    "titulo": "string",
    "dataPublicacao": "date",
    "genero": "enum",
    "preco": number,
    "autor": {
      "nome": "string",
      "dataNascimento": "date",
      "nacionalidade": "string"
    }
  }
]
```

---

### Atualizar Livro

#### Requisição
- URI: /livros/{ID}
- Método: PUT

**Body:**
```json
{
  "isbn": "string",
  "titulo": "string",
  "dataPublicacao": "date",
  "genero": "enum",
  "preco": number,
  "id_autor": "uuid"
}
```

#### Respostas

**1. Sucesso**  
Código: 204 - No Content

**2. Erro de Validação**  
Código: 422 - Unprocessable Entity  

```json
{
  "status": 422,
  "message": "Erro de Validação",
  "errors": [
    { "field": "titulo", "error": "Campo obrigatório" }
  ]
}
```

**3. ISBN Duplicado**  
Código: 409 - Conflict  

```json
{
  "status": 409,
  "message": "ISBN Duplicado",
  "errors": []
}
```
