# Payment Service API 💳

API de pagamentos de alto desempenho desenvolvida com **Java 21** e **Spring Boot 3**, focada em escalabilidade e manutenibilidade através da **Clean Architecture**.

---

## 🛠️ Tecnologias Utilizadas

* **Java 21** & **Spring Boot 3**
* **MySQL** (Persistência de dados)
* **Docker** & **Docker Compose** (Containerização)
* **OpenAPI/Swagger** (Documentação)
* **Mercado Pago SDK/API** (Gateway de pagamento)

---

## 🏗️ Arquitetura (Clean Architecture)

O projeto utiliza a separação de preocupações para isolar o domínio das tecnologias externas:

* **Core (Domain & UseCases):** Contém as entidades de negócio puras e os casos de uso (regras), totalmente independentes de frameworks.
* **Entrypoints:** Adaptadores de entrada via REST Controllers que orquestram a comunicação com o Core.
* **Dataproviders:** Implementações de infraestrutura para persistência em banco de dados (MySQL/JPA) e integração com o Gateway do Mercado Pago.

---

## 🔹 Funcionalidades

* **Checkout:** Geração dinâmica de links de pagamento (*init_point*) via Mercado Pago.
* **Consulta:** Recuperação detalhada do status e dados da transação através de DTOs.
* **Mock Mode:** Suporte a ambiente de desenvolvimento sem dependência de APIs externas reais.

---

## 🚀 Como Testar (Swagger)

A documentação interativa permite realizar chamadas à API em tempo real:

> **URL:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 🔹 Profiles Spring

O sistema utiliza perfis para alternar entre comportamentos de infraestrutura:

* **dev**: Ativa o `MockMercadoPagoClient`, retornando links de sandbox estáticos.
* **prod**: Ativa o `MercadoPagoClient` real, integrando-se à API oficial.

---

## 🐳 Rodando com Docker

A aplicação já conta com orquestração completa para subir o ambiente (App + Banco):

```bash
# 1. Gerar o pacote da aplicação
mvn clean package -DskipTests

# 2. Subir os containers (MySQL e App)
docker-compose up --build
