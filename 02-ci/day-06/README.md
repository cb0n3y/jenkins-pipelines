# Daily Summary


## What Have We Learned?

###

---

### 📂 Workspace Synchronization

💡 Lesson Learned — About `reuseNode true`

- Use `reuseNode true` to reuse the same workspace across stages running on the same node.
- ⚠️ However, if your first stage runs on a node **without Docker** and a later stage tries to use Docker with `reuseNode true`, the pipeline will fail (`docker: command not found`).
- To avoid this:
  - Start your pipeline with the Docker stage first, so Jenkins selects a node with Docker right from the beginning.

✅ Example:
```groovy
agent {
    docker {
        image 'node:18-alpine'
        label 'linux docker java21'
        reuseNode true
    }
}

💡 Observation — Defining the Docker Agent at the Pipeline Level

When multiple stages depend on Docker, it’s cleaner and safer to define the Docker agent at the pipeline level, instead of per stage.

This ensures:
- The same workspace and container are reused.
- No node-switching issues occur.
- The build remains consistent and reproducible.
- [Example Docker Agent](files/pipelineDockerAgent.groovy)

---

### 🌱 Using a Git Repository in Jenkins

💡 Jenkins retrieves your source code directly from Git repositories defined in your pipeline or project configuration.

---

#### Key Concepts

- **SCM Integration**  
  Jenkins integrates with Git via the built-in `git` plugin or the `checkout scm` step in a pipeline.

- **Pipeline Example**
  ```groovy
  pipeline {
      agent any
      stages {
          stage('Checkout') {
              steps {
                  git branch: 'main', url: 'https://github.com/user/learn-jenkins-app.git'
              }
          }
      }
  }
```
- Using checkout scm. When your Jenkinsfile is stored in the same Git repo as your code:
```groovy
stage('Checkout') {
    steps {
        checkout scm
    }
}
```

Best Practices

✅ Use HTTPS or SSH URLs depending on how credentials are managed.
- 🔒 Store Git credentials securely in Jenkins (via Credentials > Global credentials).
- 🏷️ Always specify a branch explicitly to avoid unexpected builds from other branches.
- 🧩 Combine checkout with build steps (e.g., npm install, docker build) in the same workspace for simplicity.
- 📁 Keep your Jenkinsfile at the root of your repository — this is the default location Jenkins expects.

[Example Repository](https://github.com/vdespa/learn-jenkins-app)
---

### ⚙️ Building the Project

- Use npm ci or npm install to install project dependencies.
- Docker builds require the NPM_CONFIG_CACHE environment variable to avoid permission issues:
```groovy
environment {
    NPM_CONFIG_CACHE = "${WORKSPACE}/.npm"
}
```
- ⚠️ TypeScript / react-scripts Compatibility Note

    - `eact-scripts@5.0.1`only supports `ypescript@^3.2.1 || ^4`. If your project has a newer TypeScript (e.g., `5.x`) 
    installed, npm ci will fail with an error like:
    ``vbnet
    npm ci
    Invalid: lock file's typescript@5.9.3 does not satisfy typescript@4.9.5
    ```

    **Solution**
    ```bash
    # Remove old dependencies and lockfile
    rm -rf node_modules package-lock.json

    # Install a compatible TypeScript version
    npm install --save-dev typescript@^4
    npm install
    ```
    - This ensures the lockfile is consistent and compatible with react-scripts.
    - In CI pipelines, this avoids build failures when starting from a clean workspace or Docker container.
- After dependencies are installed, build the project:
```bash
npm run build
```
- ✅ This produces the build/ folder ready for deployment or further CI/CD steps.

---

### 🏛️ Revisiting Jenkins Architecture

- Jenkins uses a master-agent model:
    - Master: orchestrates builds, manages nodes, handles the UI.
    - Agents: execute jobs, stages, and tests.
- Pipeline execution:
    - `agent` defines where a stage or pipeline runs.
    - `reuseNode` true keeps the same workspace on the same agent.
- 💡 Lessons from CI exercises:
    - Stages needing Docker fail if the first stage ran on a non-Docker node with `reuseNode true`.
    - Jenkins can automatically switch nodes if stage agents differ.
    - Pipeline-level Docker agent ensures:
        - Workspace reuse
        - Node stability
        - Container consistency
- ✅ Best Practices:
    - Use labels to target nodes with required tools (Docker, Node.js, etc.).
    - Prefer a pipeline-level agent for multi-stage Docker builds.
    - Stage-level agents are possible but need careful workspace management.