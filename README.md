# Home Data Engineering Lab

Personal home lab for business analytics, data engineering, databases, AI-assisted development, and portfolio projects.

## Current Architecture

Terryza is the primary 24/7 server and network gateway. The Intel NUC has been retired from normal server duty and remains in the lab for future local-AI use.

| Machine | Role | OS | Address |
|---|---|---|---|
| Terryza | 24/7 server, gateway, PostgreSQL, cloud storage | OpenWrt 24.10.5 | LAN 192.168.1.1 / Tailscale 100.83.143.71 |
| Windows Pavilion | Analytics and Windows workstation | Windows 10 | Tailscale 100.68.49.62 |
| Ubuntu Server Notebook | Development workstation | Ubuntu 26.04 LTS | 192.168.1.120 |
| Ubuntu Sleekbook | Data engineering client | Ubuntu | 192.168.1.130 |
| Intel NUC | Standby / future local AI | Alpine Linux | 192.168.1.121 |

## Terryza Cloud

Terryza provides remote access through Tailscale.

Services:
- PostgreSQL 15
- TerryzaCloud SMB file storage
- Tailscale remote access
- OpenWrt routing/firewall
- WAN through USB phone tethering

Remote/cloud address:
100.83.143.71

Windows maps the TerryzaCloud SMB share as drive Z:.

## PostgreSQL

Primary PostgreSQL server: Terryza

LAN endpoint:
192.168.1.1:5432

Tailscale/cloud endpoint:
100.83.143.71:5432

Current migrated project databases:
- labdb
- business_operations

Remote PostgreSQL connectivity from Windows was verified successfully over Tailscale from a separate Internet connection.

The Intel NUC previously hosted these databases and is no longer required for normal database service.

## Windows Pavilion

Primary Windows analytics workstation.

Installed tools include:
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

Windows also hosts SQL Server with the Northwind database.

TerryzaCloud mapping:
Z: -> \\100.83.143.71\TerryzaCloud

## Data and Analytics Stack

Current and previous lab work includes:
- PostgreSQL
- SQL Server
- SQLite
- dbt
- Power BI
- Metabase
- Python
- Flask analytics APIs
- pgAdmin
- Git and GitHub
- AI-assisted development tools

## Network

LAN subnet:
192.168.1.0/24

Terryza:
192.168.1.1

Linksys DD-WRT access point:
192.168.1.2

Remote access uses Tailscale rather than exposing PostgreSQL or SMB directly to the public Internet.

## Intel NUC

The Intel NUC previously served as the main PostgreSQL and home-lab server.

Current status:
- labdb migrated to Terryza
- business_operations migrated to Terryza
- Flask analytics API not running at shutdown
- Metabase not running at shutdown
- Powered down for normal server duties
- Retained for future local AI use and planned RAM upgrade

## Security

Passwords, private keys, database credentials, and authentication tokens are not stored in this repository.

## Documentation

See the documentation directory for detailed machine, network, database, dbt, and AI-stack information.
