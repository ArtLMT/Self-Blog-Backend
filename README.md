# SelfBlog

A personal blogging platform built to document learning journeys, development experiences, and deployment practices.

The project focuses on:
- Writing technical and personal learning articles
- Practicing full-stack development
- Exploring deployment workflows
- Supporting multilingual content (English & Vietnamese)

---

# ✨ Overview

SelfBlog is a minimal full-stack blog system where the owner manages all content while visitors can freely read articles and interact through comments.

The main purpose of this project is not only blogging, but also:
- learning real-world software architecture
- practicing backend/frontend integration
- understanding authentication & authorization
- handling multilingual content
- deploying production-ready applications

---

# 🎯 Target Users

- Personal use
- Friends or readers who want to follow shared experiences
- Developers interested in technical articles and learning notes

---

# 🏗️ Architecture

This project uses a separated architecture:

```text
Frontend (Next.js)
        │
        ▼
Backend API (Spring Boot)
        │
        ▼
PostgreSQL Database
```

- Frontend and Backend are separated repositories
- Backend and Database run inside Docker
- Frontend is deployed independently on Vercel

---

# 🛠️ Tech Stack

## Frontend
- Next.js
- Tailwind CSS
- TypeScript
- next-intl / i18n

## Backend
- Spring Boot
- Spring Security
- JWT Authentication

## Database
- PostgreSQL

## DevOps / Deployment
- Docker
- Docker Compose
- Vercel

---

# 🚀 Features

## Public Features
- View blog posts
- Read articles in English or Vietnamese
- Browse learning experiences and technical content
- View comments

## Authentication Features
- User registration
- User login
- JWT-based authentication

## Comment Features
Authenticated users can:
- leave comments

## Admin Features
Only the blog owner (admin) can:
- Create posts
- Edit posts
- Delete posts
- Manage comments
- Manage published content

---

# 🌐 Multi-language Strategy

The project supports:
- English
- Vietnamese

The multilingual system is separated into 2 different parts:

---

## 1. UI Translation

UI text uses translation files.

Example:

```bash
/messages
  en.json
  vi.json
```

This is suitable for:
- Buttons
- Labels
- Navigation
- Validation messages
- Authentication pages
- Notifications
- Static UI text

Example:

```json
// en.json
{
  "login": "Login"
}
```

```json
// vi.json
{
  "login": "Đăng nhập"
}
```

---

## 2. Blog Content Translation

Blog content uses separate database columns.

Example:

| Column | Description |
|---|---|
| title_en | English title |
| title_vi | Vietnamese title |
| content_en | English content |
| content_vi | Vietnamese content |

Example Entity:

```java
private String titleEn;
private String titleVi;

@Column(columnDefinition = "TEXT")
private String contentEn;

@Column(columnDefinition = "TEXT")
private String contentVi;
```

---


# 📂 Project Structure

## Frontend Repository

```bash
selfblog-frontend/
│
├── app/
├── components/
├── messages/
├── services/
├── lib/
└── public/
```

---

## Backend Repository

```bash
selfblog-backend/
│
├── src/
├── docker/
├── compose.yml
└── Dockerfile
```

---

# 🐳 Docker Setup

Backend and Database run using Docker Compose.

Example:

```yaml
services:
  backend:
    build: .
    ports:
      - "8080:8080"

  postgres:
    image: postgres:17
    environment:
      POSTGRES_DB: selfblog
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
```

---

# 🔐 Authorization Model

| Role | Permissions |
|---|---|
| Guest | Read posts |
| User | Read + comment |
| Admin | Full CRUD access |


# 📖 Learning Goals

This project is created to practice:
- Database design
- Multi-language systems
- Docker & deployment workflows
- Production architecture

---

# ⚙️ Development Setup

## Frontend

```bash
npm install
npm run dev
```

---

## Backend

```bash
./mvnw spring-boot:run
```

---

# 📄 License

This project is created for educational and personal development purposes.