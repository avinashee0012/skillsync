# SkillSync — Job & Skill Match Platform

SkillSync is a modern job and skill matching platform that intelligently connects job seekers with relevant opportunities based on their skills, experience, and learning progress. Built with Java 17 and Spring Boot 3, this full-stack web application is ideal for showcasing backend development skills, API design, authentication, and relational database handling.

---

## Features

### User Module
- User Registration & Login with JWT
- Secure role-based access (USER / ADMIN)
- Profile Management (Add bio, skills, experience)
- Upload resume (future enhancement: auto-skill extraction)

### Skill Analysis
- Add verified skills or take assessments (optional)
- View skill match percentage for each job
- Receive recommendations based on missing skills

### Job Matching
- Browse jobs filtered by role, location, tech stack
- Apply for jobs directly
- Receive job alerts via email (cron or scheduled tasks)

### Admin Panel
- Manage users, job postings
- Create/Edit/Delete job opportunities
- Bulk email to notify matched users

### Dashboard
- Personalized dashboard with job stats, skills gap
- View job recommendations and history

---

## Tech Stack

### Backend
- Java 17
- Spring Boot 3
- Spring Data JPA (Hibernate)
- Spring Security + JWT
- RESTful API Design
- MySQL 8

### Frontend
- Thymeleaf (or React – optional)
- HTML5, CSS3, Bootstrap 5
- JavaScript (ES6)

### Tools & Utilities
- Lombok
- Spring DevTools
- Swagger / OpenAPI
- Bean Validation (Jakarta)
- ModelMapper / MapStruct

---

## Project Structure

```
src/
 └── main/
     ├── java/com/skillsync/
     │   ├── controller/
     │   ├── service/
     │   ├── repository/
     │   ├── model/
     │   ├── config/
     │   └── dto/
     └── resources/
         ├── application.properties
         └── templates/
```

---

## API Documentation

- Swagger UI: `/swagger-ui/index.html`
- API secured with JWT
- Sample Headers: `Authorization: Bearer <token>`

---

## Setup Instructions

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8+
- Postman (for API testing)

### Run Locally
```bash
# Clone the repository
git clone https://github.com/avinashee0012/skillsync.git
cd skillsync

# Create MySQL DB
CREATE DATABASE skillsync;

# Configure credentials in src/main/resources/application.properties

# Build and run the application
./mvnw spring-boot:run
```

- Access App: `http://localhost:8080/`
- Access Swagger: `http://localhost:8080/swagger-ui/index.html`

---

## Testing

- Unit Tests: JUnit 5 + Mockito
- Controller Tests: Spring Boot Test + MockMvc
- Security: JWT token-based auth testing

---

## Future Enhancements

- Resume parser using Apache Tika or OpenNLP
- Gamified skill assessments (MCQ, coding challenges)
- Integration with external job boards (LinkedIn, Indeed)
- User progress tracking and smart upskilling paths
- Full React + REST frontend

---

## Author

**Avinash Pathak**  
Backend Developer | Java Enthusiast  
[LinkedIn](https://linkedin.com/in/your-profile)  
your.email@example.com

---

## License

This project is licensed under the [MIT License](LICENSE).