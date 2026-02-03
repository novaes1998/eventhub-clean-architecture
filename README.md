# EventHub – Sistema de Gestão de Eventos

API REST para gestão de eventos e participantes, desenvolvida com Java, Spring Boot, MySQL e Clean Architecture.

## 🚀 Tecnologias
### Backend
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Bean Validation
- Maven

### Frontend
- React (Vite)
- JavaScript (JSX)
- Axios
- React Router DOM

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

### Frontend (Interface Web)
Interface simples e objetiva em React para executar e testar as operações da API:
- Tela de **Eventos**: listar, criar, editar e deletar
- Tela de **Participantes**: listar, criar, editar e deletar
- Tela de **Ingressos**:
    - Selecionar **1 participante** e **1 evento** via dropdown
    - Comprar ingresso
    - Listar ingressos do participante selecionado
    - Exibição em tabela (inclui data da compra)

## 🔒 Regras de Negócio
- Não permitir eventos com data no passado
- Não permitir campos obrigatórios vazios
- Validar e-mail do participante
- Validar capacidade do evento antes da compra
- Não permitir compra de ingresso para eventos lotados
- Decrementar capacidade após a venda
- Retornar erro ao buscar eventos inexistentes

## 🖥️ Como executar o projeto
### ✅ Backend (API)
1. Configure o banco MySQL (conforme `application.properties`)
2. Execute a aplicação Spring Boot
3. API disponível por padrão em: `http://localhost:8080`

### ✅ Frontend (React)
O frontend está localizado na pasta: `/frontend`
#### ▶️ Executar
1. Acesse a pasta do frontend: `cd frontend`
2. Instale as dependências: `npm install`
3. Execute o projeto: `npm run dev`
4. Acesse no navegador: `http://localhost:5173`

### 🔁 Proxy para o Backend

O frontend utiliza o proxy do **Vite** para consumir a API localmente, evitando problemas de CORS.

* As chamadas no frontend usam o prefixo `/api`.
* O proxy reescreve `/api` para o endereço do backend (ex.: `/api/eventos` → `/eventos`).
* **Ajuste:** Se a API estiver rodando em outra porta, altere o `target` no arquivo `frontend/vite.config.js`.

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
