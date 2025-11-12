pipeline {
    agent {
        docker {
            image '4tu3y/ubuntu-devops-grype:24.04'
            label 'linux docker java21'
            reuseNode true
            registryCredentialsId 'dockerhub'
        }
    }

    environment {
        BUILD_DIR = "${WORKSPACE}/build"
    }

    stages {
        stage('Preparation') {
            steps {
                echo "[+] Creating build folder and syft cache..."
                sh 'mkdir -p $BUILD_DIR/.syft-cache'
            }
        }

        stage('Generate SBOM File') {
            steps {
                echo "[+] Generating SBOM file..."
                sh 'SYFT_CACHE_DIR=$BUILD_DIR/.syft-cache syft 4tu3y/ubuntu-devops:24.04 -o json > $BUILD_DIR/sbom.json'
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'build/**'
            cleanWs()
        }
        failure {
            echo "[✖] Pipeline failed. Cleaning workspace..."
            cleanWs()
        }
    }
}
