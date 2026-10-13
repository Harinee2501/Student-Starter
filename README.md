# Course Registration System — DevOps Assessment

## 1. Project Overview

This project implements a Course Registration System with registration validation, automated tests, a Maven multi-module build, a Jenkins Declarative Pipeline, and a Kubernetes deployment using Minikube.

The application validates course registration capacity and enforces a maximum of five course registrations per student.

## 2. Technologies Used

- Java 17
- Maven
- JUnit 5
- Git and GitHub
- Jenkins Declarative Pipeline
- Docker Desktop
- Kubernetes
- Minikube
- Nginx

## 3. Git Workflow

Development was organized using Git branches and meaningful commits.

- `main` — contains the merged application changes.
- `course-registration` — used for registration boundary tests and validation fixes.
- `maven-multimodule` — contains the Maven multi-module refactor and Jenkins pipeline.

The registration changes were developed on a feature branch and merged into `main`. The Maven multi-module project and Jenkinsfile were committed on `maven-multimodule` and pushed to GitHub.

### Git Commands

```powershell
git branch
git log --oneline --graph --all
git status
```

## 4. Maven Multi-Module Structure

The project uses a parent POM and a child application module.

```text
Student-Starter/
├── pom.xml
├── Jenkinsfile
├── README.md
└── course-registration-app/
    ├── pom.xml
    ├── src/
    │   ├── main/java/
    │   └── test/java/
    └── target/
        ├── course-registration-app-1.0-SNAPSHOT.jar
        └── surefire-reports/
```

### Parent POM

The root `pom.xml`:

- Uses `pom` packaging.
- Defines shared project properties.
- Configures Java 17 and UTF-8.
- Manages the JUnit 5 dependency version through `dependencyManagement`.
- Defines shared Maven Compiler and Surefire plugin versions.
- Declares `course-registration-app` as a module.

### Child POM

The `course-registration-app/pom.xml`:

- Inherits group ID, version, and shared configuration from the parent.
- Declares JUnit 5 for testing without repeating its version.
- Packages the application as a JAR.

## 5. Build and Test

Run these commands from the repository root:

```powershell
mvn clean test
mvn package
```

- `mvn clean test` cleans previous build output, compiles the application, and runs the automated tests.
- `mvn package` executes the required build lifecycle and creates the application JAR.

### Test Results

The successful Maven test run reported:

- Parent project: `course-registration-parent` — SUCCESS
- Child module: `course-registration-app` — SUCCESS
- Tests run: 13
- Failures: 0
- Errors: 0

### Build Artifact

The generated JAR is located at:

`course-registration-app/target/course-registration-app-1.0-SNAPSHOT.jar`

### Test Reports

JUnit XML reports are generated under:

`course-registration-app/target/surefire-reports/`

## 6. Registration Validation and Change Request

### Course Capacity Boundary Fix

The registration capacity validation was corrected so that a course cannot accept a registration once its capacity has been reached.

### Maximum Course Registration Limit

A maximum of five course registrations per student was implemented.

- The fifth registration is allowed when the student has four registrations.
- The sixth registration is rejected.
- Rejected registration attempts must not modify the student's registration state.
- Existing registration validation checks are retained.

Automated tests cover the course capacity boundary and the five-course registration limit.

## 7. Jenkins Declarative Pipeline

The root-level `Jenkinsfile` defines the automated build pipeline.

The pipeline uses Linux `sh` commands and the Maven installation configured in Jenkins.

### Pipeline Stages

1. **Build and Test:** Runs `mvn clean test`.
2. **Package:** Runs `mvn package` after the build and test stage succeeds.
3. **Publish Test Reports:** Publishes JUnit XML reports from `course-registration-app/target/surefire-reports/TEST-*.xml`.
4. **Archive Artifact:** Archives `course-registration-app/target/*.jar` when the pipeline succeeds.

### Jenkins Execution Results

The pipeline was executed successfully in Jenkins.

- Parent Maven project: SUCCESS
- Child Maven module: SUCCESS
- JUnit test results: Recorded by Jenkins
- JAR artifact: Archived successfully
- Final build status: `SUCCESS`

The GitHub repository used by the Jenkins job is:

`https://github.com/Harinee2501/Student-Starter.git`

The pipeline job is configured to use the `maven-multimodule` branch and the root-level `Jenkinsfile`. It can be triggered manually using **Build Now**.

## 8. Kubernetes Deployment Using Minikube

Minikube was configured to use Docker Desktop as its container driver.

### Verify the Cluster

```powershell
minikube status
minikube kubectl -- get nodes
```

The Minikube control plane and node were running, and the node reported `Ready`.

### Deploy Nginx

An Nginx Deployment was created using image `nginx:1.27` with two initial replicas.

```powershell
minikube kubectl -- create deployment nginx-deployment --image=nginx:1.27 --replicas=2
```

### Expose Nginx Using NodePort

The Deployment was exposed using a NodePort Service named `nginx-service`.

```powershell
minikube kubectl -- expose deployment nginx-deployment --type=NodePort --port=80 --target-port=80 --name=nginx-service
```

The Service was configured to use NodePort `30080`.

The Service configuration was verified with:

```powershell
minikube kubectl -- get services
```

Expected Service port mapping:

`80:30080/TCP`

### Access the Application

The Minikube service URL was obtained using:

```powershell
minikube service nginx-service --url
```

Opening the generated URL in a browser should display the Nginx welcome page. With the Docker driver on Windows, the terminal used to run the service command may need to remain open while accessing the URL.

### Scale the Deployment

The Deployment was scaled from two replicas to three.

```powershell
minikube kubectl -- scale deployment nginx-deployment --replicas=3
minikube kubectl -- get deployments
minikube kubectl -- get pods
```

Verification showed three ready replicas.

### Verify Self-Healing

One Pod managed by the Deployment was deleted. Kubernetes automatically created a replacement Pod to maintain the desired replica count.

The replacement was verified by checking the Pods and Deployment again.

### Final Kubernetes Verification

```powershell
minikube kubectl -- get deployments
minikube kubectl -- get services
minikube kubectl -- get pods
```

The final verification showed three running Nginx Pods and the `nginx-service` NodePort mapping.

## 9. Conclusion

The project demonstrates Git-based development, Maven multi-module configuration, automated unit testing, a Jenkins Declarative Pipeline, and Kubernetes Deployment, Service, scaling, and self-healing operations using Minikube.

The build, test, pipeline, and Kubernetes results described above reflect the outputs obtained during the assessment.