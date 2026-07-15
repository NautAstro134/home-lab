# Neovim on Kindle Fire

## Device

Amazon Kindle Fire

Environment:

- Termux
- Alpine Linux

---

## Neovim

Version:

```bash
nvim --version | head -1
```

Output:

```
NVIM v0.12.2
```

---

## Plugin Manager

lazy.nvim

Installed in:

```
~/.local/share/nvim/lazy/lazy.nvim
```

---

## Configuration

```
~/.config/nvim/init.lua
```

---

## AI Assistant

Plugin:

```
olimorris/codecompanion.nvim
```

Dependency:

```
nvim-lua/plenary.nvim
```

---

## AI Backend

Remote Ollama server:

```
http://192.168.1.121:11434
```

Model:

```
llama3:latest
```

---

## Test

Start Neovim:

```bash
nvim
```

Start chat:

```vim
:CodeCompanionChat hello
```

Expected response:

```
## CodeCompanion (Ollama)

Hello! I'm CodeCompanion...
```

---

## Workflow

Kindle Fire

↓

Termux

↓

Alpine Linux

↓

Neovim

↓

CodeCompanion

↓

Intel NUC

↓

Ollama

↓

llama3:latest

---

## Status

Working successfully.
The Kindle communicates with the Ollama server running on the Intel NUC.
