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

## ⚙️ Decisões Técnicas Tomadas
- Java 17: escolhido por ser a versão LTS mais estável e moderna, garantindo suporte a longo prazo e acesso a novos recursos da linguagem.
- Spring Boot: adotado para acelerar o desenvolvimento de APIs REST, oferecendo integração nativa com Spring Web, Data JPA e Bean Validation.
- Spring Data JPA: facilita o acesso e manipulação de dados, reduzindo boilerplate e permitindo foco nas regras de negócio.
- MySQL: banco relacional amplamente utilizado, com boa performance, suporte a transações e fácil integração com Docker.
- Bean Validation: utilizado para garantir integridade dos dados de entrada (ex.: validação de e-mail, campos obrigatórios).
- Maven: escolhido como gerenciador de dependências e build por sua robustez e ampla adoção na comunidade Java.
- React (com Vite): React foi escolhido pela flexibilidade e comunidade ativa; Vite por oferecer build rápido e ambiente de desenvolvimento otimizado.
- Axios: biblioteca simples e eficiente para consumo da API REST.
- React Router DOM: utilizado para navegação entre telas de forma declarativa e organizada.
- Clean Architecture: adotada para garantir separação de responsabilidades, facilitar testes e permitir evolução futura sem acoplamento excessivo.
- Docker + Docker Compose: escolhido para padronizar o ambiente de execução, simplificar deploy e garantir que backend, frontend e banco rodem de forma integrada.

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

## 🖥️ Como executar com Docker (backend + frontend + MySQL)
### Pré-requisitos
- Docker Desktop (Windows) ou Docker Engine (Linux) instalado e rodando.

### Subir a aplicação
1. Na raiz do projeto (`eventhub/`), execute:  `docker compose up --build`
2. A aplicação ficará disponível em:
    - Frontend: http://localhost:5173
    - Backend (API): http://localhost:8080

## 🖥️ Como executar o projeto localmente
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

## 🔁 Proxy
### Backend:
O frontend utiliza o proxy do **Vite** para consumir a API localmente, evitando problemas de CORS.
* As chamadas no frontend usam o prefixo `/api`.
* O proxy reescreve `/api` para o endereço do backend (ex.: `/api/eventos` → `/eventos`).
* **Ajuste:** Se a API estiver rodando em outra porta, altere o `target` no arquivo `frontend/vite.config.js`.

### /api no Docker:
- No Docker, o frontend é servido via Nginx e mantém o padrão de chamadas com `/api`, evitando problemas de CORS:
    1. O frontend chama: `/api/eventos`
    2. O Nginx redireciona para o backend: `http://backend:8080/eventos`
       ✅ Assim, não é necessário alterar o código do frontend para rodar em Docker.

## 🧪 Testes de API (Postman)
O projeto possui uma collection do Postman com todas as requisições da API, incluindo testes automatizados de validação e erro.

### 📂 Localização
Os arquivos estão disponíveis na pasta: `/postman`

- EventHub.postman_collection.json
- EventHub.postman_environment.json

Dica: a pasta "Testes de Erro / Validação" pode ser executada integralmente para validar automaticamente as regras da API.

### ▶️ Como importar e executar
1. Abra o Postman
2. Clique em Import
3. Importe os dois arquivos localizados na pasta /postman
4. Selecione o environment EventHub
5. Verifique a variável baseUrl (exemplo): http://localhost:8080
6. Execute as requisições individualmente ou por pasta

### 🧪 Testes implementados
Além dos testes via Postman, o projeto também possui **testes automatizados em Java** (ex.: JUnit/Mockito) cobrindo:
- **Use cases (camada Application / core.usecase)**
    - `CriarEventoUseCaseImplTest`
    - `CriarParticipanteUseCaseImplTest`
    - `ComprarIngressoUseCaseImplTest`
- **Controllers (camada Infra / Web / infra.web.controller)**
    - `EventoControllerTest`
    - `ParticipanteControllerTest`
    - `IngressoControllerTest`

## 📬 Status Codes
- 200 OK
- 201 Created
- 204 No Content
- 400 Bad Request
- 404 Not Found
- 409 Conflict

---
Projeto desenvolvido para fins de estudo e portfólio.
