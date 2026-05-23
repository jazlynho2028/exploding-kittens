# BVA Analysis: DeckBuilder Class

### Method under test: `buildDeckWithoutExplodeAndDefuse(int numPlayers)`
- **TC1: Less than minimum allowed players ** ( :white_check_mark: )
    - **State of the system**: numPlayers = 1
    - **Expected output**: IllegalArgumentException "Number of players must be between 2 and 4"

- **TC2: Minimum allowed players ** ( :x: )
    - **State of the system**: numPlayers = 2
    - **Expected output**: deck has 60 cards, of which 4 are "DEFUSE"'s. deck is shuffled.

- **TC3: Maximum allowed players ** ( :x: )
    - **State of the system**: numPlayers = 4
    - **Expected output**: deck has 58 cards, of which 2 is a "DEFUSE". deck is shuffled.

- **TC4: More than maximum allowed players ** ( :white_check_mark: )
    - **State of the system**: numPlayers = 5
    - **Expected output**: IllegalArgumentException "Number of players must be between 2 and 4"

### Helper functions
### Method under test: `initializeFullDeck()`
- **TC1: Create deck with base game cards ** ( :white_check_mark: )
    - **State of the system**: invoked internally after confirming numPlayers is valid
    - **Expected output**: returns list of exactly 56 base cards (excluding all Exploding Kittens and Defuse cards)

### Method under test: `calculateDefusesToAdd(int numPlayers)`
- **TC1: Defuse count for minimum player count (2 players) ** ( :x: )
    - **State of the system**: numPlayers = 2
    - **Expected output**: returns 4 (6 defuses - 2 players = 4 defuses left)
- **TC2: Defuse count for maximum player count (4 players) ** ( :x: )
  - **State of the system**: numPlayers = 4
  - **Expected output**: returns 2 (6 defuses - 4 players = 2 defuses left)
- **TC3: Less than minimum allowed players (1 player) ** ( :x: )
  - **State of the system**: numPlayers = 1
  - **Expected output**: returns IllegalArgumentException("Number of players must be between 2 and 4")
- **TC4: More than minimum allowed players (5 players) ** ( :x: )
  - **State of the system**: numPlayers = 5
  - **Expected output**: returns IllegalArgumentException("Number of players must be between 2 and 4")

### Method under test: `addPlayerDefuses(List<Card> deck, int count)`
- **TC1: Add 4 defuse cards to draw pile ** ( :x: )
    - **State of the system**: numDefuses = 4, initial 56-card base deck
    - **Expected output**: 56-card base deck is expanded to size 60 with exactly 4 cards matching CardType.DEFUSE
- **TC2: Add 2 defuse cards to draw pile ** ( :x: )
  - **State of the system**: numDefuses = 2, initial 56-card base deck
  - **Expected output**: 56-card base deck is expanded to size 58 with exactly 2 cards matching CardType.DEFUSE

### Method under test: `shuffleDeck(List<Card> deck)`
- **TC1: Shuffle once ** ( :x: )
    - **State of the system**: input is a sequential list of cards in original starting order
    - **Expected output**: order of elements changes from initial ordering
- **TC2: Shuffle twice ** ( :x: )
  - **State of the system**: input is a sequential list of cards in original starting order
  - **Expected output**: order of elements changes from initial ordering, to first shuffle, to second shuffle, ensuring shuffling is always random


