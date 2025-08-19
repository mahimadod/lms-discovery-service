pipeline {
    agent any

    stages {
        stage('Build JAR') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("mahimadod/lms-discovery-service:latest")
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withDockerRegistry([credentialsId: 'dockerhub-creds', url: '']) {
                    sh 'docker push mahimadod/lms-discovery-service:latest'
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                dir('../') {
                    sh 'docker-compose -f docker-compose.jenkins.yml down || true'
                    sh 'docker-compose -f docker-compose.jenkins.yml up -d'
                }
            }
        }
    }
}