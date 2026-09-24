# Home Lab Databases

## PostgreSQL - Terryza

Server: Terryza
OS: OpenWrt 24.10.5
PostgreSQL version: 15

LAN endpoint:
192.168.1.1:5432

Tailscale/cloud endpoint:
100.83.143.71:5432

PostgreSQL user:
ram2

Current project databases:
- labdb
- business_operations

The databases were migrated from the Intel NUC to Terryza. Terryza is now the primary 24/7 PostgreSQL server.

Windows Pavilion can access PostgreSQL through Tailscale from outside the home LAN. Connectivity was verified from Windows to 100.83.143.71:5432.

Do not store PostgreSQL passwords in Git.

## Intel NUC

LAN IP:
192.168.1.121

Previous role:
- PostgreSQL server for labdb and business_operations

Current status:
- Databases migrated to Terryza
- NUC powered down for normal server duties
- Retained in the lab for future local AI use

## SQLite

Machine:
Ubuntu Server Notebook

Version:
3.46.1

Purpose:
- Local testing database
- SQL practice
- Small datasets
- Python experiments

Command:
sqlite3 database_name.db
