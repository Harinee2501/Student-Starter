
pipeline {
    agent any
    tools {
        maven 'Maven-3'
    }

    stages {
        stage('Build and Test') {
            steps {
                echo 'Building and testing the Course Registration application'
                sh 'mvn clean test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging the application'
                sh 'mvn package'
            }
        }
    }

    post {
        always {
            junit testResults: 'course-registration-app/target/surefire-reports/TEST-*.xml',
                  allowEmptyResults: false
        }

        success {
            archiveArtifacts artifacts: 'course-registration-app/target/*.jar',
                             fingerprint: true
        }

        failure {
            echo 'Build or tests failed. The JAR will not be archived.'
        }

        cleanup {
            echo 'Pipeline execution finished'
        }
    }
}
