# Project Structure

The project consists of two microservices:
    `order-service` and `order-client-service`, 
both utilizing a PostgreSQL database. 
The database migration is handled seamlessly through Flyway, and initial data insertion is facilitated by the `V4__init_data.sql` script.

## Order Service

The `order-service` microservice provides a comprehensive API for managing orders, adhering to specified requirements. The API's detailed documentation can be accessed [here](http://localhost:8091/swagger-ui.html).

When users execute REST requests to create or cancel orders, the controller seamlessly sends the corresponding data through a Message Queue (MQ) query. The `order-client-service` listens to this query and aggregates valuable data about customers. To retrieve all statistics, a utility method can be accessed via a simple GET request at [http://localhost:8092/api/orders-client/statistic/all](http://localhost:8092/api/orders-client/statistic/all).

For testing purposes, the project leverages the Mockito framework and an H2 in-memory database to test the `order-service`.

## Project Directory Structure

- `order-app`
    - `order-service`
        - `Dockerfile`
    - `order-client-service`
        - `Dockerfile`
    - `docker-compose.yml`

# How to Build and Start

1. **Build `order-service`**
    - Navigate to the `order-service` directory.
    - Run the command: `mvn clean install`.

2. **Build `order-client-service`**
    - Navigate to the `order-client-service` directory.
    - Run the command: `mvn clean install`.

3. **Return to the `order-app` directory**
    - Run the command: `docker-compose up -d`.
    - This will result in the creation of:
        - `order-service` (accessible on port 8091)
        - PostgreSQL database (`ORDERS`)
        - `order-client-service` (accessible on port 8092)
        - PostgreSQL database (`ORDERS_CLIENTS`)
        - ActiveMQ

# REST API - `order-service`

The REST API for the `order-service` is documented using OpenAPI and Swagger. Navigate to [localhost:8091/swagger-ui.html](http://localhost:8091/swagger-ui.html) for detailed API documentation.

# Testing

To test the application, you can create an order in the `order-service` via a POST request:

```http
POST http://localhost:8091/api/orders/new
Content-Type: application/json

{
    "code": "new_order_1",
    "status": "PLACED",
    "customerCode": "cust 1",
    "orderDetails": [
        {
            "code": "hat-1",
            "category": "clothes",
            "amount": 1,
            "price": 2000.00
        },
        {
            "code": "t-shirt",
            "category": "clothes",
            "amount": 5,
            "price": 300.00
        }
    ]
}
```
For an overview of all customer statistics, you can access:

```http
GET http://localhost:8092/api/orders-client/statistic/all
```