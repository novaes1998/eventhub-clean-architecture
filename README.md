# EventHub – Sistema de Gestão de Eventos

API REST para gestão de eventos e participantes, desenvolvida com **Java**, **Spring Boot**, **MySQL** e **Clean Architecture**.

## 🚀 Tecnologias
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Bean Validation
- Maven

## 🧱 Arquitetura
O projeto segue os princípios da **Clean Architecture**, com separação clara entre:
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
- Decrementar capacidade após a venda

## 📬 Status Codes
- 200 OK
- 201 Created
- 204 No Content
- 400 Bad Request
- 404 Not Found

---
Projeto desenvolvido para fins de estudo e portfólio.
