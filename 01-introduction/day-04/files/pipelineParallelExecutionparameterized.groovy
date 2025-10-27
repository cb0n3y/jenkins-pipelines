pipeline {
    agent any

    environment {
        HELM_URL = "https://raw.githubusercontent.com/helm/helm/master/scripts/get-helm-3"
    }

    parameters {
        string(name: 'HELM_VERSION', defaultValue: '3.18.0', description: 'Which Helm version to install')
        string(name: 'SYFT_VERSION', defaultValue: '1.34.2', description: 'Which Syft version to install')
    }

    stages {
        stage('Preparation') {
            parallel {
                stage('Install Helm') {
                    steps {
                        withEnv(["HELM_VERSION=${params.HELM_VERSION}"]) {
                            sh '''
                                echo "[+] Creating required folder ..."
                                mkdir -p $HOME/bin

                                echo "[+] Downloading and installing Helm ..."
                                export HELM_INSTALL_DIR="$HOME/bin"
                                curl -sSL $HELM_URL | bash -s -- --version v${HELM_VERSION} --no-sudo

                                export PATH="$HELM_INSTALL_DIR:$PATH"
                                helm version
                                helm version --short
                            '''
                        }
                    }
                }

                stage('Install Syft') {
                    steps {
                        withEnv(["SYFT_VERSION=${params.SYFT_VERSION}"]) {
                            sh '''
                                echo "[+] Downloading and installing Syft ..."
                                curl -sSL "https://github.com/anchore/syft/releases/download/v${SYFT_VERSION}/syft_${SYFT_VERSION}_linux_amd64.tar.gz" | tar -xz -C "$HOME/bin"
                                echo "[+] Syft installed successfully:"
                                "$HOME/bin/syft" version
                            '''
                        }
                    }
                }
            }
        }
    }
}
