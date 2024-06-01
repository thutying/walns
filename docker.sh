#!/bin/bash

# 更新软件包列表
sudo apt update

# 安装必要的软件包
sudo apt install -y apt-transport-https ca-certificates curl gnupg-agent software-properties-common

# 添加 Docker 的官方 GPG 密钥
curl -fsSL https://download.docker.com/linux/debian/gpg | sudo apt-key add -

# 添加 Docker 的稳定存储库
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable"

# 再次更新软件包列表
sudo apt update

# 确认 Docker 版本
apt-cache policy docker-ce

# 安装 Docker
sudo apt install -y docker-ce

# 验证 Docker 是否正确安装
sudo docker --version
