# dbt Setup

## Environment

Machine:
Ubuntu Server Notebook

IP:
192.168.1.120

User:
ram2

OS:
Ubuntu 26.04 LTS


## Python Environment

Virtual environment:

~/dbt-env

Python:

3.14.4


## dbt Installation

Package:

dbt-postgres

Versions:

dbt-core:
1.12.0

dbt-postgres:
1.11.0


## Project

Project name:

lab_dbt

Location:

~/home-lab/lab_dbt


## PostgreSQL Connection

Server:

Intel NUC

IP:

192.168.1.121

Port:

5432

Database:

labdb

User:

ram2

Schema:

public


## Validation

Command:

dbt debug

Result:

Connection test: OK connection ok

All checks passed!


## Workflow

Ubuntu Server Notebook:
- runs dbt
- develops models

Intel NUC:
- runs PostgreSQL
- stores data

Main source table:

sales_raw
