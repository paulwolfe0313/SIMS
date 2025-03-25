# Store Inventory Management System (SIMS)

A microservices-based store inventory management system built with Spring Boot.

## System Architecture

The system consists of the following microservices:

1. **Auth Service** (Port: 9093)
   - Handles user authentication and authorization
   - Manages user accounts and roles
   - Issues JWT tokens for service authentication
   - Provides password reset functionality

2. **Product Service** (Port: 9090)
   - Manages product inventory
   - Handles product CRUD operations
   - Tracks stock levels
   - Secured with JWT authentication

3. **Order Service** (Port: 9091)
   - Processes customer orders
   - Manages order lifecycle
   - Integrates with Product Service for inventory updates
   - Publishes order events to Kafka
   - Secured with JWT authentication

4. **Notification Service** (Port: 9095)
   - Handles system notifications
   - Consumes order events from Kafka
   - Manages user notifications
   - Provides webhook endpoints for external integrations
   - Secured with JWT authentication

## Technology Stack

- Java 17
- Spring Boot 3.1.12
- Spring Security with JWT
- MySQL 8.0
- Apache Kafka
- Docker & Docker Compose
- Maven

## Security Implementation

- JWT-based authentication across all services
- Role-based access control (ADMIN, USER)
- Secure password storage with BCrypt
- Stateless authentication
- Protected API endpoints

## Getting Started

### Prerequisites

- Docker and Docker Compose
- Java 17
- Maven

### Setup Instructions

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd SIMS
   ```

2. Build all services:
   ```bash
   mvn clean package -DskipTests
   ```

3. Start the system using Docker Compose:
   ```bash
   docker-compose up -d
   ```

4. The services will be available at:
   - Auth Service: http://localhost:9093
   - Product Service: http://localhost:9090
   - Order Service: http://localhost:9091
   - Notification Service: http://localhost:9095

### Default Users

The system comes with two default users:

1. Admin User
   - Email: admin@example.com
   - Password: admin123
   - Role: ADMIN

2. Regular User
   - Email: user@example.com
   - Password: user123
   - Role: USER

## API Documentation

### Auth Service Endpoints

- POST `/auth/register` - Register new user
- POST `/auth/login` - User login
- POST `/auth/forgot-password` - Request password reset
- POST `/auth/reset-password` - Reset password
- GET `/auth/verify-email` - Verify email address

### Product Service Endpoints

- GET `/products` - List all products
- GET `/products/{id}` - Get product by ID
- POST `/products` - Create new product (ADMIN only)
- PUT `/products/{id}` - Update product (ADMIN only)
- DELETE `/products/{id}` - Delete product (ADMIN only)

### Order Service Endpoints

- POST `/orders` - Create new order
- GET `/orders` - List user's orders
- GET `/orders/{id}` - Get order by ID
- PUT `/orders/{id}/cancel` - Cancel order
- DELETE `/orders/{id}` - Delete order (ADMIN only)

### Notification Service Endpoints

- GET `/notifications` - List user's notifications
- GET `/notifications/{id}` - Get notification by ID
- PUT `/notifications/{id}/read` - Mark notification as read
- DELETE `/notifications/{id}` - Delete notification
- POST `/notifications/webhook/{provider}` - Webhook endpoint

## Event Flow

1. User places an order through Order Service
2. Order Service validates and processes the order
3. Order Service publishes order event to Kafka
4. Notification Service consumes the order event
5. Notification Service creates and stores notifications
6. Users can view their notifications through the API

## Monitoring

- Each service exposes actuator endpoints for monitoring
- Health checks are implemented for all services
- Logging is configured for debugging and monitoring

## Development

To run the services locally for development:

1. Start MySQL and Kafka using Docker Compose:
   ```bash
   docker-compose up -d mysql-sims kafka
   ```

2. Run each service individually using Maven:
   ```bash
   cd auth-service
   mvn spring-boot:run

   cd ../product-service
   mvn spring-boot:run

   cd ../order-service
   mvn spring-boot:run

   cd ../notification-service
   mvn spring-boot:run
