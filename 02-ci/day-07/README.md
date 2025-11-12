# 📘 Daily Summary


## 🧠 What Have We Learned?

###

---

### ⚙️ Assignment - Running Test

Learned how to execute unit tests in Jenkins pipelines using npm test or similar commands inside a Build stage.

- To test manually at first, run: `npm test` within the application folder.

```bash
npm test
```

---

### ⚙️ Assignment - Add Test Stage to the Pipeline

Added a dedicated Test stage to the Jenkinsfile to separate **Build** and **Test** responsibilities for clearer CI structure.

✅ What I did:

- Created a new stage called Test.
- Used a script block to check if the build artifact index.html exists.
- If missing, failed the pipeline gracefully using error().
- Executed npm test to run all unit tests.

💡 Takeaways

- The `script` block lets you use Groovy logic (if, else, etc.) inside a declarative pipeline.
- `fileExists()` helps validate build artifacts before running tests.
- `error()` is the proper way to mark a pipeline as failed programmatically.
- Keeping Build and Test stages separate improves **readability**, **maintainability**, and **debugging**.

📂 [Pipeline Example Script](iles/pipeAssignment01.groovy)
📄 [Original Jenkinsfile in forked repo](https://github.com/cb0n3y/learn-jenkins-app.git)  

---

### 🧾 Publishing a JUnit Test Report

Configured Jenkins to collect and display JUnit test results using the junit '**/junit.xml' post-build step.

---

### 💬 Using Comments in Jenkinsfile

Used inline comments (// and /* */) to document pipeline logic and improve readability for other developers.

---

### 🎭 Running E2E Tests with Playwright

Integrated Playwright end-to-end tests into the pipeline, ensuring browser-level validation of application behavior.

💡 Takeaways & Lessons Learned

**Jenkins & CI/CD**

- When using `agent none`, you must specify an `agent` for every stage that requires workspace execution.
- Always specify the correct Jenkins agent label when using Docker or other environment-specific tools.
- Ensure the worker node running the job has Docker installed and accessible.
- Example fix for a Dockerized stage:
```groovy
agent {
    docker {
        image 'mcr.microsoft.com/playwright:v1.56.1-jammy'
        label 'linux docker java21'
        reuseNode true
    }
}
```
- `stash`/`unstash` is essential for sharing build artifacts between stages. Remember that unstashed files are placed relative to the workspace root.
This tells Jenkins to run the pipeline on the node where Docker is available, avoiding `docker: not found` errors.

---

### 📊 Publishing an HTML Report

Published Playwright’s HTML test reports in Jenkins with the HTML Publisher plugin for easy visualization of test outcomes.

```groovy
npx playwright test --report=html

...

post {
        always {
            node('linux docker java21') {
                junit 'test-results/junit.xml'
                publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, icon: '', keepAll: false, reportDir: 'playwright-report', reportFiles: 'index.html', reportName: 'Playwright HTML Report', reportTitles: '', useWrapperFileDirectly: true])
                cleanWs()
            }
        }
    }
```
