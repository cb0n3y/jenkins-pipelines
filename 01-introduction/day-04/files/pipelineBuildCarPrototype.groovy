pipeline {
    agent any

    environment {
        BUILD_ART_PATH = 'cars/prototype'
        BUILD_ART_NAME = 'design.txt'
    }

    parameters {
        string(name: 'COMPONENTS', defaultValue: '4', description: 'How many components should the car have')
    }

    stages {
        stage('Build') {
            steps {
                sh 'echo "[>] Cleaning working space..."'
                cleanWs()

                sh '''
                    echo "[>] Building a new car prototype..."
                    mkdir -p $BUILD_ART_PATH

                    echo "[>] Creating initial file $BUILD_ART_NAME..."
                    touch $BUILD_ART_PATH/$BUILD_ART_NAME

                    # ------ Wheels ------
                    echo "[>] Creating Wheels..."
                    echo "[*] Wheels" >> $BUILD_ART_PATH/$BUILD_ART_NAME

                    # ------ Chasis ------
                    echo "[>] Creating Chasis..."
                    echo "[*] Chasis" >> $BUILD_ART_PATH/$BUILD_ART_NAME

                    # ------ Spotlight ------
                    echo "[>] Creating Spotlight..."
                    echo "[*] Spotlight" >> $BUILD_ART_PATH/$BUILD_ART_NAME

                    # ------ Motor ------
                    echo "[>] Creating Motor..."
                    echo "[*] Motor" >> $BUILD_ART_PATH/$BUILD_ART_NAME
                    ls -lhr
                '''
            }
        }

        stage('Testing') {
            steps {
                withEnv(["COMPONENTS=${params.COMPONENTS}"]) {
                    sh '''
                        echo "[>] Checking build..."
                        ACTUAL_COUNT=$(wc -l < "$BUILD_ART_PATH/$BUILD_ART_NAME")
                        echo "[i] Expected components: $COMPONENTS"
                        echo "[i] Found components: $ACTUAL_COUNT"

                        if [ "$ACTUAL_COUNT" -eq "$COMPONENTS" ]; then
                            echo "[+] Build looks good!"
                            cat "$BUILD_ART_PATH/$BUILD_ART_NAME"
                        else
                            echo "[!] Build failed: expected $COMPONENTS, found $ACTUAL_COUNT"
                            exit 1
                        fi
                    '''
                }
            }
        }

    }

    post {
        success {
            archiveArtifacts artifacts: 'cars/prototype/**'
        }
    }
}
