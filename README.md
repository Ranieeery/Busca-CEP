# Busca CEP API

A Spring Boot application that manages a database of Brazilian postal codes (CEPs) and provides a REST API for querying them. Currently focused on the state of Minas Gerais with approximately 80,000 postal codes, with plans to expand to cover all Brazilian states (approximately 890,000 postal codes).

## Features

- REST API for querying Brazilian postal codes (CEPs)
- MySQL database integration
- JUnit test coverage
- Status endpoint for monitoring setup progress
- Initial focus on Minas Gerais state postal codes

## Technology Stack

- Java 17
- Spring Boot 3
- MySQL Database
- Spring Data JPA
- Lombok
- Maven
- JUnit for testing

## Prerequisites

- Java 17 or higher
- MySQL Server
- Maven

## Installation

1. Install dependencies:

```bash
mvn clean install
```

2. Configure the MySQL database:

```sql
CREATE DATABASE correios;
```

3. Run the application:

```bash
mvn spring-boot:run
```

## Usage

1. Start the Spring Boot application

2. The initial setup will take approximately 1 minute to complete

3. Check the application status at: `http://localhost:8080/status`

4. Once setup is complete, you can query postal codes via the API

Note: Attempting to query before setup completion will return a 503 error.

## API Documentation

The API documentation is available through Swagger UI at:

- `http://localhost:8080/swagger-ui.html`

You can also access the OpenAPI specification at:

- `http://localhost:8080/v3/api-docs`

These endpoints provide interactive documentation for all available API endpoints, request/response models, and testing capabilities.

## Testing

Run the unit tests using:

```bash
mvn test
```
