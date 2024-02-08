#!/bin/bash

if [ $# -eq 0 ]; then
  echo "没有传递参数！1024"
  exit 1
fi

openssl genrsa -aes256 -out ./ca/ca.key $1 

openssl req -new -sha512 -key ./ca/ca.key -out ./ca/ca.csr -subj "/C=US/ST=Arizone/L=Phoenix/O=Walns/OU=AI/CN=CA/emailAddress=mail@walns.org"

openssl x509 -req -days 3650 -sha512 -extensions v3_ca -signkey ./ca/ca.key -in ./ca/ca.csr -out ./ca/ca.crt

