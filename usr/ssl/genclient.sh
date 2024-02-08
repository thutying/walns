#!/bin/bash

if [ $# -ne 2 ]; then
  echo "没有传递参数！[name,1024]"
  exit 1
fi

openssl req -new -newkey rsa:$2 -nodes -keyout ./client/$1.key -out ./client/$1.csr -subj "/C=US/ST=Arizone/L=Phoenix/O=Walns/OU=AI/CN=Client/emailAddress=mail@walns.org"

openssl x509 -req -days 3650 -sha512 -extensions v3_req -CA ./ca/ca.crt -CAkey ./ca/ca.key -CAserial ./srl/$1.srl -CAcreateserial -in ./client/$1.csr -out ./client/$1.crt


cp ./client.conf ./ovpn/$1.ovpn

echo "<ca>" >> ./ovpn/$1.ovpn
cat ./ca/ca.crt >> ./ovpn/$1.ovpn
echo "</ca>" >> ./ovpn/$1.ovpn

echo "<cert>" >> ./ovpn/$1.ovpn
cat ./client/$1.crt >> ./ovpn/$1.ovpn
echo "</cert>" >> ./ovpn/$1.ovpn

echo "<key>" >> ./ovpn/$1.ovpn
cat ./client/$1.key >> ./ovpn/$1.ovpn
echo "</key>" >> ./ovpn/$1.ovpn

echo "<tls-crypt>" >> ./ovpn/$1.ovpn
cat ./tls/tls-crypt.key >> ./ovpn/$1.ovpn
echo "</tls-crypt>" >> ./ovpn/$1.ovpn

#echo "<dh>" >> ./ovpn/$1.ovpn
#cat ./dh/dh.pem >> ./ovpn/$1.ovpn
#echo "</dh>" >> ./ovpn/$1.ovpn
