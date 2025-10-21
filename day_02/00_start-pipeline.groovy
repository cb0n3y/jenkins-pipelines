pipelines {
    stages {
        stage('name') {
            echo "Learning Jenkins"
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'build/**'
        }
        always {
            cleanWs()
        }
    }
}