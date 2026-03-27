# CRUD Spring Boot + ViaCEP

## 📌 Descrição

API REST desenvolvida em Spring Boot para gerenciamento de produtos, com integração externa à API ViaCEP para validação de distribuição baseada em CEP.

---

## 🚀 Funcionalidades

* CRUD completo de produtos
* Integração com API ViaCEP
* Verificação de distribuição por cidade
* Controle de produtos ativos/inativos
* Migrations com Flyway

---

## 🧠 Regra de Negócio

O sistema verifica se um produto pode ser distribuído para um determinado CEP.

📍 Regra:

* Consulta o CEP na API ViaCEP
* Obtém a cidade correspondente
* Compara com o **distribution_center** do produto no banco

✅ Se forem iguais → retorna **true**
❌ Se forem diferentes → retorna **false**

---

## 🔧 Tecnologias utilizadas

* Java
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Flyway
* API ViaCEP

---

## ▶️ Como executar o projeto

### 1. Configurar o banco de dados

No arquivo `application.properties`, configure seu PostgreSQL:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

---

### 2. Rodar a aplicação

Via IDE ou terminal:

```bash
mvn spring-boot:run
```

---

### 3. Flyway

As tabelas e dados serão criados automaticamente ao iniciar a aplicação.

Inclui:

* Criação da tabela `product`
* Inserção de dados
* Campo `distribution_center`

---

## 🧪 Endpoint principal

### Verificar distribuição

```http
GET http://localhost:8080/product/check?cep=08773380&id=p1
```

📌 Observação:

* Os produtos possuem IDs de **p1 a p20**
* Dados inseridos via Flyway

---

## 📂 Estrutura do projeto

```
controller → recebe requisições HTTP
service → regras de negócio
repository → acesso ao banco
infra → tratamento de exceções
domain → entidades e DTOs
```

---

## 🌐 Integração externa

A aplicação consome a API pública:

https://viacep.com.br/

Exemplo:

```
https://viacep.com.br/ws/08773380/json/
```

---

## 📊 Exemplo de resposta

```json
true
```

---

Projeto desenvolvido para atividade acadêmica.
