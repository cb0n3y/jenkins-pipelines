# 🧱 Jenkins Server Installation (CentOS 10)

This document covers the complete setup of a **Jenkins controller** and two **worker nodes** on **CentOS 10**, serving as the foundation for later Jenkins Pipeline experiments.

---

## 🧰 Environment Overview

| Role               | OS        | vCPU | RAM  | Disk | Purpose             |
|--------------------|-----------|------|------|------|---------------------|
| jenkins-controller | CentOS 10 | 2    | 2 GB | 20GB | Main Jenkins server |
| jenkins-worker-1   | CentOS 10 | 2    | 2 GB | 20GB | Build agent         |
| jenkins-worker-2   | CentOS 10 | 2    | 2 GB | 20GB | Build agent         |

---

## 0 Prep notes

- This guide assumes you have an account with `sudo` on each machine.
- Controller will SSH to workers using an SSH key (no password auth needed).
- Firewall is managed by `firewalld` (`firewall-cmd`) on CentOS 10.


---

## 📦 Add Jenkins Repository

```bash
sudo wget -O /etc/yum.repos.d/jenkins.repo \
  https://pkg.jenkins.io/redhat-stable/jenkins.repo
sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
sudo yum upgrade -y
# Add required dependencies for the Jenkins package
sudo yum install -y fontconfig java-21-openjdk
sudo yum install -y jenkins
sudo systemctl daemon-reload
```

---

## 🔥 Configure Firewalld

```shell
sudo firewall-cmd --add-service=jenkins --zone=public --permanent
sudo firewall-cmd --add-service=http --zone=public --permanent
sudo firewall-cmd --reload
```

💡 Optional: verify rules applied

```shell
sudo firewall-cmd --list-all
```

---

## 🔑 Generate SSH Key (Controller)

```shell
ssh-keygen -o -a 100 -t ed25519 -f ~/.ssh/id_jenkins-srv -C "your-email@example.com"
```

This key will be used by Jenkins to connect securely to worker nodes.

---

## 🚀 Start Jenkins

```shell
sudo systemctl enable jenkins
sudo systemctl start jenkins
sudo systemctl status jenkins
```

Access Jenkins in your browser:

```shell
http://<controller-ip>:8080
```

Get the initial admin password:

```shell
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

For reference: [Jenkins Linux installation guide](https://www.jenkins.io/doc/book/installing/linux/#red-hat-stable)

---

# ⚙️ Worker Node Setup

Install CentOS 10 as usual. After installation, create a dedicated jenkins user for the agent connection.

---

## 👤 Create the Jenkins User
 ```shell
sudo useradd jenkins -m -s /bin/bash
sudo passwd jenkins
 ```

---

 ## 🔐 Copy Controller SSH Key

Log in as the Jenkins user:

```shell
sudo su - jenkins
mkdir -p ~/.ssh
chmod 700 ~/.ssh
echo "<paste controller's public key here>" >> ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys
```

Verify SSH access from the controller:

```shell
ssh jenkins@<worker-node-hostname-or-ip>
```

If you can log in without a password, the SSH connection is correctly set up.

---

## ☕ Install Java (Worker Node)

```shell
sudo yum install -y java-21-openjdk
java -version
```

---

## 🗂️ Prepare Jenkins Workspace Directory

```shell
sudo mkdir -p /opt/jenkins-agent
sudo chown jenkins:jenkins /opt/jenkins-agent
```

---

# 🖥️ Configure the Node in Jenkins UI

In the Jenkins web interface (on the controller):

1. Go to Manage Jenkins → Nodes and Clouds → New Node
2. Enter a name (e.g., worker-1) and choose Permanent Agent
3. Fill in:
    - **Remote root directory**: /opt/jenkins-agent
    - **Labels (optional)**: linux, docker, etc.
    - **num of executors**: usually 1–2
    - **Launch method**: Launch agents via SSH
4. Under SSH configuration:
    - **Host**: <worker-node-hostname-or-ip>
    - **Credentials**: click Add → Jenkins → SSH Username with private key
        - **Username**: jenkins
        - **Private key**: choose Enter directly
            Paste the controller’s private key (~/.ssh/id_jenkins-srv)
5. Click Save, then Launch agent to connect.

---

✅ Verification

- Agents should show online in Jenkins → Manage Nodes
- Run a simple pipeline or freestyle job restricted to a worker:

```shell
echo "Running on $(hostname)"
```

If you see the worker hostname, the setup works.

---

🧾 Outcome

- By the end of Day 01:
- Jenkins controller and two agents are fully connected.
- Password-less SSH is configured.
- Jenkins UI is reachable and functional.

---

# 🪜 Next Steps

- Configure build tools (Git, Maven, Docker)
- Add labels to organize workloads
- Secure Jenkins via HTTPS or reverse proxy (optional)
- Automate setup using Ansible for reproducibility
