# Install Docker On CentOS


## 1. Install Docker on RHEL/CentOS/Rocky/Alma

```shell
# Remove old versions if present
sudo yum remove -y docker docker-client docker-client-latest docker-common docker-latest docker-latest-logrotate docker-logrotate docker-engine

# Install required packages
sudo yum install -y yum-utils device-mapper-persistent-data lvm2

# Add Docker CE repository
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

# Install Docker CE
sudo yum install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

# Enable and start Docker service
sudo systemctl enable --now docker
```

## 2. Give Jenkins user access to Docker

```shell
sudo usermod -aG docker jenkins
```

- Then restart the Jenkins agent so group changes take effect:

```shell
sudo systemctl restart jenkins-agent  # or restart the agent service/container
```

## 3. Verify

```shell
docker ps
```