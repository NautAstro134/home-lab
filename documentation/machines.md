# Home Lab Machines

## Terryza - Primary 24/7 Server and Gateway

Hostname:
TERRYZA

OS:
OpenWrt 24.10.5

LAN IP:
192.168.1.1

Tailscale IP:
100.83.143.71

Purpose:
- Primary 24/7 home lab server
- OpenWrt network gateway/router
- PostgreSQL 15 server
- TerryzaCloud SMB file server
- Tailscale remote/cloud access
- Hosts migrated labdb and business_operations databases

## Windows Pavilion

Hostname:
HP Pavilion Notebook

OS:
Windows 10

Tailscale IP:
100.68.49.62

Purpose:
- Power BI Desktop workstation
- Business analytics
- PostgreSQL client
- Remote Terryza administration
- TerryzaCloud client mapped as drive Z:

Installed development/data tools:
- Power BI Desktop
- Python 3.13.13
- Node.js 24.12.0
- Git
- GitHub CLI
- uv
- 7-Zip
- jq
- ripgrep
- Tailscale

## Ubuntu Server Notebook

Hostname:
ram2-HP-ENVY-m6-Notebook-PC

Model:
HP ENVY m6-1225dx

OS:
Ubuntu 26.04 LTS

LAN IP:
192.168.1.120

User:
ram2

Purpose:
- Development workstation
- VS Code
- Continue AI client
- Neovim
- CodeCompanion

## Ubuntu Client Sleekbook

LAN IP:
192.168.1.130

Purpose:
- PostgreSQL client
- dbt
- pgAdmin
- Data engineering workstation

## Intel NUC

Hostname:
nuc

Model:
Intel NUC7i5BNK

OS:
Alpine Linux

LAN IP:
192.168.1.121

User:
ram2

Previous role:
- PostgreSQL server
- Home lab services
- Local AI/Ollama

Current status:
- labdb migrated to Terryza
- business_operations migrated to Terryza
- Powered down for normal server duties
- Kept in the lab for future local AI use and planned RAM upgrade
