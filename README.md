# Profile Service 👤

**Profile Service** is a microservice responsible for managing user profiles within the Eventure system. It handles creation, reading, updating, and deletion (CRUD) of profile data and persists information using a PostgreSQL database.

## 🛠 Tech Stack

* **Java 17+** (Core logic)
* **Spring Boot 3** (Framework)
* **PostgreSQL** (Database)
* **Liquibase** (Database Migrations)
* **Docker & Docker Compose** (Containerization)

---

## 🚀 How to Run

Ensure you have **Docker Desktop** installed and running.

### Option 1. Quick Start (Scripts)

**Windows:**
Run the batch file in the root directory:

`start.bat`

**macOS / Linux::**
1.  Make the script executable (only needed the first time):

`chmod +x start.sh`

2.  Run the script:

`./start.sh`

### Option 2. via Terminal (Universal)
Open a terminal in the project root and run:

`docker-compose up -d --build`

---

## Your application will be available at http://localhost:8081.
