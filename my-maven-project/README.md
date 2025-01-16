# My Maven Project

This is a simple Maven project that demonstrates the structure and functionality of a basic Java application.

## Project Structure

```
my-maven-project
├── src
│   ├── main
│   │   ├── java
│   │   │   └── App.java
│   │   └── resources
│   └── test
│       ├── java
│       │   └── AppTest.java
│       └── resources
├── pom.xml
└── README.md
```

## Description

- **App.java**: This is the main class of the application. It contains the `main` method which serves as the entry point for the application.
- **AppTest.java**: This file contains the test class for `App`. It includes test methods to verify the functionality of the `App` class.
- **pom.xml**: The Project Object Model configuration file for Maven. It specifies the project dependencies, build configuration, and plugins.

## How to Build and Run

1. Ensure you have Maven installed on your machine.
2. Navigate to the project directory.
3. Run the following command to build the project:

   ```
   mvn clean install
   ```

4. To run the application, use the command:

   ```
   mvn exec:java -Dexec.mainClass="App"
   ```

## Running Tests

To run the tests, execute the following command:

```
mvn test
```

## License

This project is licensed under the MIT License.