# PGBuddy

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen) ![Java](https://img.shields.io/badge/Java-17-blue) ![MySQL](https://img.shields.io/badge/MySQL-8.0.41-orange) ![License](https://img.shields.io/badge/License-MIT-yellow)

**PGBuddy** is a Spring Boot-based application designed to streamline the management of Paying Guest (PG) accommodations for residents (PG Mates). It provides a user-friendly interface for residents to handle payments, submit complaints, manage meal preferences, and more, while offering admins tools to oversee operations.

Staying in a PG in a city like Bengaluru can be a real hassle—think about the delayed fixes, payment mix-ups, and zero communication. This web app is a try to sort all that out, making life in your PG way smoother!

<p align="center">
  <img src="https://github.com/user-attachments/assets/3b66f610-b1bb-48f4-ac06-4fa73beeb29a" alt="Your Alt Text" width="300">
</p>

---

## Features

### Resident View (PG Mates)
- **User Authentication**: Secure sign-up/login using email or phone with JWT-based authentication (Spring Security).
- **Payment Management**: View dues, pay rent/food charges via Razorpay/Stripe, and download payment receipts.
- **Complaint System**: Submit issues (e.g., "AC not working") with status tracking (Pending, Resolved, In Progress).
- **Notice Board**: View admin announcements (e.g., "Water maintenance tomorrow").
- **Roommate Finder**: Browse basic profiles of co-residents (privacy-respecting).
- **Meal Preferences**: Submit daily/weekly meal choices (e.g., Veg/Non-Veg).
- **Cafe**: Order food and checkout for payment.

---

## Tech Stack

- **Backend**: Spring Boot 3.4.3, Spring Data JPA, Spring Security
- **Language**: Java 23
- **Database**: MySQL 8.0.41
- **Authentication**: JWT (JSON Web Tokens)
- **Payment Gateway**: Razorpay/Stripe API
- **ORM**: Hibernate
- **Build Tool**: Maven
- **Dependencies**: Lombok, HikariCP, Hibernate Validator
- **Cloud & Deployment:** Heroku  (Not Implemented yet)
- **Logging & Monitoring:** Spring Actuator (Not Implemented yet)

---

## Project Structure

```plaintext
PGBuddy/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/pgbuddy/
│   │   │       ├── models/         # Entity classes (User, Room, Complaint, etc.)
│   │   │       ├── repositories/   # JPA repositories
│   │   │       ├── services/       # Business logic
│   │   │       ├── controllers/    # REST API endpoints
│   │   │       └── PgBuddyApplication.java  # Main application class
│   │   └── resources/
│   │       ├── application.properties  # Configuration
│   │       └── data.sql                # Dummy data
├── pom.xml  # Maven dependencies
└── README.md
```


---
## 📍 API Endpoints (Planned)

### 🔐 Auth
- **POST** `/api/auth/signup` - Register a new user.  
- **POST** `/api/auth/login` - Generate JWT token.  

### 💳 Payments
- **POST** `/api/payments/create-order` - Initiate a payment.  
- *(More endpoints to be added as development progresses.)*  

The API endpoints have been tested using Postman and Spring Test, ensuring compliance with REST API standards.

## 📖 API Documentation
- API documentation is available via Swagger UI (`/swagger-ui` route when running the server)   

---
## ⚙️ Setup Instructions

### 🛠 Installation

1. **Clone the Repository**:
   ```
   bash
   git clone https://github.com/your-username/PGBuddy.git
   cd PGBuddy
   ```

2. **Configure MySQL**:
Create a database:
```
CREATE DATABASE pg_mates;
```

Update src/main/resources/application.properties with your MySQL credential
```
spring.datasource.url=jdbc:mysql://localhost:3306/pg_mates
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.sql.init.mode=always
```

Install Dependencies:
```
mvn clean install
```

Run the Application:
```
mvn spring-boot:run
```
The app will start on http://localhost:8080

---
## Contributing
Contributions are welcome! Feel free to fork the repo and submit pull requests.

---
## Acknowledgments
Built with ❤️ by Dharshan Kumar.

