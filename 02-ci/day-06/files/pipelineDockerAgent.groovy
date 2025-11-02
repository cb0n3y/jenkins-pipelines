pipeline {
    agent {
        docker {
            image 'node:18-alpine'
            label 'linux docker java21'
            reuseNode true
        }
    }

    environment {
        FILE_NAME = 'container'
    }

    stages {
        stage('With Docker') {
            steps {
                sh '''
                    echo "With Docker"
                    ls -lha
                    touch $FILE_NAME-yes.txt
                '''
            }
        }

        stage('Without Docker') {
            steps {
                sh '''
                    echo "Without Docker"
                    ls -lha
                    touch $FILE_NAME-no.txt
                '''
            }
        }
    }
}
