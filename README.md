# Sales System

## Description

The **Sales System** is a microservices-based application designed to manage various aspects of an e-commerce platform. This system leverages modern technologies such as **gRPC**, **RabbitMQ**, **Redis**, **MySQL**, **MongoDB**, and follows the **Saga Orchestration design pattern**.

### Key Features:
- **gRPC & RabbitMQ**: Enables efficient communication between microservices.
- **Redis Cache**: The Catalog service uses Redis for caching product data.
- **Saga Orchestration**: The Order service orchestrates distributed transactions across services.
- **Database**: MySQL is used for most services, while the Inventory service uses MongoDB for flexible data storage.
- **Microservices**: Each service is independent, scalable, and deployable via Docker.

## Services

- **Catalog Service**: Manages product information, uses Redis cache for improved performance.
- **Inventory Service**: Handles stock management with MongoDB.
- **Order Service**: Orchestrates the order workflow using Saga pattern.
- **Payment Service**: Handles payment processing.
- **Discovery Server**: Facilitates dynamic service discovery.

## Technologies Used

- **gRPC**: For fast and efficient communication between services.
- **RabbitMQ**: For asynchronous messaging between services.
- **Redis**: Caching layer for the Catalog service.
- **MySQL**: For storing relational data.
- **MongoDB**: Used by Inventory service for NoSQL storage.
- **Docker**: For containerization of the application.

## Getting Started

### Prerequisites

- Java 11 or higher
- Docker
- Maven

### Setup

1. Clone the repository:

    ```bash
    git clone https://github.com/HuzeyfeUlutas/sales-system.git
    ```

2. Navigate to the project directory:

    ```bash
    cd sales-system
    ```

3. Build the project with Maven:

    ```bash
    mvn clean install
    ```

4. Start the application using Docker:

    ```bash
    docker-compose up
    ```

5. Access the services via their respective endpoints.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
