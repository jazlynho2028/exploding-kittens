# BVA Analysis: DeckBuilder Class

### Method under test: `buildDeckWithoutExplodeAndAddDefuse(int numPlayers)`
- **TC1: Minimum allowed players ** ( :white_check_mark: )
    - **State of the system**: numPlayers = 2
    - **Expected output**: deck has 59 cards, of which 3 are "DEFUSE"'s. deck is shuffled.

- **TC2: Maximum allowed players ** ( :white_check_mark: )
    - **State of the system**: numPlayers = 4
    - **Expected output**: deck has 57 cards, of which 1 is a "DEFUSE". deck is shuffled.
  
### Helper functions
### Method under test: `initializeFullDeck()`
- **TC3: Create deck with base game cards ** ( :white_check_mark: )
    - **State of the system**: invoked internally after confirming numPlayers is valid
    - **Expected output**: returns list of exactly 56 base cards (excluding all Exploding Kittens and Defuse cards)

### Method under test: `calculateDefusesToAdd(int numPlayers)`
- **TC4: Defuse count for minimum player count (2 players) ** ( :white_check_mark: )
    - **State of the system**: numPlayers = 2
    - **Expected output**: returns 4 (5 defuses - 2 players = 3 defuses left)

- **TC5: Defuse count for maximum player count (4 players) ** ( :white_check_mark: )
  - **State of the system**: numPlayers = 4
  - **Expected output**: returns 2 (5 defuses - 4 players = 1 defuses left)

- **TC6: Negative Defuse count calculated (6 players) ** ( :white_check_mark: )
  - **State of the system**: numPlayers = 6
  - **Expected output**: throws an error (5 starting defuses - 6 players = -1, throw error)