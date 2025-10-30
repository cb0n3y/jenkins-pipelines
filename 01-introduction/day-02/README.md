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

See [01_first-pipeline.groovy](01_first-pipeline.groovy)

---

## 🧩 Observations

Splitting multiple `sh` commands increases overhead and build time.  
By merging commands into a single multi-line `sh` block, runtime improved from **13s → 4s**.  

See example: [01_better_code.groovy](homeworks/01_better_code.groovy)
