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
    }
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
- The first thing you will need is the Jenkins Plugin: **docker pipelines**.
- [Example Docker As a Build Environment](pipelineDockerEnv.groovy)
- Benefits of reproducible builds and isolated environments.
- You can specify a Docker image directly in your Jenkinsfile using:
```groovy
agent {
    docker {
        image 'node:18-alpine'
        label 'linux docker java21'
    }
}
```
- Always ensure the node running this stage has Docker installed and accessible by the Jenkins user.

---

### 🔧 Troubleshooting Jenkins While Using Docker in Jenkinsfile

- Common issues:
    - Docker daemon permissions (e.g., Jenkins user not in the docker group).
    - Container image pull failures (e.g., private registry credentials missing).
    - Network access problems when Jenkins containers try to reach GitHub or internal repos.
- Tips for debugging:
    - Check the Jenkins build log for the full Docker command Jenkins runs.
    - Verify connectivity using a simple step:

    ```groovy
    sh 'docker run --rm node:18-alpine echo "Docker works!"'
    ```
- Review /var/log/jenkins/jenkins.log and the Docker daemon logs (/var/log/messages or journalctl -u docker).
- If issues persist, test Docker manually on the same agent node where Jenkins runs.