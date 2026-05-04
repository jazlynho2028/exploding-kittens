# BVA Analysis: TurnManager Class
### Method under test: `getCurrentPlayerIndex()`
- **TC1: first player's index before any turns taken, one player list** ( x )
    - **State of the system**: TurnManager constructed with 1 player
    - **Expected output**: Returns 0 (first player's index)

- **TC2: first player's index before any turns taken, many player list** ( x )
    - **State of the system**: TurnManager constructed with 4 players
    - **Expected output**: Returns 0 (first player's index)