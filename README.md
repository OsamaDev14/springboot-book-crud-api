# 📚 Book API – Spring Boot CRUD Application

A **RESTful API** built using **Spring Boot** that performs full **CRUD operations** on Book data, along with advanced features like **pagination, sorting, and custom search**.

---

## 🚀 Features

* ✅ Create, Read, Update, Delete (CRUD)
* 🔍 Custom search APIs (Author, Genre, Price, Year)
* 📄 Pagination support
* 🔃 Sorting support
* ⚠️ Global Exception Handling
* 📦 Standard API Response Structure
* 🗄️ PostgreSQL Database Integration

---

## 🛠️ Tech Stack

* **Java 17+**
* **Spring Boot**
* **Spring Data JPA**
* **PostgreSQL**
* **Maven**
* **Postman** (for API testing)

---

## 🏗️ Project Architecture

```
Controller → Service → Repository → Database
```

* **Controller** → Handles HTTP requests
* **Service** → Business logic
* **Repository** → Database interaction
* **Entity** → Maps to database table

---

## 📘 Entity: Book

| Field         | Type    | Description                  |
| ------------- | ------- | ---------------------------- |
| id            | Integer | Primary key (Auto-generated) |
| title         | String  | Book title (Required)        |
| author        | String  | Author name                  |
| genre         | String  | Book category                |
| price         | Double  | Price of book                |
| publishedYear | Integer | Year of publication          |
| availability  | Boolean | Availability status          |

---

## 🗂️ Repository Layer

Extends `JpaRepository<Book, Integer>` and provides:

### 🔹 Derived Queries

```java
findByAuthor(String author)
findByAuthorAndTitle(String author, String title)
findByPriceLessThan(double price)
findByPriceBetween(double start, double end)
```

### 🔹 Custom JPQL Queries

```java
@Query("SELECT b FROM Book b WHERE b.availability = true")
@Query("SELECT b FROM Book b WHERE b.publishedYear=?1")
@Query("SELECT b FROM Book b WHERE b.genre=:genre")
```

---

## ⚙️ Service Layer

Handles:

* Business logic
* Validations
* Exception handling
* Pagination & sorting

### 🔹 Key Functionalities

* Save single & multiple books
* Fetch by ID / all records
* Update & delete operations
* Custom filtering
* Pagination & sorting

---

## 🌐 API Endpoints

### ➕ CREATE

```
POST   /api/books
POST   /api/books/all
```

### 📖 READ

```
GET    /api/books
GET    /api/books/{id}
```

### ✏️ UPDATE

```
PUT    /api/books
```

### ❌ DELETE

```
DELETE /api/books/{id}
```

### 🔍 CUSTOM SEARCH

```
GET /api/books/author/{author}
GET /api/books/search?author=abc&title=xyz
GET /api/books/price/less/{price}
GET /api/books/price/between/{start}/{end}
GET /api/books/available
GET /api/books/year/{year}
GET /api/books/genre/{genre}
```

### 📄 PAGINATION

```
GET /api/books/page?page=0&size=5
```

### 🔃 SORTING

```
GET /api/books/sort?fieldName=price
```

### 🔀 PAGINATION + SORTING

```
GET /api/books/page-sort?page=0&size=5&fieldName=price
```

---

## 📦 Sample Request

```json
{
  "title": "Java Basics",
  "author": "Osama",
  "genre": "Programming",
  "price": 499.99,
  "publishedYear": 2024,
  "availability": true
}
```

---

## 📦 Sample Response

```json
{
  "statusCode": 201,
  "message": "Book record saved successfully",
  "data": {
    "id": 1,
    "title": "Java Basics"
  }
}
```

---

## 📦 Response Structure

All responses follow:

```json
{
  "statusCode": 200,
  "message": "Success",
  "data": {}
}
```

---

## ⚠️ Exception Handling

Handled using `@ControllerAdvice`

### 🔹 Exceptions

* `IdNotFoundException` → 404
* `NoRecordAvailableException` → 404
* `InvalidInputException` → 400

---

## ⚙️ Configuration

### 🗄️ Database (PostgreSQL)

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/LibDB
spring.datasource.username=postgres
spring.datasource.password=root
```

### 🔧 JPA Settings

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

---

## ▶️ How to Run

### 1️⃣ Clone Repository

```bash
git clone https://github.com/your-username/book-api-spring-boot.git
```

### 2️⃣ Navigate to Project

```bash
cd book-api-spring-boot
```

### 3️⃣ Run Application

```bash
mvn spring-boot:run
```

---

## 🧪 API Testing

Use:
* Postman

---

## 📈 Future Improvements

* 🔐 Add JWT Authentication
* 📄 Add Swagger Documentation

---

## 👨‍💻 Author

**Osama**
*Backend Java Developer

---
