# Kindle Fire — Cloud Mobile Sunshine T1

## Hardware
- Device: Cloud Mobile Sunshine T1 tablet
- Architecture: armv7l (32-bit ARM)
- OS: Android 11 Go
- RAM: 2.7 GB total
- Storage: 52 GB total, ~39 GB free
- Static IP: 192.168.1.224 (MAC ca:5e:b8:9d:6f:34)

## Software stack
- Termux (F-Droid) + Alpine Linux via illvart/termux-alpine
- Alias: alias alpine="termux-alpine" in ~/.bashrc

## Known limitations
- proot-distro failed (hardened kernel)
- dbt-core failed (no prebuilt Rust wheel for armv7l)

## Installed packages
bash, nano, vim, curl, wget, git, openssh, htop, tree, man-pages, mandoc,
tmux, freetds (tsql), postgresql-client (psql), python3, py3-pip,
tldr (via pip, tealdeer not available on this arch)

## Connections
PostgreSQL (NUC): psql -h 192.168.1.121 -U ram2 -d labdb
SQL Server (Pavilion): tsql -H 192.168.1.235 -p 1433 -U ram2 -P rrr
SSH to NUC: ssh ram2@192.168.1.121

## Fixed issues
apk lock file error - cleared stale lock:
rm -f /var/lib/apk/db.lock
apk update
