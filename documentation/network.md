# Home Lab Network

## Network

Subnet:
192.168.1.0/24


## Machines

| Machine | IP | User | OS |
|---|---|---|---|
| Ubuntu Client | 192.168.1.120 | ram2 | Ubuntu 26.04 |
| Intel NUC | 192.168.1.121 | ram2 | Alpine Linux |
| Router | 192.168.1.1 | - | OpenWrt |


## SSH Access

Ubuntu → NUC:

ssh ram2@192.168.1.121


Ubuntu machine:

hostname:
ram2-HP-ENVY-m6-Notebook-PC

IP:
192.168.1.120


NUC:

hostname:
nuc

IP:
192.168.1.121


## Notes

Do not store passwords or private keys in Git.

SSH authentication uses:
- SSH keys
- GitHub token for HTTPS Git operations
