pipeline {
    agent any

    environment {
        HELM_URL="https://raw.githubusercontent.com/helm/helm/master/scripts/get-helm-3"
        HELM_VERSION="3.18.0"
        SYFT_VERSION="1.34.2"
    }

    stages {
        stage('Preparation') {
            parallel {
                stage('Install Helm') {
                    step {
                        sh '''
                            echo "[+] Creating required folder ..."
                            mkdir -p $HOME/bin

                            echo "[+] Downloading and installing Helm ..."
                            export HELM_INSTALL_DIR="$HOME/bin"
                            # curl -sSL https://raw.githubusercontent.com/helm/helm/master/scripts/get-helm-3 | bash -s -- --version v${HELM_VERSION} --no-sudo
                            curl -sSL $HELM_URL | bash -s -- --version v${HELM_VERSION} --no-sudo

                            export PATH="$HELM_INSTALL_DIR:$PATH"
                            helm version
                            helm version --short
                        '''
                    }
                }

                stage('Install Sift') {
                    step {
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
