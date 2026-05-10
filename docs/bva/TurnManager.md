# BVA Analysis: TurnManager Class
### Method under test: `getCurrentPlayerIndex()`
- **TC1: first player's index before any turns taken with list of two players** ( x )
  - **State of the system**: TurnManager constructed with list of 2 players
  - **Expected output**: Returns 0 (first player's index)

- **TC2: first player's index before any turns taken with list of more than one player** ( x )
    - **State of the system**: TurnManager constructed with list of 4 players
    - **Expected output**: Returns 0 (first player's index)

- **TC3: first player's index before any turns taken with list of too many players** ( x )
  - **State of the system**: TurnManager constructed with list of 5 players
  - **Expected output**: throws IllegalArgumentException

- **TC4: first player's index before any turns taken with list of too little players** ( x )
  - **State of the system**: TurnManager constructed with list of 1 player
  - **Expected output**: throws IllegalArgumentException