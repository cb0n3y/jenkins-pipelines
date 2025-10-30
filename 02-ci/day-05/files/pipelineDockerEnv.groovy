pipeline {
    agent any

    stages {
        stage('w/o docker') {
            steps {
                echo "Without docker"
            }
        }

        stage('with docker') {
            agent {
                docker {
                    image 'node:18-alpine'
                    label 'linux docker java21'
                }
            }
            steps {
                echo "With docker"
                sh 'npm --version'
            }
        }
    }
}
