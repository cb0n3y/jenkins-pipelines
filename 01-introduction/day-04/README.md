# Daily Summary


## What Have We Learned?

###

---

### 🧩 Defining Environment Variables

- Environment variables in Jenkins pipelines are defined inside the environment {} block.
- They can be used in any stage or shell (sh) command using $VARIABLE_NAME.

[Example Defining Environment Variables](Jenkinsfile)

---

### 🕸️ Pipeline Graph View

- Jenkins automatically generates a graph view for declarative pipelines.
- It visually shows stages, parallel branches, and their status (success, failure, skipped, etc.).
- Useful for tracking progress and debugging long pipelines.

---

### ⚙️ What is DevOps?

- DevOps is a culture and set of practices that combine software development (Dev) and IT operations (Ops).
- Its goal is to shorten the development lifecycle while delivering high-quality software continuously.
- Key principles:
    - Automation (builds, testing, deployments)
    - Continuous Integration / Continuous Delivery (CI/CD)
    - Collaboration between teams
    - Monitoring and feedback loops

---

### 🏗️ Real Work Example: Preparation Stage

- Installs Helm and Syft in a local environment.
- Shows environment variables, shell execution, and verification commands.

[Example Real World Uses](files/real-world-example.groovy)

---

### 🔧 Integrating Parameters with environment and withEnv()

🧠 What’s Happening

So far, we’ve learned how:

- environment {} defines global variables used throughout the pipeline.
- parameters {} let us provide user input before a build (e.g., a version number, build type, or number of components).
- withEnv() lets us dynamically pass parameters and environment variables into shell scripts or tools that expect environment variables.

When combined, these three make pipelines flexible, reusable, and configurable — key traits in DevOps automation.

[Example: Using Parameters and Environment Together](files/pipelineBuildCarPrototype.groovy)

💡 Key Takeaways

- parameters → Allow build-time customization.
- environment → Define reusable, global variables.
- withEnv() → Dynamically merge both for shell-level access.

---

### ⚡ Running Stages in Parallel

🧠 What’s Happening

Jenkins allows stages to run in parallel inside a parallel {} block. Each parallel branch executes independently, often on separate agents or executors, which speeds up the pipeline.

This is useful for:

- Installing multiple tools simultaneously (e.g., Helm and Syft)
- Running tests across different environments at the same time
- Performing multiple validations or checks concurrently

[Example Running Stages in Parallel](files/pipelineParallelExecution.groovy)

💡 Key Takeaways

- Each parallel branch must have its own steps {} block.
- Jenkins visualizes parallel stages side by side in the Blue Ocean or classic pipeline view.
- If any branch fails, the whole parallel stage fails.
- Ideal for speeding up CI/CD pipelines and running multiple independent tasks simultaneously.

---

### 📝 Homework

💡 Exercise: Parameterize Tool Versions

Goal: Make the Helm and Syft installation versions configurable without modifying the pipeline code.

Tasks:

1. Add parameters for Helm and Syft versions

- Add two string parameters at the top of your pipeline:

```groovy
parameters {
    string(name: 'HELM_VERSION', defaultValue: '3.18.0', description: 'Which Helm version to install')
    string(name: 'SYFT_VERSION', defaultValue: '1.34.2', description: 'Which Syft version to install')
}
```

- These parameters allow you to dynamically select versions when starting a build.

2. Use withEnv() to inject the parameters

Wrap your installation commands in a withEnv() block so the shell commands can use the versions:

```groovy
withEnv(["HELM_VERSION=${params.HELM_VERSION}", "SYFT_VERSION=${params.SYFT_VERSION}"]) {
    sh '''
        echo "Installing Helm v$HELM_VERSION ..."
        curl -sSL https://raw.githubusercontent.com/helm/helm/master/scripts/get-helm-3 | bash -s -- --version v$HELM_VERSION --no-sudo

        echo "Installing Syft v$SYFT_VERSION ..."
        curl -sSL "https://github.com/anchore/syft/releases/download/v$SYFT_VERSION/syft_$SYFT_VERSION_linux_amd64.tar.gz" | tar -xz -C "$HOME/bin"
    '''
}
```

3. Test different versions

- Run the pipeline multiple times with different Helm/Syft versions to see how parameters make the pipeline flexible.
- Keep default values in parameters so builds still work if you don’t specify anything.

This exercise teaches:

- How to parameterize a pipeline for flexibility
- How to use withEnv() to inject parameters into shell scripts
- How default values allow safe builds without specifying parameters each time

[Solution Parameterize Tool Versions](files/pipelineParallelExecutionparameterized.groovy)
