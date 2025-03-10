# Banking System Mini Project

## Overview
The **Banking System Mini Project** is a console-based Java application that provides essential banking functionalities such as user registration, login, account creation, and financial transactions. It uses **MySQL** as the database for data storage and retrieval.

---
## Features
- **User Management:** Register and login securely.
- **Account Management:** Open a new bank account, debit, credit, and transfer funds.
- **Transaction Security:** Uses security PIN validation for transactions.
- **Database Integration:** Utilizes **JDBC** for MySQL connectivity.

---
## Technologies Used
- **Java** (JDK 11+ recommended)
- **JDBC** (Java Database Connectivity)
- **MySQL** (Database management system)

---
## Database Configuration
Create a MySQL database named **`Banking_System`** and add the following tables:

### 1. `user` Table
```sql
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);
```

### 2. `accounts` Table
```sql
CREATE TABLE accounts (
    account_number BIGINT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    balance DOUBLE NOT NULL,
    security_pin VARCHAR(10) NOT NULL
);
```

---
## Setup Instructions
1. **Install MySQL** and create the database using the above schema.
2. **Configure Database Credentials** in the `BankApp` class:
   ```java
   private static final String url = "jdbc:mysql://localhost:3306/Banking_System";
   private static final String uname = "your-username"; // Update username
   private static final String pass = "your-password"; // Update password
   ```
3. **Compile and Run the Project:**
   ```bash
   javac BankApp.java
   java BankApp
   ```

---
## Usage Instructions
1. **Register a User:**
   - Select option **1** and provide your details (Full Name, Email, Password).
2. **Login:**
   - Select option **2** and enter your registered email and password.
3. **Banking Features After Login:**
   - If you don't have an account, select **"Open a New Bank Account"**.
   - After account creation, you can:
     - **Debit Money**
     - **Credit Money**
     - **Transfer Money**
     - **Check Balance**
4. **Exit the System:**
   - Select option **3**.

---
## Sample Workflow
1. **Registration:**
   - Enter your details (Name, Email, Password).
2. **Login:**
   - Enter your Email and Password.
3. **Account Creation:**
   - Enter required account details like name, initial balance, and security PIN.
4. **Transaction Operations:**
   - Perform debit, credit, or transfer operations using your security PIN.

---
## Important Notes
- Ensure the MySQL service is running before executing the application.
- Make sure the database credentials are correctly configured.
- To prevent data loss, transactions are managed with `commit()` and `rollback()` logic for enhanced security.

