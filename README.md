# Hybrid-Selenium-RestAssured-Automation-Suite

## 🌟 Introduction
Welcome to this all-in-one automation framework designed for both **web UI** and **REST API** testing. It combines powerful tools like **Selenium WebDriver**, **RestAssured**, and **TestNG** to validate real-world user journeys and backend APIs in a clean, maintainable, and scalable setup.

---

## 📌 What This Project Does

### Web Testing – Amazon.eg Workflow

- A complete purchase journey on [Amazon Egypt](https://www.amazon.eg/), from login to checkout
- Covers login, filtering products, adding to cart, checkout, and order confirmation
- Applies best practices like Page Object Model (POM) and method chaining

### API Testing – Reqres.in Users API

- User management operations (create, retrieve, update) on the [Reqres API](https://reqres.in/).
- Uses RestAssured for request building, execution, and validation
- Built with clean service layer, POJOs, and validation utilities

---

## 🛠 Tools & Technologies

| Tool                | Purpose                            |
|---------------------|------------------------------------|
| **Java 24**         | Language for development           |
| **Maven**           | Dependency & build management      |
| **Selenium**        | UI automation                      |
| **Rest Assured**    | API testing                        |
| **TestNG**          | Test execution and grouping        |
| **Jackson**         | JSON serialization/deserialization |
| **SLF4J + Logback** | Logging and debugging              |

---

## 📦 Dependencies
The following dependencies are defined in the `pom.xml` to power the framework:

| Dependency                | Version       | Purpose                                      |
|---------------------------|---------------|----------------------------------------------|
| `selenium-java`           | 4.32.0        | Automates browser interactions for UI testing |
| `testng`                  | 7.9.0         | Manages test execution and assertions        |
| `rest-assured`            | 5.5.1         | Facilitates API request and response testing |
| `jackson-databind`        | 2.19.0        | Serializes/deserializes JSON for API tests   |
| `slf4j-api`               | 2.0.17        | Provides logging abstraction                 |
| `logback-classic`         | 1.5.17        | Implements SLF4J with Logback for logging    |

---

## 🔧 Prerequisites

Before you begin, make sure the following tools are installed:

- Java 24 (JDK)
- Maven
- Chrome browser
- Git (for cloning the repo)

---

## 📁 Project Structure

```
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── api/
│   │   │   │   ├── models/         # Data models (e.g., User)
│   │   │   │   ├── services/       # API logic (e.g., UserService)
│   │   │   │   ├── utils/          # API helpers (e.g., ValidationUtil)
│   │   │   ├── config/             # Configuration utilities
│   │   │   ├── ui/
│   │   │   │   ├── driver/         # WebDriver management
│   │   │   │   ├── pages/          # Page objects (e.g., CartPage)
│   │   │   │   ├── utils/          # UI utilities (e.g., ElementActions)
│   ├── resources/
│   │   ├── logback.xml/            # Logging configuration
│   │   ├── api/
│   │   │   ├── config.properties   # API settings
│   │   ├── ui/
│   │   │   ├── config.properties   # UI settings
│   ├── test/
│   │   ├── java/
│   │   │   ├── api/
│   │   │   │   ├── tests/          # API test cases
│   │   │   ├── ui/
│   │   │   │   ├── tests/          # UI test cases
│   ├── resources/
│   │   ├── api/
│   │   │   ├── payloads/           # API test data (JSON)
│   │   ├── ui/
│   │   │   ├── ShippingData.json   # UI test data
├── pom.xml                         # Maven configuration
├── README.md                       # Project guide
```

---

## ⚙️ Setup Instructions
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/el-sherbini/Hybrid-Selenium-RestAssured-Automation-Suite.git
   cd OmniTest
   ```

2. **Install Dependencies**:
   ```bash
   mvn clean install
   ```

3. **Configure WebDrivers**:
   - Install ChromeDriver (or other browser drivers) compatible with your browser.
   - Update `ui/config.properties` or set system properties for WebDriver paths.

4. **Set Configuration**:
   - **UI** (`ui/config.properties`):
     ```properties
     BASE_URL=https://www.amazon.eg/
     BROWSER=Chrome
     EMAIL=your-email@example.com
     PASSWORD=your-password
     ```
   - **API** (`api/config.properties`):
     ```properties
     BASE_URL=https://reqres.in/api
     CONTENT_TYPE=application/json
     API_KEY=your-api-key
     ENDPOINT=/users
     ```

5. **Validate Test Data**:
   - Confirm `ui/ShippingData.json` has valid shipping details.
   - Ensure `api/payloads/` contains `createUser.json` and `updateUser.json`.

---

## 🏃 Running Tests
- **All Tests**:
  ```bash
  mvn test
  ```

- **UI Tests**:
  ```bash
  mvn test -Dtest=AddToCartTest
  ```

- **API Tests**:
  ```bash
  mvn test -Dtest=UserTest
  ```

- **Log Output**:
  Logs are displayed in the console. Adjust `logback.xml` for file-based logging.

---

## ✅ Features

#### 🌐 UI (Amazon.eg)
- User login
- Menu navigation
- Product filtering
- Product sorting by price
- Continue adding products to cart that below specific limit
- Verify cart summary match the added products
- Checkout flow including shipping
- Order summary and price validation
#### 📡 API (Reqres.in)
- POST request to create a new user
- GET request to retrieve user details
- PUT request to update user information

---

## 📜 Test Scenarios

### 🛒 UI Scenarios
*File*: `src/test/java/ui/tests/AddToCartTest.java`
1. Navigate to [Amazon Egypt](https://www.amazon.eg/) and log in with credentials.
2. Access the "All" menu, select "Video Games," then "All Video Games."
3. Apply filters: "Free Shipping" and "New" condition.
4. Sort products by price (high to low).
5. Add products costing less than 15,000 EGP to the cart, paginating if needed.
6. Verify all added products appear in the cart with correct prices.
7. Proceed to checkout, enter shipping details, and select cash-on-delivery.
8. Confirm the total amount, including shipping and other fees, is accurate.

### 🔌 API Scenarios
*File*: `src/test/java/api/tests/UserTest.java`
1. **Create User**:
   - Send a POST request to `/api/users` with user details (name, job).
   - Verify 201 status and matching response data.
2. **Retrieve User**:
   - Send a GET request to `/api/users/{id}` using the created user’s ID.
   - Confirm 200 status and data consistency.
3. **Update User**:
   - Send a PUT request to `/api/users/{id}` with updated details.
   - Ensure 200 status and updated fields are correct.

---

## 🧠 Design Principles

### 📑 Page Object  **Page Object Model (POM)**
Organizes UI elements and actions into modular classes, reducing maintenance overhead.

### 🔐 **Thread-Safe WebDriver**
Employs `ThreadLocal` in `DriverFactory` for safe, parallel test execution.

### 📈 **Data-Driven Framework**
Uses JSON files (`ShippingData.json`, `createUser.json`, `updateUser.json`) for reusable test inputs.

### 🛠 **Error Tolerance**
Combines try-catch blocks, soft assertions, and WebDriverWait for stable test runs.

### 📜 **Structured Logging**
SLF4J and Logback deliver clear, hierarchical logs for easy debugging.

