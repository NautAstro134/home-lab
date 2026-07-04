# Home Data Engineering Lab

Personal home lab for learning data engineering (Linux, SQL, dbt, Metabase, Python)
and building a freelance/consulting portfolio.

## Machines

| Name             | Role              | OS            | IP             | User    |
|------------------|-------------------|---------------|----------------|---------|
| Sleekbook        | Client/control    | Ubuntu        | 192.168.1.130  | roustam |
| NUC               | Server (Postgres) | Alpine Linux  | 192.168.1.121  | ram2    |
| Ubuntu server     | Server (Postgres) | Ubuntu        | 192.168.1.120  | ram2    |
| HP Pavilion       | SQL Server        | Windows 10    | 192.168.1.235  | ram2    |
| Acer              | Misc              | antiX Linux   | 192.168.1.157  | ram     |

## Network

- Router: Terryza AK3V (OpenWrt 24.10.5), gateway 192.168.1.1, WAN via phone tether (usb0)
- Dumb AP: Linksys WRT160N (DD-WRT), 192.168.1.2
- Static DHCP leases assigned for all lab MACs in LuCI

## Databases

- **NUC** — PostgreSQL 18.4 (native), Docker runs Portainer + Ollama
- **Ubuntu server** — PostgreSQL 18, database `labdb`
- **HP Pavilion** — SQL Server 17.0, database `Northwind`

Credential standard: one username/password across all lab databases (not stored in this repo).

## dbt

Project: `~/sales_project` on Sleekbook (venv: `~/dbt-env`)
Targets in `~/.dbt/profiles.yml`:
- `dev` → Ubuntu server (.120), default
- `nuc` → NUC (.121), verified working

## Metabase

Runs on Sleekbook: `~/metabase/metabase.jar`, started manually each session via
`cd ~/metabase && java -jar metabase.jar` → http://localhost:3000

Connected databases: Ubuntu Server (labdb), NUC (labdb), Pavilion (Northwind)

## Known issues

- Terminal sometimes opens in wrong directory after reboot on Sleekbook — suspected
  cause: `battery-guard.timer`. Not yet confirmed.

## TODO

- [ ] Verify `dbt debug --target dev` on Ubuntu server
- [ ] Add actual config files (network, postgres, systemd units) to their folders
- [ ] Confirm battery-guard.timer bug

## Update (2026-07-04): BAT1 directory bug resolved

The "terminal opens in wrong directory" issue was NOT caused by
battery-guard.timer. Root cause: the terminal uses VTE's OSC 7 directory
tracking (/etc/profile.d/vte-2.91.sh), which remembers the last `cd`
location across sessions. At some point BAT1 was checked manually via
`cd /sys/class/power_supply/BAT1`, and the terminal kept reopening there.

Fix: cd to a normal directory once (e.g. `cd ~`), open a new terminal
tab to confirm it sticks.
