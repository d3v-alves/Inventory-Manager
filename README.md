# Inventory Manager

Console-based inventory management system built in Java as a study project, focused on practicing Object-Oriented Programming, layered architecture, and unit testing.

## About the project

This project started as a simple console CRUD and evolved step by step into a more professional structure, applying concepts commonly used in real-world Java applications:

- Layered architecture (Model, DTO, Mapper, Repository, Service)
- Dependency Inversion via interfaces
- Dependency Injection (constructor-based)
- Custom exceptions for business rule validation
- File persistence (CSV)
- Unit testing with JUnit 5

## Tech stack

- Java 21
- Maven
- JUnit 5

## Project structure

```
src/
├── main/java/org/example/
│   ├── model/         # Domain entities (Product, Category)
│   ├── dto/            # Data Transfer Objects (ProductDTO)
│   ├── mapper/         # Entity <-> DTO conversion
│   ├── exception/      # Custom exceptions (business rule violations)
│   ├── repository/     # Data access layer (in-memory and file-based implementations)
│   ├── service/        # Business logic
│   └── Main.java       # Console entry point
└── test/java/org/example/
    └── service/         # Unit tests
```

## Features

- Add product (with input validation)
- Update product (partial update: quantity, price, or both)
- Remove product
- Search product by name
- List all products
- Data persisted to a local CSV file between runs

## Architecture notes

- `IProductRepository` defines the contract for data access. Two implementations are provided:
  - `MemoryProductRepository`: in-memory storage, used mainly for fast and isolated unit tests
  - `ProductRepositoryFile`: persists data to a CSV file on disk
- `ProductService` depends only on the `IProductRepository` interface, not on a concrete implementation — the repository is injected via constructor.
- DTOs decouple the console/presentation layer from the domain entity, and are immutable by design.
- Custom exceptions (`ProductNotFoundException`, `InvalidProductException`) represent expected business errors, handled explicitly at the entry point.

## How to run

```bash
mvn compile exec:java -Dexec.mainClass="org.example.Main"
```

Or simply run the `Main` class from your IDE.

## How to run tests

```bash
mvn test
```

Or run `ProductServiceTest` directly from the IDE (with coverage support if using IntelliJ IDEA).

## Status

Actively evolving as a learning project. Planned next steps include:

- Increasing test coverage (edge cases, boundary values)
- Migrating persistence to a relational database (JDBC)
- Possibly exposing the service layer through a REST API (Spring Boot)

## Author

d3v-alves
