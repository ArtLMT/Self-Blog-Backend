# Personal Tech Blog Project

## 1. Project Overview

### Project Name
Self-Blog

---

## 2. Description

This project is a personal blog platform built to document learning experiences, technical knowledge, deployment practices, and development journeys.

The blog mainly focuses on:
- Software development learning
- Backend and frontend experiences
- Deployment and DevOps practices
- Personal technical notes and project reflections

The platform supports bilingual content:
- English
- Vietnamese

This project is also used as a sandbox environment to practice:
- Full-stack development
- Authentication and authorization
- Deployment workflows
- CI/CD concepts
- Cloud hosting

---

## 3. Target Users

### Primary User
- The project owner (admin)

### Secondary Users
- Friends
- Recruiters
- Developers
- Readers interested in software engineering and deployment topics

---

# 4. Project Goals

The goals of this project are:

- Practice full-stack web development
- Learn deployment workflows
- Build a production-ready personal portfolio
- Improve frontend and backend integration skills
- Practice authentication and authorization
- Create a platform to document personal learning experiences

---

# 5. Technology Stack

## Frontend
- Next.js
- Tailwind CSS

## Backend
- Spring Boot

## Database
- PostgreSQL

## Deployment
- Vercel (Frontend)

---

# 6. Functional Requirements

## 6.1 Public Features

### Guest Users Can:
- View blog posts
- Read post details
- Switch between English and Vietnamese
- Browse published articles
- View responsive layouts on multiple devices

---

## 6.2 Authentication Features

### Registered Users Can:
- Login to the system
- Leave comments or messages on blog posts

---

## 6.3 Admin Features

Only the admin (project owner) can:
- Create blog posts
- Edit blog posts
- Delete blog posts
- Publish or unpublish posts
- Manage comments/messages

---

## 6.4 Language Features

The system must support:
- English language
- Vietnamese language

Users can switch languages dynamically.

---

# 7. Non-Functional Requirements

## Performance
- Fast page loading
- Responsive UI

## Security
- JWT authentication
- Protected admin APIs
- Role-based authorization

## Usability
- Clean and minimal UI
- Mobile responsive design

## Maintainability
- Modular frontend structure
- RESTful backend APIs
- Clean code practices

---

# 8. Suggested Database Entities

## Users
Stores user account information.

Fields:
- id
- username
- password
- role

---

## Posts
Stores blog articles.

Fields:
- id
- slug
- title_en
- title_vi
- content_en
- content_vi
- published
- created_at
- updated_at

---

## Comments
Stores user comments/messages.

Fields:
- id
- user_id
- post_id
- content
- created_at

---

# 9. Future Improvements

Possible future enhancements:
- Markdown editor
- Rich text editor
- Image upload support
- SEO optimization
- Search functionality
- Tags and categories
- Analytics dashboard
- Email newsletter
- Dark mode
- CI/CD pipeline
- Docker support

---

# 10. Deployment Plan

## Frontend
Deploy using:
- Vercel

## Backend
Possible deployment platforms:
- Railway
- Render

## Database
Possible cloud database providers:
- Neon
- Supabase PostgreSQL

---

# 11. Development Timeline

## Week 1
- Project setup
- Database design
- Authentication
- Basic CRUD APIs
- Frontend layout
- Language switch
- Initial deployment

## Week 2
- UI improvements
- Comment system
- Responsive optimization
- Production environment setup
- Deployment polishing
- Testing and bug fixes

---

# 12. Expected Outcomes

After completing the project, the developer should:
- Understand full-stack development workflow
- Gain deployment experience
- Learn authentication implementation
- Improve frontend/backend integration skills
- Build a professional personal portfolio project

