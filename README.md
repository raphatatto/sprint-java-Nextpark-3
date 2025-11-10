
Bem-vindo ao **Nextpark**, uma aplicação web construída com Spring Boot para administrar um pátio de motos. O sistema oferece autenticação baseada em perfis, cadastro de clientes, gestão de vagas e motos, além de um histórico completo de movimentações.

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-ED8B00?logo=java&logoColor=white" alt="Java 17" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?logo=springboot&logoColor=white" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Build-Maven_CI-blue?logo=apachemaven&logoColor=white" alt="Maven" />
</p>

---

## 📚 Sumário
- [Integrantes](#-integrantes)
- [Links Personalizáveis](#-links)
- [Principais Funcionalidades](#-principais-funcionalidades)
- [Arquitetura e Stack](#-arquitetura-e-stack)
- [Pré-requisitos](#️-pré-requisitos)
- [Configuração do Banco e Dados Iniciais](#️-configuração-do-banco-e-dados-iniciais)
- [Executando a Aplicação](#-executando-a-aplicação)
- [Executando os Testes](#-executando-os-testes)
- [Fluxo de Acesso](#-fluxo-de-acesso)
- [Estrutura do Projeto](#-estrutura-do-projeto)

---

##  Integrantes
- Tiago Ribeiro Capela — RM 558021
- Raphaela Oliveira Tatto — RM 554983

##  Links Personalizáveis
Preencha os campos abaixo com os links mais importantes do projeto:
- 🔗 Link video: `https://youtu.be/YqRqRSi7-fU`
- 🔗 Link deploy: `nextpark-sprint-rm554983.azurewebsites.net`


## Principais Funcionalidades
- **Autenticação e controle de acesso por perfil** (cliente ou gerente), com gerenciamento automático do usuário administrador.
- **Cadastro de clientes** via tela pública `/register`, com senha armazenada usando BCrypt.
- **Gestão de vagas** exclusiva para gerentes, incluindo criação, edição, listagem e exclusão.
- **Gestão de motos** acessível a clientes (suas próprias motos) e gerentes (todas as motos), com alocação e desalocação de vagas.
- **Histórico de movimentações** para acompanhar todas as ações executadas sobre cada moto.
- **Promoção de usuários** para o papel de gerente diretamente pela interface administrativa.
- **Tratamento global de erros** para feedback amigável em violações de regras de negócio ou restrições de banco de dados.
- **Pipeline de deploy** configurado no Azure DevOps.

##  Arquitetura e Stack
- **Linguagem:** Java 17
- **Frameworks principais:** Spring Boot (Web, Data JPA, Security), Thymeleaf, Flyway e Lombok
- **Banco de dados:** Azure SQL Database (SQL Server) com versionamento de esquema e dados via Flyway
- **Front-end:** páginas Thymeleaf com assets estáticos em `/static`

##  Pré-requisitos
1. **Java 17** instalado e configurado no `PATH`.
2. **Maven 3.9+** ou uso do wrapper (`mvnw`).
3. **Azure SQL Database** acessível (porta 1433 liberada). Configure as variáveis de ambiente:
   - `AZURE_SQL_URL`
   - `AZURE_SQL_USER`
   - `AZURE_SQL_PAS`
   - `AZURE_SQL_SCHEMA` *(opcional, padrão `dbo`)*
4. Permissão no banco para executar as migrações Flyway no schema configurado.

##  Configuração do Banco e Dados Iniciais
1. Defina as variáveis de ambiente com as credenciais do Azure SQL antes de iniciar a aplicação ou executar o Flyway.
2. Atualize o seed do usuário gerente em `src/main/resources/db/migration/V2__seed_usuarios.sql` com um hash BCrypt válido.
   - Gere o hash executando `new BCryptPasswordEncoder().encode("suaSenha")` em um REPL Spring ou classe utilitária.
3. Execute as migrações:
   ```bash
   ./mvnw flyway:migrate
   ```
   Esse processo cria as tabelas, o usuário gerente inicial e vagas de exemplo definidos nos scripts `V1__baseline.sql` e `V3__seed_vagas.sql`.

##  Executando a Aplicação
1. Compile o projeto (opcional, mas recomendado na primeira execução):
   ```bash
   ./mvnw clean package
   ```
2. Inicie o servidor Spring Boot:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Acesse a aplicação em [http://localhost:8080](http://localhost:8080).

###  Variáveis de Ambiente Locais
**Unix-like:**
```bash
export AZURE_SQL_URL="jdbc:sqlserver://<servidor>.database.windows.net:1433;database=<nome>;encrypt=true;trustServerCertificate=false;loginTimeout=30"
export AZURE_SQL_USER="<usuario>"
export AZURE_SQL_PASS="<senha>"
export AZURE_SQL_SCHEMA="dbo" # opcional
```

**Windows (PowerShell):**
```powershell
$Env:AZURE_SQL_URL = "jdbc:sqlserver://<servidor>.database.windows.net:1433;database=<nome>;encrypt=true;trustServerCertificate=false;loginTimeout=30"
$Env:AZURE_SQL_USER = "<usuario>"
$Env:AZURE_SQL_PASS = "<senha>"
$Env:AZURE_SQL_SCHEMA = "dbo" # opcional
```

###  Configuração no Azure App Service
1. No portal do Azure, abra o App Service da aplicação.
2. Vá em **Configurações ➜ Variáveis de Aplicativo** e adicione as chaves `AZURE_SQL_URL`, `AZURE_SQL_USER`, `AZURE_SQL_PASSWORD` e, se necessário, `AZURE_SQL_SCHEMA`.
3. Use a URL no formato `jdbc:sqlserver://<servidor>.database.windows.net:1433;database=<nome>;encrypt=true;trustServerCertificate=false;loginTimeout=30`.
4. Salve e reinicie o App Service para aplicar as alterações.

##  Executando os Testes
Execute todos os testes unitários e de integração com Maven:
```bash
./mvnw test
```

## Fluxo de Acesso
- **Login:** `http://localhost:8080/login`
  - O usuário gerente criado via Flyway (por exemplo, `gerente` + senha configurada) possui acesso completo às áreas administrativas.
- **Cadastro de clientes:** `http://localhost:8080/register`
  - Novos usuários recebem automaticamente o perfil `CLIENTE` e podem cadastrar suas próprias motos.
- **Promoção a gerente:** após logado como gerente, utilize a ação `POST /usuario/{id}/promover` para elevar um cliente a gerente.

##  Estrutura do Projeto
```
├── src/main/java
│   ├── br/com/fiap/nextpark/config        # Configurações de segurança e recursos estáticos
│   ├── br/com/fiap/nextpark/controller    # Controladores MVC (auth, motos, vagas, usuários)
│   ├── br/com/fiap/nextpark/model         # Entidades e enums do domínio
│   ├── br/com/fiap/nextpark/repository    # Repositórios Spring Data
│   ├── br/com/fiap/nextpark/security      # Usuários, roles e UserDetailsService
│   └── br/com/fiap/nextpark/service       # Regras de negócio e integrações com repositórios
├── src/main/resources
│   ├── db/migration                       # Scripts Flyway (schema + seeds)
│   ├── templates                          # Páginas Thymeleaf
│   └── static                             # CSS/JS
└── pom.xml                                # Dependências e plugins Maven
```

---

> **Dica:** personalize este README adicionando prints da aplicação, instruções específicas de deploy e quaisquer integrações adicionais utilizadas pelo time.

