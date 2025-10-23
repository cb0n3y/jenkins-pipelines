pipeline {
    stages {
        stage('Build') {
            steps {
                cleanWs()

                sh '''
                    echo "Building a new laptop ..."
                    mkdir -p build
                    touch build/computer.txt
                    echo "Mainboard" >> build/computer.txt
                    cat build/computer.txt
                    echo "Display" >> build/computer.txt
                    cat build/computer.txt
                    echo "Keyboard" >> build/computer.txt
                    cat build/computer.txt
                '''
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'build/**'
        }
    }
}