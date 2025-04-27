# Buy Now Pay Later Payment Backend

A Spring Boot backend application for managing customers, loan applications, loan approvals, installments, and payments.  
It provides a RESTful API for a simple loan management system.

---

## ✨ Features

- Customer Management
  - Create new customers
  - View customer details
  - List all customers
- Loan Application Management
  - Apply for a loan
  - Approve or reject loan applications
  - View customer-specific loans
  - View pending loans
- Installment Management
  - Automatically generate installments upon loan approval
  - View installments for a specific loan
- Payment Handling
  - Make payments toward loans
  - Automatically update installment statuses
  - Mark loans as fully paid when all installments are completed

---

## 🛠️ Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- H2 / MySQL Database (can be configured)
- Maven
- RESTful APIs

---

## 📦 Project Structure

```
com.paymedia.paymentbackend
│
├── controller
│   ├── CustomerController.java
│   └── LoanController.java
│
├── entity
│   ├── Customer.java
│   ├── LoanApplication.java
│   ├── Installment.java
│   └── Payment.java
│
├── repository
│   ├── CustomerRepository.java
│   ├── LoanApplicationRepository.java
│   ├── InstallmentRepository.java
│   └── PaymentRepository.java
│
├── service
│   └── LoanService.java
│
└── dto
    ├── LoanApplicationResponse.java
    ├── InstallmentResponse.java
    ├── PaymentRequest.java
    └── PaymentResponse.java
```

---

## 🚀 API Endpoints

### Customer Endpoints

| Method | Endpoint | Description |
|:------:|:---------|:------------|
| GET    | `/api/customers` | Get all customers |
| POST   | `/api/customers` | Create a new customer |
| GET    | `/api/customers/{id}` | Get a customer by ID |

### Loan Endpoints

| Method | Endpoint | Description |
|:------:|:---------|:------------|
| POST   | `/api/loans/apply` | Apply for a new loan |
| PUT    | `/api/loans/{id}/approve` | Approve a loan application |
| PUT    | `/api/loans/{id}/reject` | Reject a loan application |
| GET    | `/api/loans/customer/{customerId}` | Get all loans for a customer |
| GET    | `/api/loans/{id}/installments` | Get installments for a loan |
| GET    | `/api/loans/pending` | Get all pending loan applications |
| POST   | `/api/loans/{id}/payments` | Make a payment towards a loan |

---

## 📋 How to Run Locally

1. **Clone the repository**

   ```bash
   git clone https://github.com/your-username/paymedia-payment-backend.git
   cd paymedia-payment-backend
   ```

2. **Configure the database**

   Update `application.properties`:
   ```properties
    spring.application.name=paymentbackend
    server.port=8080
    spring.datasource.url=jdbc:mysql://localhost:3306/paymentdb?createDatabaseIfNotExist=true
    spring.datasource.username=root
    spring.datasource.password=root
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    cors.allowed-origins=*
   ```

3. **Build and run the project**

   ```bash
   mvn spring-boot:run
   ```

4. **Access API documentation**

   You can test the endpoints via Postman or access the H2 console at:  
   `http://localhost:8080/h2-console` (if using H2)

---

## 🧩 DTO Usage

- **LoanApplicationResponse**: For safer, cleaner loan data responses
- **InstallmentResponse**: For installment details without exposing entity internals
- **PaymentRequest**: For structured payment requests
- **PaymentResponse**: For payment response details after processing

---

## 📖 Future Enhancements

- JWT-based Authentication and Authorization
- Partial Payment Support
- Admin and Customer Roles
- Swagger API Documentation
- Notification System for Payment Reminders

---

## 🤝 Contribution

Pull requests are welcome!  
For major changes, please open an issue first to discuss what you would like to change.

---

## 📜 License

This project is open-source and available under the [MIT License](LICENSE).

---
