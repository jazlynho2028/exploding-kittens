# BVA Analysis: TurnManager Class
### Method under test: `getCurrentPlayerIndex()`
- **TC1: first player's index before any turns taken with list of two players** ( checkmark )
  - **State of the system**: TurnManager constructed with list of 2 players
  - **Expected output**: Returns 0 (first player's index)

- **TC2: first player's index before any turns taken with list of more than one player** ( checkmark )
    - **State of the system**: TurnManager constructed with list of 4 players
    - **Expected output**: Returns 0 (first player's index)