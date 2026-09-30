# URL Shortener Web Application

A Spring Boot-based URL shortening service with user authentication, private/public links, expiration support, click tracking, and an admin dashboard.

## Overview

This project lets users shorten long URLs into compact short links, view their own links, and redirect traffic through a secure Spring Boot application. It uses PostgreSQL for persistence, Flyway for schema management, Thymeleaf for the UI, and Spring Security for authentication and authorization.

## Features

- Create short URLs from long original links
- Mark URLs as private or public
- Set expiration dates for short URLs
- Track click counts per short link
- View public URLs on the home page
- View and manage personal URLs after login
- Admin dashboard for browsing all URLs
- User login and logout with Spring Security
- Seeded sample data for quick local testing

## Tech Stack

- Java 25
- Spring Boot 4.0.7
- Spring MVC
- Spring Data JPA
- Spring Security
- PostgreSQL
- Flyway
- Thymeleaf + Bootstrap
- Maven wrapper

## Prerequisites

Before starting the app, make sure you have:

- JDK 25 installed
- Docker Desktop or Docker Engine installed
- A browser to access the app locally

## Local Setup

1. Clone the repository and open it in your IDE or terminal.
2. Start the PostgreSQL container:

```bash
docker compose up -d
```

3. Run the application:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell or Command Prompt, use:

```powershell
mvnw.cmd spring-boot:run
```

4. Open the app in the browser:

```text
http://localhost:8080
```

## Default Login Credentials

The database migration seeds the following accounts:

- Admin: `admin@gmail.com` / `admin`
- User: `siva@gmail.com` / `secret`

## Application URLs

- Home page: `/`
- Login page: `/login`
- My URLs: `/my-urls`
- Redirect short link: `/s/{shortKey}`
- Admin dashboard: `/admin/dashboard`

## Database Configuration

The app is configured to use PostgreSQL on port `5433` with the following defaults:

- Host: `localhost`
- Port: `5433`
- Database: `postgres`
- Username: `postgres`
- Password: `1234`

These values are defined in `src/main/resources/application.properties` and are compatible with the Docker Compose setup in `compose.yml`.

## Flyway Migrations

The project includes schema and sample data migrations under:

- `src/main/resources/db/migration/V1__create_tables.sql`
- `src/main/resources/db/migration/V2__insert_sample_data.sql`
- `src/main/resources/db/migration/V3__update_user_passwords.sql`

## Stopping the App

To stop the local PostgreSQL container:

```bash
docker compose down
```

## Notes

- The app uses `spring.docker.compose.lifecycle-management=start_only`, so Docker Compose is used for the database dependency.
- The base URL is configured as `http://localhost:8080`.
- The default expiry window is set to 30 days in the application properties.

## Project Structure

```text
src/
  main/
    java/        # Spring Boot Java source files
    resources/
      application.properties
      db/migration/  # Flyway SQL scripts
      templates/     # Thymeleaf HTML views
      static/css/    # CSS assets
  test/
    java/        # Test classes
compose.yml       # PostgreSQL container definition
pom.xml           # Maven configuration
mvnw              # Linux/macOS Maven wrapper
mvnw.cmd          # Windows Maven wrapper
```
