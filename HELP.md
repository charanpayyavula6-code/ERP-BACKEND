# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.charan.erp 1' is invalid and this project uses 'com.charan.erp1' instead.

## MySQL Setup

1. Open `mysql-schema.sql` in MySQL Workbench and execute the script. It creates the `erp2db` database and the `student` table.
2. For an existing database created with the old `age/dept/course` schema, run `mysql-migration.sql` once first. Existing rows retain their department; unavailable email, phone, and roll number values receive migration placeholders that should be replaced with real student data.
3. If the current table was created with a GPA limit of 4.0, run `mysql-gpa-range-migration.sql` once to raise the database constraint to 10.0.
4. Configure the application connection with environment variables before starting it:

	```powershell
	$env:DB_URL = "jdbc:mysql://localhost:3306/erp2db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
	$env:DB_USERNAME = "root"
	$env:DB_PASSWORD = "your-local-mysql-password"
	$env:AUTH_USERNAME = "admin"
	$env:AUTH_PASSWORD = "use-a-long-unique-password"
	$env:JWT_SECRET = "use-a-random-secret-of-at-least-32-bytes"
	.\mvnw.cmd spring-boot:run
	```

The application listens on port 8080 and validates the database schema at startup without altering it. The frontend origin `http://localhost:3000` is allowed by CORS. Do not commit database passwords.

## Authentication

All student endpoints require an `Authorization: Bearer <accessToken>` header. `POST /api/auth/token` exchanges the configured `AUTH_USERNAME` and `AUTH_PASSWORD` for a 15-minute access token and a 7-day refresh token. `POST /api/auth/refresh` exchanges a refresh token for a new pair; refresh tokens are one-time use. `POST /api/auth/logout` revokes the submitted refresh token. Refresh-token revocations are stored in memory and are cleared when the application restarts.

## Student API

The API base path is `/api/students`. `GET /api/students` returns a JSON array and accepts optional `search` and exact `department` filters. `GET`, `POST`, `PUT`, and `DELETE` support standard student CRUD; create and update require `name`, `email`, `phone`, `rollNo`, and `department`. GPA is limited to 0.0-10.0, attendance to 0-100, and optional `feeStatus`/`status` values must match the documented enum labels. Four sample students are inserted on startup only when the table is empty.

Example create request:

```json
{
	"name": "Aarav Sharma",
	"email": "aarav.sharma@example.edu",
	"phone": "+1-555-0101",
	"rollNo": "2024CS101",
	"department": "Computer Science",
	"semester": "4th Sem",
	"gpa": 3.82,
	"attendance": 94,
	"feeStatus": "Paid",
	"status": "Active",
	"admissionDate": "2024-08-15",
	"address": "12 Cedar Street",
	"avatarColor": "#4f46e5"
}
```

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/4.1.1/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.1.1/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/4.1.1/reference/web/servlet.html)
* [Spring Web Services](https://docs.spring.io/spring-boot/4.1.1/reference/io/webservices.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/4.1.1/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring Session for JDBC](https://docs.spring.io/spring-session/reference/)
* [HTTP Client](https://docs.spring.io/spring-boot/4.1.1/reference/io/rest-client.html#io.rest-client.restclient)
* [Reactive HTTP Client](https://docs.spring.io/spring-boot/4.1.1/reference/io/rest-client.html#io.rest-client.webclient)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Producing a SOAP web service](https://spring.io/guides/gs/producing-web-service/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

