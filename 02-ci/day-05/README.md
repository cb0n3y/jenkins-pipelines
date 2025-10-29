# Daily Summary

## Prerequisites

Before starting the exercises in this daily summary, ensure you have:

- **Jenkins installed** (master and agents).
- **Jenkins plugins**:
  - Docker Pipeline
  - Publish HTML
- **Node.js and npm** (via Volta or system installation)
- **Docker installed** on at least one agent node. Refer to: [Docker Installation](files/centos-docker-installation.md)
- SSH keys or personal access tokens for GitHub access.
- Labels configured for Jenkins agents if using `docker { image ... }` stages.

---

## What Have We Learned?

###

---

### 🧩 Introduction to CI

- Understanding Continuous Integration (CI) concepts.
- Benefits of CI in DevOps pipelines.

---

### 🕸️ Creating a GitHub Account

- Setting up a GitHub account for version control.
- Configuring SSH keys or personal access tokens for Jenkins integration.

---

### 🏗️ Website Project Overview

- Overview of the example website used in CI exercises.
- Installing required Tools.


```shell
cd /path/to/learn-jenkins-app

# Install Volta
curl https://get.volta.sh | bash
source ~/.bashrc # if this doesn't work, close your terminal and open a new one

# Install Node 18 (and npm)
volta install node@18
volta install npm@9

# Clean old dependencies (if any)
rm -rf node_modules package-lock.json

# Install dependencies
npm install

# Start the app
npx react-scripts start
```

Now we can start building our pipeline:

```groovy
pipeline {
    agent any

    stages {
        stage('w/o docker') {
            steps {
                echo "Without docker"
            }
        }
        
        stage('with docker') {
            agent {
                docker {
                    image 'node:18-alpine'
                    // this labels must match exactly the labels configured on the agent node
                    // All labels are separeted by spaces, not commas.
                    label 'linux docker java21'
                }
            }
            steps {
                echo "With docker"
            }
        }
    }Jenkins agent labels must match what you specify in the pipeline.Jenkins agent labels must match what you specify in the pipeline.
}
```

💡 Key Takeaways

- Docker must be installed on at least one agent node. [Docker Installation](files/centos-docker-installation.md)
- Jenkins agent labels must match what you specify in the pipeline. [Example Label configuration in the pipeline](files/pipelineLabels.groovy)

# TODO

- Project structure and key files Jenkins will build.
- Reference repository: [learn-jenkins-app](https://github.com/vdespa/learn-jenkins-app.git)

---

### 🐳 Using Docker as a Build Environment

- Running Jenkins agents in Docker containers.

The first thing you will need is the Jenkins Plugin: **docker pipelines**.



- Benefits of reproducible builds and isolated environments.

---

### 🔧 Troubleshooting Jenkins While Using Docker in Jenkinsfile

- Common issues with Jenkins in Docker.
- Checking container logs and permissions.
- Networking considerations for Git access.

---

### 📂 Workspace Synchronization

- Understanding Jenkins workspaces.
- Using `cleanWs()` to reset the workspace.
- Sharing artifacts between stages and builds.

---

### 🌱 Using a Git Repository in Jenkins

- Connecting Jenkins jobs to a Git repository.
- Cloning, fetching, and checking out branches.

---

### ⚙️ Building the Project

- Using `sh` steps to execute build commands.
- Running Docker commands inside pipelines.
- Archiving build artifacts.

---

### 🏛️ Revisiting Jenkins Architecture

- Overview of Jenkins master-agent architecture.
- How declarative pipelines execute across agents.

---

### 📝 Assignments

- **Assignment 1:** Create a simple CI pipeline for the website project.
- **Assignment 2:** Integrate Docker-based build steps.
- **Assignment 3:** Implement workspace cleanup and artifact archiving.
- **Assignment 4:** Troubleshoot a failing build and document the solution.

---

### 🎭 Running E2E Tests with Playwright

- Automating end-to-end tests using Playwright.
- Integrating tests into the Jenkins pipeline.

---

### 📊 Publishing an HTML Report

- Collecting and publishing test results or reports.
- Using Jenkins `publishHTML` plugin or similar.

---

### ⚡ Running Stages in Parallel

- Running multiple stages or branches simultaneously.
- Speeding up pipelines for independent tasks.

---

### 🌊 Jenkins Blue Ocean Plugin

- Visualizing pipelines with the Blue Ocean interface.
- Easier navigation of stages, parallel execution, and history.

---

### 🏗️ How to Structure a Pipeline

- Organizing stages, environment variables, and steps.
- Best practices for readability and maintainability.

---

### ⬆️ How to Update Jenkins and Its Plugins

- Keeping Jenkins core and plugins up-to-date.
- Managing plugin dependencies safely.
