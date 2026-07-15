# Ubuntu Client - Neovim AI Setup

## System

Host: HP ENVY m6 Sleekbook

## Installed software

### Neovim

Version:

```bash
nvim --version | head -1
```

Output:

```
NVIM v0.12.4
```

### Git

Version:

```bash
git --version
```

Output:

```
git version 2.53.0
```

---

## Plugin Manager

lazy.nvim

Installed in:

```
~/.local/share/nvim/lazy/lazy.nvim
```

---

## Neovim Configuration

Configuration directory:

```
~/.config/nvim
```

Main configuration file:

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

Open Neovim:

```bash
nvim
```

Open chat:

```vim
:CodeCompanionChat hello
```

Expected result:

```
## CodeCompanion (ollama)

Hello! ...
```

The Ubuntu client communicates successfully with the Ollama server running on the Intel NUC.

---

## Installed plugins

- lazy.nvim
- codecompanion.nvim
- plenary.nvim

---

## Notes

Close the Lazy plugin window:

```
q
```

Open CodeCompanion chat:

```vim
:CodeCompanionChat
```

The Ubuntu client uses the Intel NUC as its local AI server.
