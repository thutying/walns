#!/bin/bash

# 安装必要的依赖项
sudo apt-get update
sudo apt-get install -y build-essential cmake gcc pkg-config libglib2.0-dev libgpgme-dev libgnutls28-dev uuid-dev libssh-gcrypt-dev libldap2-dev doxygen graphviz libradcli-dev libhiredis-dev libpcap-dev bison libksba-dev libsnmp-dev libmicrohttpd-dev libxml2-dev libxslt1-dev xsltproc clang-format python3 python3-pip python3-setuptools python3-wheel python3-psycopg2 python3-paramiko python3-lxml python3-defusedxml python3-netifaces python3-cffi python3-dateutil virtualenv

# 克隆并构建OpenVAS
git clone https://github.com/greenbone/openvas.git
cd openvas
mkdir build
cd build
cmake .. -DCMAKE_INSTALL_PREFIX=/usr
make
sudo make install

# 配置OpenVAS
sudo openvas-manage-certs -a
sudo openvasmd --rebuild --progress
sudo systemctl enable openvas-scanner.service
sudo systemctl enable openvas-manager.service
sudo systemctl enable greenbone-security-assistant.service
sudo systemctl start openvas-scanner.service
sudo systemctl start openvas-manager.service
sudo systemctl start greenbone-security-assistant.service

# 设置管理员用户
sudo openvasmd --create-user=admin
sudo openvasmd --user=admin --new-password=<password>

echo "OpenVAS已成功安装和配置。您现在可以登录到Web界面并开始扫描。"
