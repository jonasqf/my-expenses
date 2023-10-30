# Project Overview
Welcome to the My Expenses Project. This project is designed to provide a tool for organize your finances and keep things organized.
This application and services are beeng developed with Java 21, Spring Boot, Mockito, JUnit, and Docker. 
This project offers a starting point for your development.

## Prerequisites
Before getting started, please ensure you have the following prerequisites installed:

Java 21: Ensure that you have Java 21 or a compatible version installed on your system.

Maven: You'll need Maven to manage project dependencies and build the application. You can download Maven from https://maven.apache.org/.

Docker: To containerize and run the application in a Docker container, make sure Docker is installed on your system. You can find Docker installation instructions at https://www.docker.com/get-started.

Getting Started
Clone this repository to your local machine:

```
bash
Copy code
git clone https://github.com/yourusername/finance-springboot-project.git
```

Navigate to the project directory:
```
bash
Copy code
cd my-expenses
```
Build the project using Maven:
```
bash
Copy code
mvn clean package
```
Running the Application
You can run the Spring Boot application locally using the following command:
```
bash
Copy code
java -jar target/my-expenses.jar
```
The application will start and be accessible at http://localhost:8080.

Running Tests
This project includes unit tests using JUnit and Mockito. To run the tests, execute the following command:
```
bash
Copy code
mvn test
```
Docker Integration
Building a Docker Image
To create a Docker image for this application, you need to have Docker installed and running. Execute the following commands to build the Docker image:
```
bash
Copy code
# Build the Docker image
docker build -t my-expenses
```
Running the Docker Container
Once the Docker image is built, you can run a container using the following command:
```
bash
Copy code
docker run -p 8080:8080 my-expenses
```
The application will be accessible at http://localhost:8080 from within the Docker container.

License
This project is licensed under the MIT License. You can find the license details in the LICENSE file.
