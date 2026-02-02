# Sales Simulator

## Table of Contents
- [Project Goal](#project-goal)
- [Main Technologies](#main-technologies)
- [API Documentation](#api-documentation)
    - [Price Query Endpoint](#price-query-endpoint)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Compiling and Installation](#compiling-and-installation)
    - [Running the Application](#running-the-application)
- [Testing](#testing)
    - [Running All Tests](#running-all-tests)
    - [Running Specific Tests](#running-specific-tests)
- [Challenges and Improvements](#challenges-and-improvements)
    - [Challenges](#challenges)
    - [Potential Improvements](#potential-improvements)

## Project Goal
The goal of this project is to provide a REST API that allows querying the applicable price for a specific product, brand, and date. The application manages a set of prices that have a priority and an application date range. When multiple prices are applicable for a given date, the one with the highest priority is selected.

The project follows **Hexagonal Architecture** (Ports and Adapters) to ensure a clean separation between domain logic and infrastructure/delivery details.

It's a simple, one-endpoint application, to showcase the use of Hexagonal Architecture and all its components. 

## Main Technologies
- **Java 21** (standard for modern Spring Boot applications)
- **Spring Boot 3**: Main framework for the application and dependency injection.
- **Spring Data JPA**: For database interaction.
- **H2 Database**: An in-memory database used for data storage and testing.
- **Lombok**: To reduce boilerplate code (getters, setters, builders, etc.).
- **JUnit 5 & Mockito**: For unit and integration testing.
- **Maven**: Project management and build tool.
- **OpenAPI Generator**: For contract-first API development and automated generation of controller interfaces and models.

## API Documentation

The project follows an **API-First** approach using **OpenAPI 3.0**. The API specification can be found in `controller/src/main/resources/sales-simulator.yaml`.

### Price Query Endpoint
Retrieves the applicable price for a given product, brand, and date.

- **URL**: `/v1/sales`
- **Method**: `GET`
- **Query Parameters**:
    - `applicationDate` (ISO-8601 OffsetDateTime, e.g., `2020-06-14T10:00:00Z`): The date for which the price is being queried.
    - `productId` (String): The identifier of the product.
    - `chainId` (String): The identifier of the brand/chain.

- **Response**:
    - **Success (200 OK)**:
        ```json
        {
          "brandId": "1",
          "productId": "35455",
          "startDate": "2020-06-14T00:00:00Z",
          "endDate": "2020-12-31T23:59:59Z",
          "price": 35.50,
          "currency": "EUR"
        }
        ```
    - **Bad Request (400)**: Returned if any required parameter is missing or invalid.
    - **Not Found (404)**: Returned if no applicable price is found for the given parameters.

## Getting Started

### Prerequisites
- **Java 21**
- **Maven 3.x**

### Compiling and Installation
To compile the project and install it in your local Maven repository, run:

```bash
mvn clean install
```

This command performs several steps:
1. **Code Generation**: Generates the controller interfaces and models from the OpenAPI specification in the `controller` module.
2. **Compilation**: Compiles the source code of all modules.
3. **Unit & Integration Testing**: Executes all tests to ensure project stability.
4. **Packaging**: Creates JAR files for each module.
5. **Installation**: Installs the generated artifacts into your local `.m2` repository.

Alternatively, if you only want to compile the code without running tests or installing:

```bash
mvn clean compile
```

### Running the Application
The main entry point is the `BootApplication` class in the `boot` module. You can start the application using Maven:

```bash
mvn spring-boot:run -pl boot
```

Once started, the API will be available at `http://localhost:8080`.

## Testing
The project includes both unit and integration tests.

### Running All Tests
To run all tests across all modules:

```bash
mvn test
```

### Running Specific Tests
- **Integration Tests**: Focus on the end-to-end flow.
  ```bash
  mvn test -pl boot -Dtest=SalesSimulatorITTest
  ```
- **Controller Unit Tests**:
  ```bash
  mvn test -pl controller
  ```
- **Application Logic Unit Tests**:
  ```bash
  mvn test -pl application
  ```
- **Infrastructure/Persistence Unit Tests**:
  ```bash
  mvn test -pl infrastructure
  ```

## Challenges and Improvements

### Challenges
- **Hexagonal Architecture Implementation**: Maintaining a strict separation between domain, application, and infrastructure layers in a multi-module Maven project requires careful dependency management to avoid circular dependencies and ensure that the domain remains "pure".
- **API-First Development**: Integrating OpenAPI Generator into the build process to ensure the controller implementation stays in sync with the API specification.
- **Timezone Management**: Switching from `LocalDateTime` to `OffsetDateTime` in the API layer to properly handle temporal data in a global context, while keeping the domain/persistence layers focused on local business hours.
- **Overlapping Price Logic**: Correctly implementing the priority-based selection when multiple price ranges overlap for the same product and brand.
- **Data Consistency**: Ensuring that the mapping between database entities and domain objects handles null values and data types correctly across different layers.

### Potential Improvements
- **Global Error Handling**: Implement a `@ControllerAdvice` to provide more detailed and consistent error responses (e.g., using RFC 7807 Problem Details).
- **Caching**: Implement a caching layer (e.g., Spring Cache with Redis or Caffeine) for frequently queried prices to improve performance.
- **Persistence**: Transition from an in-memory H2 database to a persistent database (e.g., PostgreSQL) for production environments.
- **Testing**: Increase coverage with more edge cases and performance tests to ensure the application scales effectively.
