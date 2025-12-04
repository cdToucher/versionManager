# Version Management System

## Project Overview
This is a Java-based version management system designed to help software development teams manage different versions of their software products. The system provides functionality for tracking foundation versions, custom versions, deployment information, database initialization scripts, and more.

## Features
- User management with role-based access control
- Version management with CRUD operations
- Approval workflow for versions
- Export functionality for version packages (ZIP/PDF)
- RESTful API with Swagger documentation
- MySQL database integration
- Redis caching support

## Technology Stack
- Java 8
- Spring Boot 2.7.0
- Spring Data JPA
- MyBatis
- MySQL database
- Redis
- Spring Security
- Swagger API documentation
- iText PDF generation
- Apache POI

## Project Structure
```
src/main/java/com/example/vms/
├── controller/          # REST controllers
├── entity/             # JPA entities
├── repository/         # Data repositories
├── service/            # Business logic interfaces
├── service/impl/       # Business logic implementations
├── config/             # Configuration classes
└── util/               # Utility classes
```

## Setup Instructions

### Prerequisites
- Java 8 JDK
- Maven 3.x
- MySQL 8.0+
- Redis (optional)

### Installation Steps
1. Clone the repository
2. Create a MySQL database named `vms_db`
3. Update the database credentials in `application.properties` if needed
4. Build the project: `mvn clean install`
5. Run the application: `mvn spring-boot:run` or deploy the WAR file

### Default Credentials
- Admin user: `admin` / `admin123`
- Regular user: `user` / `user123`

### API Documentation
The API documentation is available at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API Docs: `http://localhost:8080/api-docs`

## Key Endpoints
- User Management: `/api/users`
- Version Management: `/api/versions`
- Export: `/api/export`

## Version Management Workflow
1. Create a new version (status: DRAFT)
2. Submit for approval (status: PENDING_APPROVAL)
3. Admin approves/rejects (status: APPROVED/REJECTED)
4. Export version package as ZIP or PDF

## Deployment
The application can be deployed as:
- Fat JAR: `java -jar version-management-system-1.0.0.jar`
- WAR file: Deploy to a servlet container like Tomcat

## Security
- Basic HTTP authentication is implemented
- Role-based access control (ADMIN, USER, DEVELOPER)
- Passwords are encrypted using BCrypt

## Export Functionality
- Export version information as PDF
- Export complete version package as ZIP (includes PDF, SQL, deployment info, etc.)
