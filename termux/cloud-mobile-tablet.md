# Cloud Mobile Sunshine T1 Elite Tablet — Termux (no Alpine)

Android 11 Go Edition, armv7l, 2GB RAM. Termux installed and working.

## Alpine via proot-distro: does not work
Tried the usual proot-distro install alpine approach. Install completes but login fails:
proot error: execve("/bin/sh"): Exec format error
proot error: can't chdir to '/root': Function not implemented
Confirmed not a bad download (busybox arch correct, FPU present). PROOT_NO_SECCOMP=1 did not help either.
Kernel on this device blocks ptrace-based syscalls proot needs. Not fixable via reinstall or flags.

## Current setup
Native Termux packages only, no chroot: python 3.13.13, pip 26.1.2, sqlite3.
Practice script at ~/practice.py — basic sqlite3 connect/create/insert/query.

## Status
Used as a Python + SQLite training terminal, isolated from lab databases.
