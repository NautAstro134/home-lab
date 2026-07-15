# tmux on the Intel NUC

## Installation

```bash
doas apk add tmux
```

Verify installation:

```bash
tmux -V
```

Output:

```
tmux 3.6b
```

---

## Create a new session

```bash
tmux new -s lab
```

Session name:

```
lab
```

---

## List sessions

```bash
tmux ls
```

Example:

```
lab: 1 windows
```

---

## Attach to a session

```bash
tmux attach -t lab
```

If the session is already attached elsewhere:

```bash
tmux attach -d -t lab
```

---

## Detach from tmux

Press:

```
Ctrl+b
d
```

The session continues running.

---

## Kill a session

```bash
tmux kill-session -t lab
```

---

## Why use tmux?

- Keep long-running terminal sessions alive.
- Reconnect after SSH disconnects.
- Work from multiple devices.
- Keep Neovim, PostgreSQL, Docker, and shell sessions running independently.

---

## Planned workflow

Window 1

- Neovim + CodeCompanion

Window 2

- PostgreSQL

Window 3

- Docker

Window 4

- Bash shell

Window 5

- Git
