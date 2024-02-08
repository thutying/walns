#!/bin/bash

if [ $# -eq 0 ]; then
  echo "没有传递参数！1024"
  exit 1
fi

openssl req -new -newkey rsa:$1 -nodes -keyout ./server/server.key -out ./server/server.csr -subj "/C=US/ST=Arizone/L=Phoenix/O=Walns/OU=AI/CN=Server/emailAddress=mail@walns.org"

openssl x509 -req -days 3650 -sha512 -extensions v3_req -CA ./ca/ca.crt -CAkey ./ca/ca.key -CAserial ./srl/server.srl -CAcreateserial -in ./server/server.csr -out ./server/server.crt

