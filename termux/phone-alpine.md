# Phone — Alpine Linux via Termux

Set up 2026-07-02. Same approach as Kindle Fire, using illvart/termux-alpine installer script (setup-termux-alpine).

## Key fix
The installer fails with create_resolv errors unless a real gzip binary is installed first (Termux default is a busybox stub that does not work with the installer).

Fix — run before the installer: pkg install gzip -y
Then run: setup-termux-alpine

## Status
Working. Same general capability as the Kindle Fire setup.
