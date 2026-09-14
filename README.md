# Inventory Manager

A console-based inventory manager written in Java. This is a study project. I'm using it to practice OOP, layered architecture, and unit testing while also training my English (that's why classes, variables, and commit messages are in English, even though earlier commits still have some Portuguese in them).

## Why this project

I wanted something simple enough to actually finish and iterate on, but real enough to practice concepts: interfaces, dependency injection, DTOs, custom exceptions, and testing. So instead of jumping straight to "the complete version," I built it in stages starting with a plain CRUD in a single class, then gradually splitting it into layers as I learned why each piece exists.

## Stack

- Java 21
- Maven
- JUnit 5

## Structure

```
src/
├── main/java/org/example/
│   ├── dto/            # ProductDTO
│   ├── exception/      # ProductNotFoundException, InvalidProductException
│   ├── mapper/         # Entity <-> DTO conversion
│   ├── model/          # Product, Category
│   ├── repository/     # IProductRepository + two implementations
│   ├── service/        # Business logic and validation
│   └── Main.java
└── test/java/org/example/service/
```

## What it does

- Add, update, remove, search, and list products
- Partial updates (you can update only quantity, only price, or both)
- Input validation (no negative quantity/price, no empty name, category required)
- Persists to a local CSV file, so data survives between runs

## A few design decisions (and why)

- `IProductRepository` is an interface with two implementations: `MemoryProductRepository` (used in tests, no disk I/O involved) and `ProductRepositoryFile` (the real one, reads/writes a CSV). `ProductService` only knows about the interface — the actual implementation gets injected through the constructor.
- DTOs exist so the console layer never touches the domain entity directly.
- CSV persistence is done by hand (`BufferedReader`/`BufferedWriter`), no library. It's not how I'd do it in a real project, but the point here was learning file I/O, not shipping something production-ready.

## Running it

Easiest way: open the project in your IDE and run the `Main` class directly.

If you prefer the terminal:
```bash
mvn compile exec:java -Dexec.mainClass="org.example.Main"
```

## Running the tests

```bash
mvn test
```

I'm actively working on increasing test coverage right now the service layer is reasonably covered, but I'm still filling in edge cases (boundary values, combinations of null parameters in the update method, etc).

## What's next

- More tests, especially boundary/edge cases
- Swap CSV for a real database (SQLite via JDBC) just to learn JDBC
- Maybe wrap the service layer in a small REST API later on

## Author

d3v-alves
