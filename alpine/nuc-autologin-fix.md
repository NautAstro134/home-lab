# NUC — Fix: Password Prompt at Every Boot

## Problem
The NUC (Alpine Linux) required a password login at the physical console/monitor after every reboot, instead of dropping straight into a shell.

## Root Cause
Alpine uses OpenRC, not systemd. Console logins are controlled by /etc/inittab, which spawns a getty process on tty1. The default line had no autologin flag, so it always prompted for credentials.

## Fix — Step by Step

1. SSH into the NUC:
   ssh ram2@192.168.1.121

2. Check the current tty1 line (no autologin flag present):
   cat /etc/inittab | grep tty1

3. Open the file for editing (NUC has no sudo, uses doas):
   doas vi /etc/inittab

4. In vi, move the cursor onto the tty1 line, then delete it with dd.
5. Press i to enter insert mode, then type the replacement line:
   tty1::respawn:/sbin/agetty --autologin ram2 38400 tty1

6. Press Esc to leave insert mode.
7. Save and quit vi:
   :wq
   (press Enter after typing :wq)

8. Verify the saved file:
   cat /etc/inittab | grep tty1
   Should now show: tty1::respawn:/sbin/agetty --autologin ram2 38400 tty1

9. Reboot to apply the change:
   doas reboot

## Result
Confirmed working 2026-07-05. NUC now boots straight to a logged-in ram2 shell at the physical console, no password prompt.
