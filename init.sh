#!/bin/bash

# 获取主网卡名称
NIC=$(ip route show | awk '/default/ {print $5}')
#GFW='sudo iptables'
GFW='iptables'


# 初始化
$GFW -A INPUT -i $NIC  -m state --state RELATED,ESTABLISHED -j ACCEPT
$GFW -A INPUT -i lo -j ACCEPT

$GFW -A OUTPUT -o $NIC -m state --state RELATED,ESTABLISHED -j ACCEPT
$GFW -A OUTPUT -o lo -j ACCEPT

$GFW -A OUTPUT -o $NIC -p udp -m udp --dport 53 -m state --state NEW,ESTABLISHED -j ACCEPT
$GFW -A INPUT -i $NIC -p udp -m udp --sport 53  -m state --state RELATED,ESTABLISHED -j ACCEPT


# 默认出行端口
$GFW -A INPUT -i $NIC -p tcp --dport 22 -j ACCEPT
$GFW -A OUTPUT -o $NIC -p tcp --sport 22 -m state --state ESTABLISHED -j ACCEPT

$GFW -A INPUT -i $NIC -p tcp --dport 55555 -j ACCEPT
$GFW -A OUTPUT -o $NIC -p tcp --sport 55555 -m state --state ESTABLISHED -j ACCEPT

$GFW -A INPUT -i $NIC -p udp --dport 55555 -j ACCEPT
$GFW -A OUTPUT -o $NIC -p udp --sport 55555 -m state --state ESTABLISHED -j ACCEPT



# 端口转发至55555
$GFW -t nat -A PREROUTING -p udp --dport 40000:50000 -j REDIRECT --to-ports 55555




#iptables -t nat -A PREROUTING -p tcp --dport 666 -j DNAT --to-destination <B服务器的IP地址>:999
#iptables -t nat -A POSTROUTING -j MASQUERADE



$GFW -P INPUT DROP
$GFW -P OUTPUT DROP







