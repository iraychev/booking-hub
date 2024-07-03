<h1 align="center"></h1>

<p align="center">
  <em>This is a RESTful API built with Spring Boot for managing a booking system.</em>
</p>

## Features

- **Authentication**: JWT-based authentication for secure endpoints.
- **Database**: PostgreSQL integration for data persistence.
- **RESTful API**: Provides endpoints for various functionalities.

## Setup Instructions

1. **Clone the repository:**

   ```bash
   git clone github.com/iraychev/booking
   cd booking
   
2 **Configure PostgreSQL Database:**

Create a PostgreSQL database and update application.properties with your database configuration:

    spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    
3 **Run the Application:**

You can run the application using Maven or your IDE:
   
    ./mvnw spring-boot:run
or

Import the project into your IDE and run the SpringBootApplication class.

4 **Access the API:**

Once the application is running, you can access the API at http://localhost:8080.
