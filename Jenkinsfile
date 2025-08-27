pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials')
        DOCKER_IMAGE = "mahimadod/lms-discovery-service"
        JAVA_HOME = tool name: 'JDK17', type: 'jdk'
        MAVEN_HOME = tool name: 'Maven3.9.9', type: 'maven'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/mahimadod/lms-discovery-service.git'
            }
        }

        stage('Build & Test') {
            steps {
                withEnv(["JAVA_HOME=${env.JAVA_HOME}", "PATH+MAVEN=${env.MAVEN_HOME}/bin"]) {
                    sh 'mvn clean install'
                }
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub-credentials') {
                        def customImage = docker.build("${DOCKER_IMAGE}:${env.BUILD_NUMBER}")
                        customImage.push()
                        // Also tag latest and push
                        customImage.tag('latest')
                        customImage.push('latest')
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying Docker container...'
                // Example: run docker container on Jenkins node (adjust as needed)
                sh '''
                docker rm -f lms-discovery || true
                docker run -d --name lms-discovery -p 8761:8761 ${DOCKER_IMAGE}:${env.BUILD_NUMBER}
                '''
            }
        }
    }
}