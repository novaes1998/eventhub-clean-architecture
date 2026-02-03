# EventHub – Sistema de Gestão de Eventos

API REST para gestão de eventos e participantes, desenvolvida com Java, Spring Boot, MySQL e Clean Architecture.

## 🚀 Tecnologias
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Bean Validation
- Maven

## 🧱 Arquitetura
O projeto segue os princípios da Clean Architecture, com separação clara entre:
- Domain (Entidades e regras de negócio)
- Application (Use Cases)
- Infrastructure (Persistência, frameworks)
- Interfaces (Controllers / DTOs)

## 📌 Funcionalidades
### Eventos
- Criar evento
- Listar eventos
- Buscar evento por ID
- Atualizar evento
- Deletar evento
- Validação de capacidade e data

### Participantes
- Criar participante
- Listar participantes
- Buscar por ID
- Atualizar participante
- Deletar participante
- Comprar ingresso
- Listar ingressos comprados

## 🔒 Regras de Negócio
- Não permitir eventos com data no passado
- Não permitir campos obrigatórios vazios
- Validar e-mail do participante
- Validar capacidade do evento antes da compra
- Não permitir compra de ingresso para eventos lotados
- Decrementar capacidade após a venda
- Retornar erro ao buscar eventos inexistentes

## 🧪 Testes de API (Postman)
O projeto possui uma collection do Postman com todas as requisições da API, incluindo testes automatizados de validação e erro.

### 📂 Localização
Os arquivos estão disponíveis na pasta: `/postman`

- EventHub.postman_collection.json
- EventHub.postman_environment.json

### ▶️ Como importar e executar
1. Abra o Postman
2. Clique em Import
3. Importe os dois arquivos localizados na pasta /postman
4. Selecione o environment EventHub
5. Verifique a variável baseUrl (exemplo): http://localhost:8080
6. Execute as requisições individualmente ou por pasta

### 🧪 Testes implementados
- CRUD completo de Eventos
- CRUD completo de Participantes
- Compra de ingressos
- Listagem de ingressos por participante
- Testes de validação:
    - Evento com nome vazio
    - Evento com data no passado
    - Evento lotado (capacidade = 1 com teste automatizado)
    - Evento não encontrado

Dica: a pasta "Testes de Erro / Validação" pode ser executada integralmente para validar automaticamente as regras da API.

## 📬 Status Codes
- 200 OK
- 201 Created
- 204 No Content
- 400 Bad Request
- 404 Not Found
- 409 Conflict

---
Projeto desenvolvido para fins de estudo e portfólio.
