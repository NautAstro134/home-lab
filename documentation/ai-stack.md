# AI Stack

## Ollama Server

Machine:
Intel NUC

IP:
192.168.1.121

Service:
Ollama

Process:
 /bin/ollama serve

API:
http://192.168.1.121:11434

Model:
llama3:latest

Details:
- Family: llama
- Parameters: 8B
- Quantization: Q4_K_M


## Neovim AI

Machine:
Ubuntu Client

Plugin:
olimorris/codecompanion.nvim

AI Backend:
Ollama

Connection:
http://192.168.1.121:11434

Model:
llama3:latest


## VS Code AI

Machine:
Ubuntu Client

Editor:
Visual Studio Code

Extension:
Continue

Provider:
Ollama

Model:
llama3:latest

Connection:
http://192.168.1.121:11434
