# Employee & Department Management Backend Service

This is a powerful backend solution built with **Java**, **Maven**, **Spring Boot**, and **PostgreSQL** as the database. The service is designed to streamline employee and department management by providing a seamless RESTful API. With Spring Boot, the application offers an easy-to-use and production-ready environment, while PostgreSQL serves as the reliable database for storing employee records and department details. Whether you're adding employees, fetching department information, or managing employee assignments, this service has got you covered efficiently.

## Tech Stack

- **Java**: Leveraging the latest features of Java 17 for enhanced performance, security, and maintainability.
- **Maven**: Efficient project management and dependency handling to simplify builds and integrations.
- **PostgreSQL**: A reliable and scalable relational database used to store employee and department data.
- **Spring Boot**: An easy-to-use framework that enables the development of production-grade RESTful APIs.
- **JPA/Hibernate**: Simplifies database interactions with an Object-Relational Mapping (ORM) layer for smooth data handling.
- **JasperReports**: Integrated for dynamic report generation in PDF format, displaying employee data grouped by departments.

## Prerequisites
Before you begin, make sure you have the following software installed:

- **Java**: Make sure you have Java 17 installed on your machine.
- **Maven**: Ensure that Maven version 3.9.9 is installed for project management and build handling.
- **PostgreSQL**: Install PostgreSQL version 17.2 to manage the relational database.

## Setup & Installation

### Build the Project

Make sure Maven is properly installed, and then use it to build the project:

```bash
mvn clean install
```
### Run the Application
Once the project is built, you can run the application using the following command:

```bash
mvn spring-boot:run
```

### Testing the API
You can test the API using tools like Postman. Here is an example of how to use curl for testing:

**Fetch all employees:**
```bash
curl -X GET http://localhost:8080/api/employees
```

## API Endpoints
 - `GET /api/employees`: Gets a list of all employees.
 - `GET /api/departments`: Gets a list of all departments.
 - `GET /api/employees/department/{departmentId}`: Gets a list of employees in a specific department.
 - `GET /api/employees/{employeeId}`: Gets details of a specific employee by their `employeeId`.
 - `POST /api/employees/department/{departmentId}`: Adds a new employee to a department.
 - `DELETE /api/employees/{employeeId}`: Deletes a specific employee based on their unique `employeeId`.
 - `GET /api/reports/generateReport`: Generates a PDF report displaying employees grouped by their respective departments.
 - `PUT /api/employees/{employeeId}`: Updates the details of an existing employee based on their `employeeId`.

