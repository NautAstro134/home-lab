# Kindle Fire — Alpine Linux via Termux

Installed via F-Droid Termux + termux-alpine bootstrap script.

Alias: alpine = termux-alpine (drops into the Alpine chroot)

## Toolkit
Full CLI toolkit installed, including tmux and freetds (provides tsql).

## SQL Server access from Kindle
tsql -H 192.168.1.235 -p 1433 -U ram2 -P REDACTED
Queries need GO on its own line to execute (FreeTDS syntax, not semicolons).

## Status
This is now the preferred terminal device, more reliable than the Sleekbook physical keyboard given typing constraints.
