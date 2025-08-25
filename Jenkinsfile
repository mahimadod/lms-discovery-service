pipeline {
    agent {
            docker {
                image 'maven:3.8.6-openjdk-17'
            }
        }

    environment {
        IMAGE_NAME = 'mahimadod/lms-discovery-service'  // change per repo
        IMAGE_TAG = "${env.BUILD_NUMBER}"               // unique per build
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("${IMAGE_NAME}:${IMAGE_TAG}")
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('', 'dockerhub-creds') {
                        dockerImage.push()
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Replace with path to your k8s yaml for this service in repo
                    sh "kubectl apply -f k8s/deployment.yaml"
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    // Wait for pod rollout and verify
                    sh "kubectl rollout status deployment/${env.SERVICE_NAME} --timeout=120s"

                    // Optional: Run a basic health check curl, change port/path accordingly
                    sh '''
                      POD_NAME=$(kubectl get pods -l app=${SERVICE_NAME} -o jsonpath="{.items[0].metadata.name}")
                      kubectl exec $POD_NAME -- curl -f http://localhost:8080/actuator/health
                    '''
                }
            }
        }
    }

    post {
        failure {
            echo "Build failed"
        }
    }
}
