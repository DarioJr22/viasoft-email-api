# 📧 Viasoft Email API

**Viasoft Email API** é uma API desenvolvida para o desafio proposto pela Viasoft, permitindo o envio de e-mails via uma interface RESTful.  

## 📌 Tecnologias Utilizadas

- **Java 17** - Linguagem principal do projeto.  
- **Spring Boot** - Framework para construção de aplicações Java.  
- **Maven** - Gerenciador de dependências e build do projeto.  
- **Docker & Docker Compose** - Para facilitar a execução da aplicação.  
- **Lombok** - Simplificação do código eliminando código boilerplate.  

## 🚀 Funcionalidades  

✅ Recebe requisições HTTP para envio de e-mails.  
✅ Suporta múltiplas plataformas de envio (AWS, OCI).  
✅ Simula o envio exibindo os dados no console.  

## 📂 Estrutura do Projeto  

```
viasoft-email-api/
│── src/main/java/com/viasoft/emailapi/
│   ├── controller/        # Controlador REST para envio de e-mails
│   ├── service/           # Lógica de envio de e-mails
│   ├── model/             # Representação dos dados da requisição
│   ├── EmailApiApplication.java # Classe principal do Spring Boot
│── src/main/resources/
│   ├── application.properties # Configurações da aplicação
│── docker-compose.yml      # Arquivo para rodar a aplicação via Docker Compose
│── Dockerfile              # Configuração para criar a imagem Docker
│── pom.xml                 # Dependências do Maven
│── README.md               # Documentação do projeto
```

---

## ⚙️ Configuração  

A aplicação permite configurar a plataforma de envio de e-mails via variável de ambiente.  

### 🔧 Variáveis de Ambiente  

No arquivo `application.properties`, a variável `mailPlatform` define qual serviço de e-mail será utilizado:  

```
mailPlatform=AWS  # ou OCI
```

Para sobrescrever essa variável ao rodar com Docker Compose, modifique o arquivo `docker-compose.yml` (veja abaixo).  

---

## 📡 Endpoints da API  

### ➤ **1. Enviar um e-mail**  
- **URL:** `POST /emails/send`  
- **Content-Type:** `application/json`  
- **Corpo da Requisição:**  

```json
{
  "emailDestinatario": "luiz@gmail.com",
  "nomeDestinatario": "Luiz Felipe",
  "remetente": "LuizFelipe@mail.com",
  "assunto": "Novo E-mail",
  "conteudo": "Conteúdo do e-mail"
}
```

- **Resposta de Sucesso (`200 OK`)**:  

```json
{
  "mensagem": "E-mail enviado com sucesso!"
}
```

---

## ▶️ Como Executar  

### 🔹 1️⃣ Executar Localmente (Sem Docker)  

**Pré-requisitos:**  
✔️ Java 17+ instalado  
✔️ Maven instalado  

1️⃣ Clone o repositório:  

```bash
git clone https://github.com/DarioJr22/viasoft-email-api.git
cd viasoft-email-api
```

2️⃣ Compile e execute a aplicação:  

```bash
./mvnw clean install
./mvnw spring-boot:run
```

3️⃣ A API estará disponível em:  

```
http://localhost:8080
```

---

### 🔹 2️⃣ Executar com Docker Compose  

**Pré-requisitos:**  
✔️ Docker e Docker Compose instalados  

1️⃣ Clone o repositório:  

```bash
git clone https://github.com/DarioJr22/viasoft-email-api.git
cd viasoft-email-api
```

2️⃣ Crie e inicie os containers com **Docker Compose**:  

```bash
docker-compose up --build
```

📌 Isso criará e iniciará o container da API automaticamente.  

3️⃣ Para parar a aplicação:  

```bash
docker-compose down
```

---

## 🛠 **Testando a API**  

### ✅ Usando `cURL`  

```bash
curl -X POST http://localhost:8080/emails/send \
  -H "Content-Type: application/json" \
  -d '{
        "emailDestinatario": "luiz@gmail.com",
        "nomeDestinatario": "Luiz Felipe",
        "remetente": "LuizFelipe@mail.com",
        "assunto": "Novo E-mail",
        "conteudo": "Conteúdo do e-mail"
      }'
```

### ✅ Usando `Postman`  

1️⃣ Abra o **Postman**  
2️⃣ Crie uma nova requisição **POST**  
3️⃣ Insira a URL: `http://localhost:8080/emails/send`  
4️⃣ Vá para a aba **Body → raw → JSON** e insira o JSON do exemplo  
5️⃣ Clique em **Send**  

---

## 📌 **Docker Compose Configuração (`docker-compose.yml`)**  

```yaml
version: '3.8'

services:
  email-api:
    build: .
    container_name: viasoft-email-api
    ports:
      - "8080:8080"
    environment:
      - mailPlatform=AWS
    restart: always
```

Se desejar mudar a plataforma de e-mail, edite `mailPlatform` para `OCI`.  

---

## 👥 Contribuição  

Contribuições são bem-vindas! Para contribuir:  

1. Faça um **fork** do repositório  
2. Crie uma **branch** (`git checkout -b minha-feature`)  
3. Faça **commit** das suas alterações (`git commit -m 'Minha feature'`)  
4. Faça um **push** (`git push origin minha-feature`)  
5. Abra um **Pull Request**  

---
📌 _Última atualização: Março de 2025_  
