# Library Manager API Documentation

Welcome to the documentation for the Library Manager API. This API is designed to manage library operations such as managing books, patrons, borrowing records, and more. Below you will find information on how to set up and run the API, as well as details on its features and endpoints.
## Prerequisites

- Java 17
- PostgreSQL
- Postman (optional for testing)

## Setup

1. Create a PostgreSQL database named `library-manager`.
2. Run the SQL script `script.sql` located in the `src/main/resources/database` folder to initialize the database schema.

## Running the Application

To run the application:

1. Navigate to the `LibraryApiApplication.java` file.
2. Run the application.

## Authentication

The API uses JSON Web Token (JWT) for authentication. You need to authenticate before accessing the endpoints.

Default user for testing:
- Email: admin@test.com
- Password: admin

## Endpoints

For detailed information on how to use the endpoints, please refer to the [Postman documentation](https://documenter.getpostman.com/view/32556647/2s9YyqiN5p).


