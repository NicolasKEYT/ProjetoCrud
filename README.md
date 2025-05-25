# Projeto - Sistema Simplificado de Gestão de OKRs

## Grupo:

1. 10418047 - Nicolas Gonçalves Santos
2. 10425609 - Vinicius Gutierrez Gomes
   
## Requisitos do Projeto 

**Requisitos para o Back-end (Java Spring Boot):**

   •Criar um CRUD para Objetivos com atributos como título, descrição e porcentagem de conclusão geral.
   
   •Criar um CRUD para Resultados-Chave (KR), relacionados a um objetivo específico, incluindo descrição, meta e porcentagem de conclusão.
   
   •Criar um CRUD para Iniciativas, relacionadas a um resultado-chave específico, com atributos como título, descrição e porcentagem individual de conclusão.
   
   •Implementar lógica automática para calcular a porcentagem de conclusão do KR com base nas iniciativas relacionadas.
   
   •Implementar lógica automática para calcular a porcentagem de conclusão do Objetivo com base na média dos KRs relacionados.
   
   •Criar endpoints REST claros e padronizados para comunicação com o front-end.

•Bônus: Implementar documentação automática dos endpoints utilizando Swagger (Springdoc/OpenAPI).

**Requisitos para o Front-end (NextJS):**

   •Construir uma aplicação SPA (Single Page Application) simples para consumir os dados da API.
   
   •Permitir que o usuário realize operações de CRUD nos objetivos, KRs e iniciativas.
   
   •Exibir claramente as relações hierárquicas entre objetivos, KRs e iniciativas.
   
   •Atualizar automaticamente as visualizações das porcentagens após criação, atualização ou exclusão de iniciativas ou KRs.

   
## 1. Visão geral
Nesta fase inicial, utilizamos Spring Data JPA com Hibernate para garantir criação automática de tabelas (DDL auto-update) e fácil mapeamento objeto-relacional. O banco PostgreSQL hospedado no Supabase foi escolhido pela praticidade de um serviço gerenciado; o driver JDBC e as credenciais foram configurados no application.properties. Adotamos Lombok para reduzir boilerplate em entidades e serviços, e TestRestTemplate nos testes integrados, garantindo chamadas HTTP reais ao servidor. Para testes manuais e CI local, usamos Postman (com variáveis de ambiente) e Maven (mvn clean test). 
o JPA/Hibernate gerencia o ciclo de vida das entidades, realiza o mapeamento objeto-relacional, e executa consultas via métodos de JpaRepository como save(), findAll(), findById() e deleteById() sem necessitar de SQL explícito. Para suportar a lógica de cálculo de percentuais, criamos métodos customizados como findByResultadoChaveId e findByObjetivoId, isolando consultas específicas na camada de repositório e mantendo os serviços focados apenas na lógica de negócio e recálculo em cascata.

## Sprints Finais: Front-end & Integração

### Visão Geral
Nas etapas finais, implementamos toda a camada de interface em Next.js (App Router) consumindo nossa API REST em Spring Boot. O front-end ficou responsável por realizar operações CRUD (create, read, update, delete) para Objetivos, KRs e Iniciativas de forma interativa e reativa, garantindo consistência imediata de dados graças ao cache e revalidação automática do SWR.

---

### Tecnologias Principais
- **Next.js** (v13+ App Router) & **React**  
  – Client Components (`'use client'`), hooks (`useState`, `useEffect`, `useRouter`, `useParams`)  
- **Tailwind CSS**  
  – Classes utilitárias para layout responsivo e design consistente  
- **SWR**  
  – Fetch automático, cache stale-while-revalidate, `mutate()` para revalidação pós-PUT/DELETE  
- **Spring Boot** + **Spring Data JPA**  
  – Endpoints REST em `/objetivos`, `/krs`, `/iniciativas`  
- **PostgreSQL (Supabase)**  
  – Banco gerenciado para persistência de dados  
- **Lombok**, **Maven**, **TestRestTemplate**  
  – Redução de boilerplate, build/testes e integração contínua

---

### Fluxo de Funcionamento

1. **Listagem**  
   - `GET /objetivos | /krs | /iniciativas` via SWR  
   - Exibição em cards: título, descrição, contexto hierárquico (KR→Objetivo, Iniciativa→KR→Objetivo)

2. **Criação**  
   - Páginas dedicadas (`/objetivos/new`, `/krs/new`, `/iniciativas/new`) com formulários controlados  
   - `POST` JSON → revalidação de cache e redirecionamento à listagem

3. **Edição**  
   - Rotas dinâmicas (`/[recurso]/[id]`) que fazem `GET /{recurso}/{id}`, pré-preenchem o form (em `useEffect`), e enviam `PUT /{recurso}/{id}` mantendo todos os campos

4. **Inline Editing**  
   - Na listagem de Iniciativas: `<input onBlur>` dispara `PUT /iniciativas/{id}` e `mutate()` em cascata (`iniciativas`, `krs`, `objetivos`)

5. **Exclusão**  
   - Botões “Excluir” chamam `DELETE /{recurso}/{id}` e revalidam a lista

---

### Conexão Front-Back

- **Base URL** definida em `.env.local`:
  ```bash
  NEXT_PUBLIC_API_BASE_URL=https://<seu-codespace>-8080.app.github.dev

