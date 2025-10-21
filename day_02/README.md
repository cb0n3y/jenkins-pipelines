# Daily Summary

# Pipeline Structure

```groovy
pipelines {
    stages {
        stage('name') {
            sh 'echo "Learning Jenkins"'
        }
    }
}
```

## Post stage

### Options

- [always](https://www.jenkins.io/doc/book/pipeline/syntax/#post)

```groovy
pipelines {
    stages {
        stage('name') {
            echo "Learning Jenkins"
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
```

## Storing Artifacts

- success: Only run the steps in post if the current Pipeline’s or stage’s run has a "success" status, typically denoted by blue or green in the web UI.

```groovy
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
```

This won't work since Jenkins execute things in specific steps. The best way to save the artifacts,
is moving the instruction to clean the workspace right to the top of the first stage and delete the always block:

```groovy

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                
                cleanWs()

                echo 'Building a new laptop ...'
                sh 'mkdir -p build'
                sh 'touch build/computer.txt'
                sh 'echo "Mainboard" >> build/computer.txt'
                sh 'cat build/computer.txt'
                sh 'echo "Display" >> build/computer.txt'
                sh 'cat build/computer.txt'
                sh 'echo "Keyboard" >> build/computer.txt'
                sh 'cat build/computer.txt'
            }
        }
    }
    post{
        success {
            archiveArtifacts artifacts: 'build/**'
        }
    }
}
```

## Observations

I have noticed that running the jenkins pipeline can be improve with how the code is giving. As an
example, this code cost me 13 s 2s both stages:

```shell
pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                cleanWs()
                echo 'Building a new laptop ...'
                sh 'mkdir -p build'
                sh 'touch build/computer.txt'
                sh 'echo "Mainboard" >> build/computer.txt'
                sh 'cat build/computer.txt'
                sh 'echo "Display" >> build/computer.txt'
                sh 'cat build/computer.txt'
                sh 'echo "Keyboard" >> build/computer.txt'
                sh 'cat build/computer.txt'
            }
        }
    }
    post{
        success {
            archiveArtifacts artifacts: 'build/**'
        }
    }
}
```

Now, this other way of writting the code, cost me only 4s 2s for both stages:

```shell
pipeline {
    agent any

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
    post{
        success {
            archiveArtifacts artifacts: 'build/**'
        }
    }
}
```