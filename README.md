# **SIMS - Smart Inventory Management System**

## **Overview**
SIMS (Smart Inventory Management System) is a **Dockerized microservices architecture** for managing products, orders, and notifications. It leverages **Spring Boot**, **MySQL**, and **Kafka** for asynchronous event-driven messaging. The system is deployed using **Docker Compose**.

## **Architecture**
- **Product-Service**: Manages product inventory.
- **Order-Service**: Handles order creation and updates.
- **Notification-Service**: Listens for Kafka events and stores notifications.
- **MySQL Database**: Stores products, orders, and notifications.
- **Kafka + Zookeeper**: Enables event-driven communication.

---

## **Deployment Instructions**

### **1️⃣ Prerequisites**
Before deploying SIMS, ensure you have the following installed:
- **Docker** & **Docker Compose**
- **Java 17** (for local development)
- **Postman** (to test API endpoints)

---

### **2️⃣ Clone the Repository**
```sh
git clone https://github.com/paulwolfe0313/sims.git
cd sims
