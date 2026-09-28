pipeline {
    agent any

    environment {
        GRID_URL = 'http://selenium-hub:4444'
        TEST_GROUP = 'tests/UI'
    }

    stages {
        stage('Build') {
            steps {
                sh './gradlew clean build -x test --no-daemon'
            }
        }

        stage('UI Tests') {
            steps {
                sh './gradlew test --no-daemon'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
            allure includeProperties: false, jdk: '', results: [[path: 'build/allure-results']]
        }
        success {
            echo 'Тесты прошли успешно'
        }
        failure {
            echo 'Тесты упали'
        }
    }
}