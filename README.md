# Java Web Application

## Description

This project is a Java web application that uses the Spring Framework, Hibernate, and MapStruct for the backend, with a React frontend. It is deployed on an Apache Tomcat server and built and run using IntelliJ IDEA

## Prerequisites

Before you can build and run this project, make sure you have the following installed:

### For Backend

1. **Java Development Kit (JDK) 21**
    - Java 21 is recommended for this project.
    - Ensure that JDK 21 is installed and configured on your system.
    - Download it from the [Oracle website](https://www.oracle.com/in/java/technologies/downloads/) or use a package manager like `sdkman`.

2. **Apache Maven**
    - Maven version 3.9.9 is recommended for this project.
    - Maven is required for building the Java application.
    - Download it from the [Apache Maven website](https://maven.apache.org/download.cgi).

3. **Apache Tomcat 10**
    - Apache Tomcat 10 is recommended for this project.
    - Tomcat 10 is required for deploying the web application.
    - Download it from the [Apache Tomcat website](https://tomcat.apache.org/download-10.cgi) and follow the installation instructions.

4. **PostgresSQL**
    - PostgresSQL 16 is recommended for this project.
    - PostgresSQL is used as the database for this project.
    - Download it from the [PostgreSQL website](https://www.postgresql.org/download/).

### For Frontend

1. **Node.js and npm**
    - Node version 20 is recommended for this project.
    - Node.js is required to build the React application, and npm (Node Package Manager) is used for managing dependencies.
    - Download Node.js from the [Node.js website](https://nodejs.org/en/download/package-manager), which includes npm.

## Project Structure

- `src/main/java/` - Contains the Java source code for the backend.
- `src/main/resources/` - Contains the `applicationContext.xml` and other resources.
- `src/main/webapp/` - Contains the web application resources.
  - `myApp/` - Contains the React application.
      - `static/` - Contains static assets like `index.html` and other static files.
      - `react/` - Contains React components.
      - `static/react/` - Contains compiled React components.
  - `WEB-INF/` - Contains `web.xml` configuration file.

## Frontend

- The React application is located in `src/main/webapp/myApp`.
- Static assets are located in `src/main/webapp/myApp/static`.
- Ensure that the `.html` is accessible at `http://localhost:8080/`.

## Backend

- The backend uses Spring Framework for servlet configuration.
- The database connection is configured in `DatabaseContextFile.xml` located in `src/main/resources/`.
- The hibernate and other configurations are located in `applicationContext.xml` in `src/main/resources/`.
- XML configurations are maintained for different parts of the application configuration.

## Build and Run

1. **Set up Tomcat in your IDE**:
    - Download Tomcat and unzip it to your desired folder.
    - Configure Tomcat in your IDE and add it as a local server, selecting the folder where Tomcat was unzipped.

2. **Build the Project**:
    - In your Tomcat configuration, set the following Maven goals before launching:
        - `clean`
        - `generate-resources`
        - `build`
        - `build artifacts`
    - Ensure that the build artifacts are correctly set(War explode).
    - (Optional) You can also set the Application Context to / in the Tomcat configuration under the Deployment settings.
3. **Database Configuration**:
    - Configure the database connection in `DatabaseContextFile.xml` located in `src/main/resources/`.
    - Update the database username, password and jdbcUrl.

3. **Run the Project**:
    - Use the "Run" button in your IDE to start Tomcat.
    - Access your application at `http://localhost:8080/`.

## License

This project does not currently have a formal license. You are free to use, modify, and distribute the code for personal or educational purposes.

## Contributions

Contributions to this project are welcome. Please follow the standard process for contributing to open-source projects:
- Fork the repository.
- Create a new branch for your changes.
- Commit your changes and push them to your fork.
- Submit a pull request detailing your changes.