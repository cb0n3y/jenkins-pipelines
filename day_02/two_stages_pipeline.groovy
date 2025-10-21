pipeline {
    agent any

    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
            }
        }
        stage('Goodbye') {
            sh 'echo "Goodby from Jenkins"'
        }
    }
}