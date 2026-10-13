# Course Registration

## Project Structure

Maven parent-child structure.

- **Parent POM (`pom.xml`)**: Defines shared project properties, Java version, JUnit version, dependency management, plugin versions, and the application module.
- **Child module (`course-registration-app/pom.xml`)**: Inherits from the parent POM, declares the JUnit 5 test dependency, and builds the Course Registration application as a JAR.


## Maven Configuration

The parent POM manages the Java compiler configuration and JUnit 5 dependency version. The child POM inherits the parent configuration and declares JUnit without repeating its version.

The project uses Java 17, JUnit 5, Maven Compiler Plugin, and Maven Surefire Plugin.

## Build and Test Commands


```powershell
mvn clean test
mvn package
```

- `mvn clean test` removes previous build output, compiles the module, and executes the automated tests.
- `mvn package` runs the build lifecycle, executes tests, and packages the application as a JAR.

## Build and Test Evidence

The root-level `mvn clean test` command completed successfully through the Maven reactor.

- Parent project: `course-registration-parent` — SUCCESS
- Child project: `course-registration-app` — SUCCESS
- Tests run: 13
- Failures: 0
- Errors: 0

The packaged JAR is located at:

`course-registration-app/target/course-registration-app-1.0-SNAPSHOT.jar`

## Test Reports

JUnit XML reports are generated under:

`course-registration-app/target/surefire-reports/`
