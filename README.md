# E-Commerce Backend

REST API built with Spring Boot, featuring product management with image upload and stateless JWT authentication with role-based access (`ADMIN` / user).

## Features

- Product CRUD with image upload and keyword search
- JWT authentication (register and login), passwords hashed with BCrypt
- Role-based authorization: public read access, `ADMIN`-only writes
- Stateless sessions and CORS support for a separate frontend

## Tech Stack

Java, Spring Boot, Spring Security, JWT, Spring Data JPA, Hibernate, H2, Maven

## Run Locally

```bash
git clone https://github.com/sanvi04/Ecommerce-Project.git
cd Ecommerce-Project
export JWT_SECRET="your-random-secret-min-32-chars"   # PowerShell: $env:JWT_SECRET="..."
./mvnw spring-boot:run
```

The API runs at `http://localhost:8080`.

## API

| Method | Endpoint | Access |
|--------|----------|--------|
| POST | `/api/auth/register`, `/api/auth/login` | Public |
| GET | `/api/products`, `/api/product/{id}`, `/api/products/search?keyword=` | Public |
| POST / PUT / DELETE | `/api/product`, `/api/product/{id}` | ADMIN (`Authorization: Bearer <token>`) |