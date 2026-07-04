# Metabase Database Connections

As of 2026-07-04, Metabase (running on Sleekbook, ~/metabase/metabase.jar,
http://localhost:3000) has these databases configured:

1. **NUC - labdb** — PostgreSQL, 192.168.1.121 — working
2. **Pavilion - Northwind** — SQL Server, 192.168.1.235 — working
3. **Pavilion - Northwind** (duplicate) — same as above, needs cleanup
4. **Ubuntu Server - labdb** — PostgreSQL, 192.168.1.120 — fails when
   machine is powered off (expected, not a bug)
5. **Sample Database** — Metabase's built-in demo data

Connection passwords are stored encrypted in Metabase's internal H2
database (~/metabase/metabase.db.mv.db) and are not human-readable;
not included in this repo.

## Update (2026-07-04): Duplicate removed

Confirmed both Pavilion - Northwind entries had identical settings
(host, port, database). Removed the duplicate (ID 5) via Admin >
Databases. Four connections remain: NUC, Pavilion, Ubuntu Server,
Sample Database.
