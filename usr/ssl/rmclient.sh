#!/bin/bash

if [ $# -eq 0 ]; then
  echo "没有传递参数！1024"
  exit 1
fi


rm -rf client/$1.csr
rm -rf client/$1.crt
rm -rf client/$1.key
rm -rf srl/$1.srl
rm -rf ovpn/$1.ovpn

