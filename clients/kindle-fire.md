# Kindle Fire (Amazon tablet)

## Role in the lab
Primary hands-on terminal device. Typing on the Kindle is noticeably
easier than on a phone, making it the preferred device for daily
Linux/SQL practice and remote access to lab servers.

## Software stack
- Termux (F-Droid) + Alpine Linux via illvart/termux-alpine
- Alias: alias alpine="termux-alpine" in ~/.bashrc


## Installed packages
bash, nano, vim, curl, wget, git, openssh, htop, tree, man-pages, mandoc,
tmux, freetds (tsql), postgresql-client (psql), python3, py3-pip,
tldr (via pip)

## Connections
PostgreSQL (NUC): psql -h 192.168.1.121 -U ram2 -d labdb
SQL Server (Pavilion): tsql -H 192.168.1.235 -p 1433 -U ram2 -P rrr
SSH to NUC: ssh ram2@192.168.1.121

## Fixed issues
apk lock file error - cleared stale lock:
rm -f /var/lib/apk/db.lock
apk update

## GitHub
Repo cloned and configured directly on this device (2026-07-08).
Personal access token generated in-device via GitHub Settings > Tokens,
scope "repo". Credential caching enabled: git config --global
credential.helper store
## SSH Targets

Ubuntu Client

```bash
ssh roustam@192.168.1.130
```

Intel NUC

```bash
ssh ram2@192.168.1.121
```

Intel PostgreSQL Server

```bash
ssh <username>@192.168.1.120
```

---

## Web Interfaces

code-server

http://192.168.1.121:8080

Portainer

http://192.168.1.121:9000

Ollama API

http://192.168.1.121:11434

---

## AI Development

Neovim on the Ubuntu client is configured to use the Ollama server
running on the Intel NUC.

Workflow:

Kindle Fire
→ Termux
→ SSH
→ Ubuntu Client
→ Neovim
→ CodeCompanion
→ Ollama (Intel NUC)

---

## Git Workflow

```bash
git pull
git status
git add .
git commit -m "message"
git push
```
