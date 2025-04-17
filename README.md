# E-Commerce-platform-architecture with Microservice

A modern, scalable e-commerce platform built using Java, Spring Boot microservice, Spring Cloud, and React.js( In progress). This project simulates a real-world system designed for high performance, modularity, and maintainability.

-----
## Author

**Ashish Rajput**  
Full Stack Java Developer  
[LinkedIn](https://www.linkedin.com/in/ashish-rajput-6b59361a4)  
Email- ashishrajput35cs@gmail.com

---

## Motivation

This project was initiated to bridge the gap during my job search and demonstrate my hands-on skills in designing scalable distributed systems using Spring Boot and microservices.

## License

This project is open for recruiters, collaborators, and hiring managers to explore. Not under an open-source license yet.

---

## Tech Stack

### Backend (Microservice)
-Java 17
-spring Boot
-Spring Cloud Gateway
-Spring Security (JWT)
-Spring Config Server
-Spring Eureka Server
-Spring Data JPA & Hibernate
-MySQL/ PostgreSQL
-Kafka (planned for async comm.)
-Swagger (API Documentation)
-Docker (Containerization)

### Frontend (Planned)
-React.js
-Redux (for state management)
-Axios (For API communication)

### DevOps & Tools:
- Git & GitHub
- Docker Compose
- IntelliJ/ VSCode

## Microservices Architecture ( In progress)
|----------------------|-------------------------------------------------------------|--------------|
| Service              | Description                                                 | Status       |
|----------------------|-------------------------------------------------------------|--------------|
| **Service Registry** | Eureka Server for Service Registration and Discovery        |  Completed   |
| **API Gateway**      | Routes all requests to appropriate services                 |  Completed   |
| **Authentication**   | Handles JWT login, signup, and auth flow                    |  Completed   |
| **User Service**     | Manages user roles, profiles                                |  In progress |
| **Product Service**  | Catalog, product listing & management                       |  Upcoming    |
| **Order Service**    | Cart, order creation & tracking                             |  Upcoming    |
| **Payment Service**  | Integration with payment gateways                           |  Upcoming    |
| **Config Server**    | Centralized config for microservices                        |  Completed   |
|----------------------|-------------------------------------------------------------|--------------|

## Feature (Current & Planned)
- JWT Authentication & Role-based Authorization (current)
- Centralized Api Gateway, Eureka Server, Config Server using Spring Cloud (current)
- User Profile Management (current)
- Swagger UI for each microservice
- Product & Order management (Planned)
- Payment Gateway integration-Stripe or Razorpay(Planned)
- Kafka for async events (planned)
- Docker-based microservices

---

## Getting Started

> This is a work-in-progress project. Setup instructions will be added as the modules stabilize.

### Prerequisites
- Java 17+
- MySQL/PostgreSQL
- Docker (Optional)
- Maven
- Git

---

##Repository Structure

```
ecommerce-microservices/
├── api-gateway/
├── auth-service/
├── user-service/
├── product-service/
├── order-service/
├── payment-service/
├── config-server/
├── service-registry/
├── frontend/ (React - upcoming)
└── docker-compose.yml
```

---

