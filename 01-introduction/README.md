# 01 – Introduction to Jenkins

Welcome to the **Introduction** section of the Jenkins Pipelines learning journey.  
This section covers the basics of Jenkins, pipeline concepts, shell usage, and simple CI practices. Each day builds on the previous one with practical examples.

---

## Days Overview

### Day 01 – Getting Started with Jenkins
- Installing and configuring Jenkins
- Basic pipeline syntax and structure
- `sh` step and shell commands
- Workspace management (`cleanWs()`)
- **Key file:** [Jenkinsfile](day-01/Jenkinsfile)
- **Notes:** [README.md](day-01/README.md)

### Day 02 – Pipeline Stages and Multiple Steps
- Creating multiple stages in a pipeline
- Combining shell steps
- Hands-on examples with homeworks and multi-stage pipelines
- **Key files:**
  - [Jenkinsfile](day-02/Jenkinsfile)
  - [Homework Jenkinsfile](day-02/homeworks/Jenkinsfile)
  - [Two-stages Jenkinsfile](day-02/two-stages/Jenkinsfile)
- **Notes:** [README.md](day-02/README.md)

### Day 03 – Test Stage and Build Artifacts
- Using the Test stage for verification
- Running unit tests and static analysis
- Archiving build artifacts
- **Key files:**
  - [Jenkinsfile](day-03/Jenkinsfile)
  - Example test stage: [test-stage](day-03/files/test-stage)
  - Testing build artifacts: [testing-build-artifacts](day-03/files/testing-build-artifacts)
- **Notes:** [README.md](day-03/README.md)

### Day 04 – Real-World Example: Preparation Stage
- Practical pipeline example for SBOM scanning of microservices releases
- Installing tools (Helm and Syft) in a preparation stage
- Environment variables and shell usage in a real-world scenario
- **Key files:**
  - [Jenkinsfile](day-04/Jenkinsfile)
  - Example Groovy script: [real-world-example.groovy](day-04/files/real-world-example.groovy)
- **Notes:** [README.md](day-04/README.md)

---

## Summary

By the end of this section, you should be able to:

- Understand basic Jenkins pipeline syntax
- Write simple pipelines with one or more stages
- Run shell commands inside pipelines
- Archive artifacts and organize pipeline outputs
- Create reusable pipeline examples for real-world scenarios

---

> Next section: **[02 – Implementing Continuous Integration (CI) with Jenkins](../02_ci/README.md)**
