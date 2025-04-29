# PGBuddy
*Your ultimate companion for managing PG life!*

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen) ![Java](https://img.shields.io/badge/Java-23-blue) ![MySQL](https://img.shields.io/badge/MySQL-8.0.41-orange) ![License](https://img.shields.io/badge/License-MIT-yellow)

**PGBuddy** is a React (frontend) & Spring Boot (backend) based application designed to streamline the management of Paying Guest (PG) accommodations for residents (PG Mates). It provides an user-friendly interface for residents to handle payments, submit complaints, manage meal preferences, and more, while offering admins tools to oversee operations.

Staying in a PG in a city like Bengaluru can be a real hassle—think about the delayed fixes, payment mix-ups, and zero communication. This web app is a try to sort all that out, making life in your PG way smoother!

This repo contains the **backend** code for PGBuddy, built with Spring Boot. If you're interested in the frontend, which is built with React, you can find it here: 🔗 [PGBuddy Frontend](https://github.com/dharshan-kumar-ks/PGBuddy-FrontEnd)

<p align="center">
  <img src="https://github.com/user-attachments/assets/3b66f610-b1bb-48f4-ac06-4fa73beeb29a" alt="Your Alt Text" width="300">
</p>

---
## ☁️ Hosting

This project is fully cloud-hosted.  
You can access the live website here: 🔗 [PG Buddy Website](https://pg-buddy-front-end.vercel.app)

If you prefer to interact directly with the backend (e.g., via Postman or your own frontend), you can use this backend URL:  🔗 [Backend API](https://pgbuddy.onrender.com)

- 🖥️ **Backend**: Hosted on **Render** (running inside a Docker container)
- 🗄️ **Database**: Hosted on **Railway** (SQL database)
- 🌐 **Frontend**: Hosted on **Vercel**

**Test Credentials:**  
- Resident user:
  - **Username:** `guestuser@gmail.com`  
  - **Password:** `guestuser`  
- Admin user:
  - **Username:** `admin@gmail.com`  
  - **Password:** `admin123` 

Or, you can create your own profile as a resident user using the **Registration** page.

⚡ **Important Note**:
- The backend may take up to **3 minutes** for the initial boot-up when opening the website because it goes into **sleep mode** after **30 minutes of inactivity** (to optimize cloud resource usage and reduce costs).
- Once the server is awake, subsequent API calls will be much faster (in milliseconds).

---
## ✨ Features

- **Modular Services**: Cleanly separated service, repository, and controller layers for better organization and testability.
- **Stream Processing**: Used Java Streams in service layer for declarative, readable operations instead of traditional loops.

### 👤 Resident View (PG Mates)
- **Secure Authentication**: Sign up or log in via email with JWT-based authentication (Spring Security).
- **Rent & Payments**: View rent dues, pay securely via Razorpay, and track your payment history.
- **Support Tickets**: Raise issues (e.g., "AC not working"), track status (Pending, In Progress, Resolved), and chat with the admin.
- **Notice Board**: View and bookmark announcements (e.g., "Water maintenance tomorrow").
- **Meal Preferences**: Choose daily/weekly meal type (Veg/Non-Veg) and view real-time user vote counts.
- **Café Ordering**: Browse the café menu, place orders, and reorder from order history.
- **Utilities**: Monitor internet/electricity usage and purchase internet add-on packs.
- **Profile Management**: Update your profile and view admin contact details.

### 🔑 Admin View (PG Managers)
- **Ticket Dashboard**: View, assign, update ticket statuses, and chat with residents.
- **Notice Management**: Post and manage important notices for all residents.

---

## ⚙️ Tech Stack

- **Backend**: Spring Boot 3.4.3, Spring Data JPA, Spring Security, Spring Actuator, Spring Websocket (SockJS + STOMP)
- **Language**: Java 23.0.1
- **Database**: MySQL 8.0.41
- **Authentication**: JWT (JSON Web Tokens) 0.12.5
- **Payment Gateway**: Razorpay 1.4.8
- **ORM**: Hibernate 8.0.1
- **Build Tool**: Maven 4.0.0
- **Dependencies**: Lombok 1.18.30, Swagger 2.5.0
- **Cloud & Deployment:** Railway
- 🔌 **Other Notable Integrations**:
  - Implemented **real-time chat** using $\color{teal}{\textsf{WebSockets}}$ over **SockJS** with a simple **message broker**; clients send messages to `"/app/chat.sendMessage"` and receive them via `"/user/queue/messages"`.
  - Integrated $\color{teal}{\textsf{Razorpay}}$ for rent and café **payment handling** with both **API and checkout UI**.
  - Enabled $\color{teal}{\textsf{JWT-based authentication}}$ using **Spring Security** for secure login.
  - Applied $\color{teal}{\textsf{BCrypt hashing}}$ for password encryption; **no plain-text passwords** stored in the **MySQL database**.
  - All application data is persisted in a $\color{teal}{\textsf{MySQL}}$ backend.
 
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

### 🧠 Project Architecture Flow

This project follows a modular, layered architecture that cleanly separates concerns across frontend, backend, authentication, business logic, and data access layers.

#### 🔄 Flow of a Typical Request

- **User Request**
  - ⬇️
- **Frontend** (React App - Hosted on Vercel)
  - ⬇️ API calls (Axios / Fetch)
- **Backend** (Spring Boot - Hosted on Render)
  - ⬇️ Filters (JWT Authentication / Token Validation)
- **Controllers** (Handle incoming API/WebSocket requests)
  - ⬇️
- **Services** (Business Logic)
  - ⬇️
- **Repositories** (Database Access)
  - ⬇️
- **Database** (SQL - Hosted on Railway)

#### 🖼️ Sequence Diagram

For a more detailed visualization of the request and WebSocket flow, refer to the sequence diagram below:
<img width="1200" alt="Architecture Sequence Diagram" src="https://github.com/dharshan-kumar-ks/PGBuddy-FrontEnd/blob/main/app_ui_images/sequence-diagram.jpg" />

---
## 📍 API Endpoints (Important Ones)

### 🔐 Authentication
- **POST** `/api/auth/signup` – Register a new user.
- **POST** `/api/auth/login` – Authenticate and receive JWT token.

### 📢 Notices
- **GET** `/api/notices` – Retrieve all published notices.
- **POST** `/api/notices/publish` – Create and publish a new notice (admin access).

### 🛠️ Tickets
- **POST** `/api/tickets/create` – Submit a new ticket for any issue.
- **GET** `/api/tickets/user/{userId}` – Fetch all tickets submitted by a specific user.
- **GET** `/api/tickets/all` – Fetch all tickets across all users (admin access).
- **GET** `/api/chat/history/{ticketId}` – Retrieve chat history for a ticket.

### 💳 Rent & Booking
- **GET** `/api/booking/{userId}` – Get rent payment details for a user.
- **POST** `/api/booking/pay` – Initiate rent payment using Razorpay.
- **GET** `/api/booking/transactions/{transactionId}` – View payment transaction details.

### ☕ Café
- **GET** `/api/cafe/menu` – Retrieve full café menu items.
- **POST** `/api/cafe/order` – Place an order and save to database.
- **GET** `/api/cafe/orders` – Fetch all placed orders by the user.

### ⚡ Utility Services
- **GET** `/api/internet/usage` – Get current internet usage for a user.
- **GET** `/api/internet/data-add-ons` – List available internet data add-on packs.
- **GET** `/api/electricity/usage` – View electricity consumption stats.
- **GET** `/api/roomcleaning/usage` – Check past room cleaning usage.
- **POST** `/api/roomcleaning/request` – Request a new room cleaning service.

### 📊 Application Metrics
- **GET** `/api/system/metrics` – Fetch system health metrics like CPU and memory usage

The API endpoints have been tested using Postman, ensuring compliance with REST API standards.

## 📖 API Documentation

- Interactive API docs available via **Swagger UI**:  
  🔗 [https://dharshan-kumar-ks.github.io/PGBuddy/](https://dharshan-kumar-ks.github.io/PGBuddy/)
- Swagger documentation is **hosted on GitHub Pages** and automatically updated through **GitHub Actions** when backend changes, following a CI/CD pipeline.
- Also available on **Postman**, generated directly from the Swagger spec:  
  🔗 [Postman Public API Docs](https://documenter.getpostman.com/view/43024310/2sB2ixiDX6)

---

## 📸 Screenshots of UI (from frontend)

### Home Page
<img width="800" alt="image" src="https://github.com/dharshan-kumar-ks/PGBuddy-FrontEnd/blob/main/app_ui_images/home-page.png" />
<img width="800" alt="image" src="https://github.com/dharshan-kumar-ks/PGBuddy-FrontEnd/blob/main/app_ui_images/ticket-page.png" />
<img width="800" alt="image" src="https://github.com/dharshan-kumar-ks/PGBuddy-FrontEnd/blob/main/app_ui_images/chat-ticket-page.png" />
<img width="800" alt="image" src="https://github.com/dharshan-kumar-ks/PGBuddy-FrontEnd/blob/main/app_ui_images/create-ticket-page.png" />

### Food Page
<img width="800" alt="image" src="https://github.com/dharshan-kumar-ks/PGBuddy-FrontEnd/blob/main/app_ui_images/food-page.png" />

### Stay Page
<img width="800" alt="image" src="https://github.com/dharshan-kumar-ks/PGBuddy-FrontEnd/blob/main/app_ui_images/stay-page.png" />
<img width="800" alt="image" src="https://github.com/dharshan-kumar-ks/PGBuddy-FrontEnd/blob/main/app_ui_images/razor-pau-UI.png" />
<img width="800" alt="image" src="https://github.com/dharshan-kumar-ks/PGBuddy-FrontEnd/blob/main/app_ui_images/razor-pay-payment-page.png" />

### Cafe Page
<img width="800" alt="image" src="https://github.com/dharshan-kumar-ks/PGBuddy-FrontEnd/blob/main/app_ui_images/cafe-page.png" />
<img width="800" alt="image" src="https://github.com/dharshan-kumar-ks/PGBuddy-FrontEnd/blob/main/app_ui_images/order-history-page.png" />

### Services Page
<img width="800" alt="image" src="https://github.com/dharshan-kumar-ks/PGBuddy-FrontEnd/blob/main/app_ui_images/utilities-page.png" />

### Accounts Page
<img width="800" alt="image" src="https://github.com/dharshan-kumar-ks/PGBuddy-FrontEnd/blob/main/app_ui_images/accounts-page.png" />

### Admin Page
<img width="800" alt="image" src="https://github.com/dharshan-kumar-ks/PGBuddy-FrontEnd/blob/main/app_ui_images/admin-notice-page.png" />

### Login Page
<img width="800" alt="image" src="https://github.com/dharshan-kumar-ks/PGBuddy-FrontEnd/blob/main/app_ui_images/login-page.png" />

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

Set the active profile to `dev` from `prod` in `src/main/resources/application.properties`:
```
spring.profiles.active=dev
```

Update `src/main/resources/application-dev.properties` with your MySQL DB credential
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
## Contributions
Contributions are welcome! Feel free to fork the repo and submit pull requests.

### 💡 Future Enhancement Ideas
- **Spring Boot Actuator**: Add detailed logging, monitoring, health checks, and metrics endpoints.
- **Metrics Visualization**: Integrate Prometheus and Grafana for real-time system monitoring and metric dashboards.
- **Spring Boot Test**: Add unit and integration tests for each feature.
- **Custom Exception Handling**: Add more project-specific exception classes instead of generic Java exceptions for better error tracking.
- **Elastic search**: Implement elastic search in backend; removing the current simple search logic from frontend
- **Email Alerts**: Notify users via email for ticket status updates and escalations.
- **Role-Based Dashboards**: Different dashboards for staff, management, and admins.
- **Media Uploads**: Allow image/file uploads in tickets and café menu items.
- **Analytics**: Add dashboards showing food, rent, and utilities usage stats for admins.
- **PDF Receipts**: Auto-generate downloadable PDF bills after successful payments.
- **Laundry Services**: Add a module to manage laundry requests and billing.
- **Virtual Wallet**: Introduce virtual coins for rent, café, or utility payments.
- **Feedback Surveys**: Show monthly satisfaction pop-ups on the homepage.
- **Resident Onboarding**: KYC, document upload, and digital signature during registration.
- **Escalation Notices**: Auto-notify users if their ticket remains unanswered for 2+ days.
- **Usage Data Export**: Allow residents to download their internet/electricity usage reports as CSV files.
- **Password Recovery**: Add "Forgot Password" support on the login screen.
- **Roommate Explorer**: Discover roommate profiles and switch rooms via an interactive room map.
- **Dynamic Meal Management**: Let admins add or remove meal options on the fly.

---
## Acknowledgments
Built with ❤️ by Dharshan Kumar.

