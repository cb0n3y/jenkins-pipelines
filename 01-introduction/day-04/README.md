# # Daily Summary


## What Have We Learned?

### 

---

### 🧩 Defining Environment Variables

- Environment variables in Jenkins pipelines are defined inside the environment {} block.
- They can be used in any stage or shell (sh) command using $VARIABLE_NAME.

[Example Difining Environment Variables](Jenkinsfile)

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
- Full example: [files/real-world-example.groovy](files/real-world-example.groovy)

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
