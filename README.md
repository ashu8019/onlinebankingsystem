# Online Banking System (Java + JDBC + MySQL)

A console-based banking application built with Java 17, JDBC, and MySQL.
It has two portals:
- **Admin (Accountant)** portal for managing customers/accounts
- **Customer** portal for balance and transaction operations

---

## Project Overview

This project follows a simple layered structure:
- **Entry point / UI:** `App.java` (menu-driven CLI)
- **DAO layer:** interfaces + JDBC implementations for admin/customer operations
- **Entity layer:** `Accountant`, `Customer`
- **Exception layer:** custom checked exceptions (`AccountantException`, `CustomerException`)
- **DB connection utility:** `DatabaseConnection`

### Package Structure

```text
src/main/java/code/vibes/onlinebankingsystem/
├── App.java
├── dao/
│   ├── AccountantDao.java
│   ├── AccountantDaoImplementation.java
│   ├── CustomerDao.java
│   └── CustomerDaoImplementation.java
├── databaseconnection/
│   └── DatabaseConnection.java
├── entity/
│   ├── Accountant.java
│   └── Customer.java
└── exception/
    ├── AccountantException.java
    └── CustomerException.java
```

---

## Features

### Admin Portal
1. Admin login
2. Add customer
3. Add account for existing customer (`cid`)
4. Update customer address (via account number)
5. Delete account
6. View all customers with account details

### Customer Portal
1. Customer login (name + password + account number)
2. View balance
3. Deposit money
4. Withdraw money
5. Transfer money between accounts

---

## Tech Stack

- Java 17
- Maven
- JDBC
- MySQL Connector/J `8.3.0`
- JUnit 5 (basic starter test included)

---

## Database Requirements

The code expects a MySQL database named:
- `bankingsystem`

And tables used by queries:
- `accountant`
- `customerinformation`
- `account`

### Minimum expected columns

#### `accountant`
- `accountantUsername`
- `accountantEmail`
- `accountantPassword`

#### `customerinformation`
- `cid` (primary key, auto-increment)
- `customerName`
- `customerMail`
- `customerPassword`
- `customerMobile`
- `customerAddress`

#### `account`
- `customerAccountNumber` (primary key, auto-increment)
- `customerBalance`
- `cid` (foreign key to `customerinformation.cid`)

---

## Configuration

DB connection is currently hardcoded in:
- `src/main/java/code/vibes/onlinebankingsystem/databaseconnection/DatabaseConnection.java`

Current values in code:
- URL: `jdbc:mysql://localhost:3306/bankingsystem?useSSL=false&serverTimezone=UTC`
- Username: `root`
- Password: `Mahesh@1234`

> Update these credentials for your local setup before running.

---

## Build and Run

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL running locally
- Database and tables created

### Build

```bash
mvn clean compile
```

### Run tests

```bash
mvn test
```

### Run application

Run `App.java` from your IDE (recommended), using main class:
- `code.vibes.onlinebankingsystem.App`

(If you want CLI-only run via Maven, add `exec-maven-plugin` to `pom.xml`.)

---

## Current Behavior Notes (from code analysis)

- DAO methods use prepared statements (good baseline against SQL injection).
- Transfer operation performs withdraw and deposit as separate calls; it is **not transactional**.
- Customer login uses customer **name** as username (not email).
- `viewAllCustomer()` prints directly to console instead of returning DTO/list.
- The included unit test is a default placeholder (`assertTrue(true)`).

---

## Suggested Improvements

1. Move DB credentials to environment variables or properties file.
2. Add transaction handling for money transfer (`setAutoCommit(false)` + rollback).
3. Hash passwords instead of storing plain text.
4. Add real unit/integration tests for DAO operations.
5. Add SQL migration/init scripts for reproducible setup.

---

## Maven Coordinates

- Group ID: `code.vibes`
- Artifact ID: `onlinebankingsystem`
- Version: `0.0.1-SNAPSHOT`

---

## Authoring Note

This README is generated from direct analysis of the current source code in this repository.