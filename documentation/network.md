# Home Lab Network

## LAN

Subnet:
192.168.1.0/24

| Machine | LAN IP | Role |
|---|---|---|
| Terryza | 192.168.1.1 | Gateway, PostgreSQL, cloud storage |
| Ubuntu Server Notebook | 192.168.1.120 | Development workstation |
| Intel NUC | 192.168.1.121 | Standby / future local AI |
| Ubuntu Client Sleekbook | 192.168.1.130 | Data engineering client |

## Terryza Remote Access

Terryza Tailscale IP:
100.83.143.71

Windows Pavilion Tailscale IP:
100.68.49.62

Tailscale allows remote access to Terryza across the Internet without exposing PostgreSQL or SMB directly to the public Internet.

## Terryza WAN

WAN interface:
usb0

WAN source:
USB tethering through phone

The WAN address is assigned dynamically by the tethered phone and should not be treated as a permanent server address.

## PostgreSQL Access

LAN:
192.168.1.1:5432

Tailscale/cloud:
100.83.143.71:5432

Windows Pavilion successfully tested PostgreSQL connectivity over Tailscale from a separate Internet connection.

## TerryzaCloud SMB

TerryzaCloud is available to authorized Tailscale/LAN clients through SMB.

Windows Pavilion mapping:
Z: -> \\100.83.143.71\TerryzaCloud

## SSH

Terryza over LAN:
ssh root@192.168.1.1

Terryza over Tailscale:
ssh root@100.83.143.71

NUC from the lab LAN when powered on:
ssh ram2@192.168.1.121

## Security Notes

- Do not store passwords or private keys in Git.
- PostgreSQL and SMB remote access use the private Tailscale network.
- The Intel NUC is no longer required for normal 24/7 server duties.
