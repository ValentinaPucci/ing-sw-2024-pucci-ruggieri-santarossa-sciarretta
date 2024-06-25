# Prova Finale - Ingegneria del Software - A.A. 2023/24
![Logo](src/main/resources/Codex.png)

### Gruppo GC02
- [Valentina Pucci](https://github.com/ValentinaPucci)
- [Riccardo Ruggieri](https://github.com/RiccardoRuggieri)
- [Margherita Santarossa](https://github.com/margherita-santarossa)
- [Lorenzo Sciarretta](https://github.com/L-Neur0)

We have implemented the following features:
   | Feature | Implemented  |
|:--------|:----|
| Complete rules  | :heavy_check_mark:    |
| Socket  | :heavy_check_mark:    |
| RMI  | :heavy_check_mark:    |
| TUI | :heavy_check_mark:    |
| GUI  | :heavy_check_mark:    |
| Multiple Games   | :heavy_check_mark:    |
| Chat  | :heavy_check_mark:    |

Requirements: <a href="https://github.com/ValentinaPucci/ing-sw-2024-pucci-ruggieri-santarossa-sciarretta/blob/main/DOC/Requirements/requirements.pdf">requirements.pdf</a> <br>
Rulebook_ITA:  <a href="https://github.com/ValentinaPucci/ing-sw-2024-pucci-ruggieri-santarossa-sciarretta/blob/main/DOC/Requirements/CODEX_Rulebook_IT.pdf">Rulebook_ITA</a> <br>
Rulebook_ENG: <a href="https://github.com/ValentinaPucci/ing-sw-2024-pucci-ruggieri-santarossa-sciarretta/blob/main/DOC/Requirements/CODEX_Rulebook_EN.pdf">Rulebook_EN</a> <br>
Official Site: <a href="https://www.craniocreations.it/prodotto/codex-naturalis">producer site</a>


# How to play:

First of all, the user is requested to write the server's IP, which can be read on the server's interface once it is running. The game can be played using both the command line interface (TUI) and the graphic interface (GUI): the user selects the interface once the program is launched, following the instructions.

The user chooses the connection protocol he want to use following the instructions in the terminal. The user can choose to create a game, join a random game or join a specific game by entering the game id. The player that creates a game can select the number of players for the match, typing a number between 2 and 4. Every user need to choose a nickname to play. The nickname can't be already used by a connected player of the same game. After that, if enough players are waiting, and are all ready to start, the match starts. Once the match starts, the player whose turn is will be notified so they can play their turn by following the instructions. Every turn is divided into 3 phases: selection of the card to play from the hand, selection of the place on the personal board where he wants to place the card, and selection of the common card he wants to draw.

Users can also write a message in the public chat or a private message to another player, at any moment:
Public message: they need to write "/c" and the message.
Private message: they need to write "/cs", the Nickname of the recipient player separated by a space, and the message.

If a player disconnects, the game ends immediately.

# How to Use 
First of all you need to save the two jars in a directory. Then, open the terminal in that directory and type:

to start the Server:
```bash
java -jar Server.jar
```

to start the Client:
```bash
java -jar Client.jar
```

## Test Coverage
![Coverage Report](deliverables/Test%20Coverage%20Report.png)

## UML Diagrams
- Class Diagrams
  - [Model+Controller](DOC/UML/UML_model+controller.pdf)
  - [RMI](DOC/UML/UML_RMI.pdf)
  - [SOCKET](DOC/UML/UML_socket.pdf)
- Sequence Diagrams
  - [RMI](DOC/sequenceDiagrams/UML_Protocol.pdf)
  - [SOCKET](DOC/sequenceDiagrams/Socket_Protocol.pdf)
 
  
## Disclaimer
NOTA: Codex Naturalis è un gioco da tavolo sviluppato ed edito da Cranio Creations Srl. I contenuti grafici di questo progetto riconducibili al prodotto editoriale da tavolo sono utilizzati previa approvazione di Cranio Creations Srl a solo scopo didattico. È vietata la distribuzione, la copia o la riproduzione dei contenuti e immagini in qualsiasi forma al di fuori del progetto, così come la redistribuzione e la pubblicazione dei contenuti e immagini a fini diversi da quello sopracitato. È inoltre vietato l'utilizzo commerciale di suddetti contenuti.
