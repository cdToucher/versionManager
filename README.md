# Version Management System

## Project Overview
This is a Java-based version management system designed to help software development teams manage different versions of their software products. The system provides functionality for tracking foundation versions, custom versions, deployment information, database initialization scripts, user authentication and authorization, file management, and more.

## Features
- User authentication with registration and login
- Role-based access control (ADMIN, USER, DEVELOPER)
- Version management with CRUD operations
- Approval workflow for versions
- File management for versions
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
- File upload functionality

## Project Structure
```
src/main/java/com/anmi/vms/
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
- Authentication: `/api/auth`
- User Management: `/api/users`
- Version Management: `/api/versions`
- Version File Management: `/api/version-files`
- Role Management: `/api/roles`
- Export: `/api/export`

## Version Management Workflow
1. Register/login to the system
2. Create a new version (status: DRAFT)
3. Upload related files to the version
4. Submit for approval (status: PENDING_APPROVAL)
5. Admin approves/rejects (status: APPROVED/REJECTED)
6. Export version package as ZIP or PDF

## Database Schema

The application uses the following database tables:

```sql
-- Users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    role ENUM('ADMIN', 'USER', 'DEVELOPER') NOT NULL,
    created_at DATETIME,
    updated_at DATETIME,
    is_active BOOLEAN DEFAULT TRUE
);

-- Versions table
CREATE TABLE versions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    version_number VARCHAR(255) NOT NULL,
    foundation_version VARCHAR(255),
    type ENUM('FOUNDATION', 'CUSTOM') NOT NULL,
    description TEXT,
    database_init_sql MEDIUMTEXT,
    deployment_info TEXT,
    branch_name VARCHAR(255),
    custom_requirements TEXT,
    status ENUM('DRAFT', 'PENDING_APPROVAL', 'APPROVED', 'REJECTED') NOT NULL,
    created_by BIGINT,
    approved_by BIGINT,
    created_at DATETIME,
    updated_at DATETIME,
    approved_at DATETIME
);

-- Version Files table
CREATE TABLE version_files (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(500) NOT NULL,
    file_path VARCHAR(1000) NOT NULL,
    file_type VARCHAR(100),
    file_size BIGINT,
    description TEXT,
    version_id BIGINT NOT NULL,
    uploaded_by BIGINT,
    uploaded_at DATETIME,
    is_active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (version_id) REFERENCES versions(id)
);
```

## Security
- Basic HTTP authentication is implemented
- Role-based access control (ADMIN, USER, DEVELOPER)
- Passwords are encrypted using BCrypt
- Authentication endpoints for registration and login

## Export Functionality
- Export version information as PDF
- Export complete version package as ZIP (includes PDF, SQL, deployment info, etc.)

## File Management
- Upload files to specific versions
- Download files associated with versions
- Manage version-related documents and resources
