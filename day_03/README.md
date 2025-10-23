# Daily Summary


## What Have We Learned?

### 🐚 What is the Shell?

- The Jenkins sh step runs commands in a shell environment (typically /bin/bash on Linux agents).
- Each sh block starts a new shell, so environment variables don’t persist between separate steps unless explicitly exported or passed.

---

### ⏹️ Manually interrupting the pipeline execution.

- You can stop a running pipeline manually from the Jenkins UI.
- In scripted or declarative pipelines, you can also use error("message") or currentBuild.result = 'ABORTED' to stop execution programmatically.

---

### Combining multiple shell

Multiple sh steps can be combined into one block for efficiency and to share context:

```groovy
sh '''
  echo "Running tests..."
  ./gradlew test
  echo "Tests complete."
'''
```

- This reduces startup overhead and avoids losing variables or state between separate steps.

[Example Jenkinsfile](Jenkinsfile)

---

### Test Stage

- The Test stage is used to verify code functionality after build.
- It can run unit tests, integration tests, or static analysis tools.

[Example Test Stage](files/test-stage)

---

### Testing The Build

- Validates build artifacts before deployment.
- Ensures that outputs (e.g., JARs, Docker images) meet expected standards.

[Example Testing The Build](files/testing-build-artifacts)
