# Home Lab Network

## Network

Subnet:
192.168.1.0/24


## Machines

| Machine | IP | User | OS |
|---|---|---|---|
| Ubuntu Server Notebook | 192.168.1.120 | ram2 | Ubuntu 26.04 |
| Ubuntu Client Sleekbook | 192.168.1.130 | ram2 | Ubuntu |
| Intel NUC Server | 192.168.1.121 | ram2 | Alpine Linux |
| OpenWrt Gateway | 192.168.1.1 | - | OpenWrt |


## SSH Access

Ubuntu Server Notebook → NUC:

ssh ram2@192.168.1.121


## Services

Ubuntu Server Notebook:
- VS Code
- Continue
- Neovim
- CodeCompanion

Intel NUC:
- Ollama API
- llama3:latest


## Notes

Do not store passwords or private keys in Git.

Use:
- SSH keys
- GitHub Personal Access Tokens
for authentication.
