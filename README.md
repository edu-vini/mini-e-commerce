# 🛒 Mini E-commerce

Mini E-commerce é uma aplicação RESTful desenvolvida em Java com Spring Boot, criada para gerenciar produtos e simular funcionalidades básicas de um e-commerce. O projeto serve como base para estudos, experimentação e expansão futura.

## 📋 Índice

- [Características](#-características)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação](#-instalação)
- [Configuração](#-configuração)
- [Executando a Aplicação](#-executando-a-aplicação)
- [Documentação da API](#-documentação-da-api)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Funcionalidades](#-funcionalidades)
- [Autenticação e Autorização](#-autenticação-e-autorização)
- [Modelos de Dados](#-modelos-de-dados)
- [Dados de Teste](#-dados-de-teste)
- [Endpoints da API](#-endpoints-da-api)
- [Tratamento de Erros](#-tratamento-de-erros)
- [Testes](#-testes)
- [Contribuindo](#-contribuindo)
- [Licença](#-licença)

## ✨ Características

- ✅ Autenticação JWT (JSON Web Tokens)
- ✅ Controle de acesso baseado em roles (USER e ADMIN)
- ✅ Gerenciamento de produtos e categorias
- ✅ Sistema de carrinho de compras
- ✅ Gestão de pedidos (orders)
- ✅ Controle de estoque e transações de inventário
- ✅ Documentação interativa com Swagger/OpenAPI
- ✅ Validação de dados com Bean Validation
- ✅ Banco de dados H2 em memória
- ✅ Auditoria de entidades (createdAt, updatedAt)

## 🛠 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.7**
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Segurança e autenticação
- **JWT (jjwt 0.11.5)** - Tokens de autenticação
- **H2 Database** - Banco de dados em memória
- **Lombok** - Redução de boilerplate
- **SpringDoc OpenAPI (Scalar)** - Documentação da API
- **Hibernate Validator** - Validação de dados
- **Maven** - Gerenciamento de dependências

## 📦 Pré-requisitos

- Java 21 ou superior
- Maven 3.6+ (ou use o Maven Wrapper incluído)
- IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code, etc.)

## 🚀 Instalação

1. Clone o repositório:
```bash
git clone <url-do-repositório>
cd Mini-E-commerce
```

2. Compile o projeto:
```bash
./mvnw clean install
```

ou no Windows:
```bash
mvnw.cmd clean install
```

## ⚙️ Configuração

As configurações da aplicação estão no arquivo `src/main/resources/application.properties`:

```properties
spring.application.name=MiniEcommerce

# Banco de dados H2
spring.datasource.url=jdbc:h2:mem:ecommerce
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=admin
spring.datasource.password=admin

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.defer-datasource-initialization=true

# Console H2 (acessível em http://localhost:8080/h2-console)
spring.h2.console.enabled=true

# Documentação Swagger
springdoc.version=2.8.14
scalar.enabled=true
scalar.path=/docs

# Secret para JWT (em produção, use uma chave segura)
api.security.token.secret=QWxhZGRpbjpPcGVuU2VzYW1lU2FtcGxlMTIzNDU2Nzg5
```

## ▶️ Executando a Aplicação

### Usando Maven Wrapper:

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

**Windows:**
```bash
mvnw.cmd spring-boot:run
```

### Usando Maven instalado:
```bash
mvn spring-boot:run
```

### Executando o JAR:
```bash
mvn clean package
java -jar target/MiniEcommerce-0.0.1-SNAPSHOT.jar
```

A aplicação estará disponível em: `http://localhost:8080`

## 📚 Documentação da API

A documentação interativa da API está disponível através do Scalar UI em:

**http://localhost:8080/docs**

Você pode explorar todos os endpoints, testar requisições e ver exemplos de requisições/respostas diretamente no navegador.

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/grupo5/trainee/minsait/MiniEcommerce/
│   │   ├── config/          # Configurações (Swagger)
│   │   ├── controller/      # Controllers REST
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── enums/           # Enumeradores
│   │   ├── exception/       # Tratamento de exceções
│   │   ├── model/           # Entidades JPA
│   │   ├── repository/      # Repositórios Spring Data
│   │   ├── security/        # Configurações de segurança
│   │   └── service/         # Lógica de negócio
│   └── resources/
│       ├── application.properties
│       └── data.sql         # Dados iniciais
└── test/                     # Testes unitários
```

## 🎯 Funcionalidades

### Autenticação
- Login com JWT
- Controle de acesso baseado em roles (ROLE_USER, ROLE_ADMIN)

### Gerenciamento de Usuários
- Criar usuários
- Listar todos os usuários
- Buscar usuário por ID
- Excluir usuário

### Gerenciamento de Produtos
- Criar, listar, buscar, atualizar e excluir produtos
- Associação com categorias
- Controle de estoque
- Preços e informações detalhadas

### Gerenciamento de Categorias
- Criar categorias (com suporte a hierarquia pai/filho)
- Listar todas as categorias
- Buscar categoria por ID
- Atualizar categoria

### Carrinho de Compras
- Criar ou buscar carrinho ativo do usuário
- Adicionar itens ao carrinho
- Atualizar quantidade de itens
- Remover itens
- Limpar carrinho
- Validação de estoque

### Pedidos (Orders)
- Criar pedido a partir do carrinho
- Buscar pedido por ID
- Listar todos os pedidos
- Cancelar pedido
- Cálculo automático de totais (subtotal, desconto, frete)

### Controle de Estoque
- Adicionar produtos ao estoque
- Remover produtos do estoque
- Listar transações de estoque
- Buscar transações por produto
- Rastreamento de movimentações

## 🔐 Autenticação e Autorização

### Login

Para obter um token de autenticação, faça uma requisição POST para `/auth/login`:

```json
{
  "login": "user_teste",
  "senha": "senhauser"
}
```

Resposta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Usando o Token

Inclua o token no header `Authorization` de todas as requisições protegidas:

```
Authorization: Bearer <seu-token>
```

### Roles Disponíveis

- **ROLE_USER**: Acesso básico (carrinho, pedidos)
- **ROLE_ADMIN**: Acesso completo (incluindo gerenciamento de produtos, categorias, estoque)

## 📊 Modelos de Dados

### Principais Entidades

- **User**: Usuários do sistema
- **Product**: Produtos do catálogo
- **Category**: Categorias de produtos (suporta hierarquia)
- **Cart**: Carrinho de compras
- **CartItem**: Itens do carrinho
- **Order**: Pedidos
- **OrderItem**: Itens do pedido
- **InventoryTransaction**: Transações de estoque
- **Address**: Endereço de entrega
- **Review**: Avaliações (modelo presente)
- **Promotion**: Promoções (modelo presente)
- **AuditLog**: Logs de auditoria

## 🧪 Dados de Teste

O arquivo `data.sql` inicializa o banco com dados de exemplo:

### Usuários Padrão

| Login | Senha | Role |
|-------|-------|------|
| `admin_teste` | `senhaadmin` | ROLE_ADMIN |
| `user_teste` | `senhauser` | ROLE_USER |

### Categorias e Produtos

- Categorias: Informática, Acessórios, Gamer, Periféricos, Monitores, etc.
- Produtos: Mouse Gamer, Teclado Mecânico, Monitores, Headsets, etc.
- Carrinhos de exemplo com itens

## 🔌 Endpoints da API

### Autenticação
- `POST /auth/login` - Realizar login e obter token

### Usuários
- `POST /v1/usuarios` - Criar usuário
- `GET /v1/usuarios` - Listar todos os usuários
- `GET /v1/usuarios/{id}` - Buscar usuário por ID
- `DELETE /v1/usuarios/{id}` - Excluir usuário

### Produtos
- `POST /v1/products` - Criar produto
- `GET /v1/products` - Listar todos os produtos
- `GET /v1/products/{id}` - Buscar produto por ID
- `PUT /v1/products` - Atualizar produto
- `DELETE /v1/products/{id}` - Excluir produto

### Categorias
- `POST /v1/categories` - Criar categoria
- `GET /v1/categories` - Listar todas as categorias
- `GET /v1/categories/{id}` - Buscar categoria por ID
- `PUT /v1/categories/{id}` - Atualizar categoria
- `DELETE /v1/categories/{id}` - Excluir categoria

### Carrinho
- `GET /v1/cart/active` - Buscar ou criar carrinho ativo
- `POST /v1/cart/items` - Adicionar item ao carrinho
- `PUT /v1/cart/items/{itemId}` - Atualizar item do carrinho
- `DELETE /v1/cart/items/{itemId}` - Remover item do carrinho
- `DELETE /v1/cart/clear` - Limpar carrinho

### Pedidos
- `POST /v1/order` - Criar pedido
- `GET /v1/order/{id}` - Buscar pedido por ID
- `GET /v1/order` - Listar todos os pedidos
- `DELETE /v1/order/{id}/cancel` - Cancelar pedido

### Estoque
- `POST /v1/inventory/{productId}/add?quantity={qtd}` - Adicionar ao estoque
- `POST /v1/inventory/{productId}/remove?quantity={qtd}` - Remover do estoque
- `GET /v1/inventory` - Listar todas as transações
- `GET /v1/inventory/{productId}` - Buscar transações por produto

## ⚠️ Tratamento de Erros

A aplicação utiliza um `RestExceptionHandler` global que retorna respostas padronizadas:

```json
{
  "message": "Mensagem de erro",
  "timestamp": "2024-01-01T12:00:00",
  "status": 404
}
```

### Códigos de Status HTTP

- `200` - Sucesso
- `201` - Criado com sucesso
- `204` - Sem conteúdo (exclusão bem-sucedida)
- `400` - Requisição inválida
- `401` - Não autorizado
- `403` - Acesso negado
- `404` - Recurso não encontrado
- `409` - Conflito (ex: estoque insuficiente)

## 🧪 Testes

Execute os testes com:

```bash
./mvnw test
```

ou no Windows:
```bash
mvnw.cmd test
```

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto foi criado para fins educacionais e de aprendizado.

## 👥 Autores

- Grupo 5 - Trainee Minsait

## 📞 Suporte

Para dúvidas ou sugestões, abra uma issue no repositório.

---
