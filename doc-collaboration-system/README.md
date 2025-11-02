# Document Collaboration System

This project is a multi-tenant document collaboration system built with Java Servlets.

## Modules

* `api`: The web-facing part of the application, containing servlets and filters.
* `service`: Contains the business logic of the application.
* `dao`: The data access layer, responsible for interacting with the database.
* `worker`: A background worker for processing asynchronous tasks.

## Local Development

To set up the local development environment, you will need Docker and Docker Compose installed.

1.  **Start the services:**
    ```bash
    docker-compose up
    ```

2.  **Build the project:**
    ```bash
    mvn clean package
    ```
