# Subnet-Calculator
The program acts as a subnet calculator for an IP or an IP with a VLSM entered.

If you enter 199.212.55.7
The result should be:

Network Class: C
Subnet Mask: 255.255.255.0
CIDR: /24
Hosts per subnet: 254
Network Address: 199.212.55.0
Broadcast Address: 199.212.55.255
Bits in Host: 8
Bits in Network: 24


199.212.55.7/16 or 199.212.55.7 255.255.0.0 
The result should be:

Subnet Mask: 255.255.0.0
CIDR: /16
Hosts per subnet: 65534
Network Address: 199.212.0.0
Broadcast Address: 199.212.255.255
Bits in Host: 16
Bits in Network: 16
