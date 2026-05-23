# BVA Analysis: DeckBuilder Class

### Method under test: `buildDeckWithoutExplodeAndAddDefuse(int numPlayers)`
- **TC1: Less than minimum allowed players ** ( :white_check_mark: )
    - **State of the system**: numPlayers = 1
    - **Expected output**: IllegalArgumentException "Number of players must be between 2 and 4"

- **TC2: Minimum allowed players ** ( :white_check_mark: )
    - **State of the system**: numPlayers = 2
    - **Expected output**: deck has 59 cards, of which 3 are "DEFUSE"'s. deck is shuffled.

- **TC3: Maximum allowed players ** ( :white_check_mark: )
    - **State of the system**: numPlayers = 4
    - **Expected output**: deck has 57 cards, of which 1 is a "DEFUSE". deck is shuffled.

- **TC4: More than maximum allowed players ** ( :white_check_mark: )
    - **State of the system**: numPlayers = 5
    - **Expected output**: IllegalArgumentException "Number of players must be between 2 and 4"

### Helper functions
### Method under test: `initializeFullDeck()`
- **TC1: Create deck with base game cards ** ( :white_check_mark: )
    - **State of the system**: invoked internally after confirming numPlayers is valid
    - **Expected output**: returns list of exactly 48 base cards (excluding all Exploding Kittens and Defuse cards)

### Method under test: `calculateDefusesToAdd(int numPlayers)`
- **TC1: Defuse count for minimum player count (2 players) ** ( :white_check_mark: )
    - **State of the system**: numPlayers = 2
    - **Expected output**: returns 4 (5 defuses - 2 players = 3 defuses left)
- **TC2: Defuse count for maximum player count (4 players) ** ( :white_check_mark: )
  - **State of the system**: numPlayers = 4
  - **Expected output**: returns 2 (5 defuses - 4 players = 1 defuses left)
- **TC3: Less than minimum allowed players (1 player) ** ( :white_check_mark: )
  - **State of the system**: numPlayers = 1
  - **Expected output**: returns IllegalArgumentException("Number of players must be between 2 and 4")
- **TC4: More than maximum allowed players (5 players) ** ( :white_check_mark: )
  - **State of the system**: numPlayers = 5
  - **Expected output**: returns IllegalArgumentException("Number of players must be between 2 and 4")

### Method under test: `addPlayerDefuses(List<Card> deck, int defuseCount)`
- **TC1: Add 3 defuse cards to draw pile ** ( :white_check_mark: )
    - **State of the system**: defuseCount = 3, initial 56-card base deck
    - **Expected output**: Test deck is expanded to size 3 with all 3 cards matching CardType.DEFUSE
- **TC2: Add 1 defuse cards to draw pile ** ( :white_check_mark: )
  - **State of the system**: defuseCount = 1, initial 56-card base deck
  - **Expected output**: Test deck is expanded to size 1 with exactly 1 card matching CardType.DEFUSE

### Method under test: `shuffleDeck(List<Card> deck)`
- **TC1: Shuffle once ** ( :white_check_mark: )
    - **State of the system**: input is a sequential list of cards in original starting order
    - **Expected output**: order of elements changes from initial ordering
- **TC2: Shuffle twice ** ( :white_check_mark: )
  - **State of the system**: input is a sequential list of cards in original starting order
  - **Expected output**: order of elements changes from initial ordering, to first shuffle, to second shuffle, ensuring shuffling is always random


