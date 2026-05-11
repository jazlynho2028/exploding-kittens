# BVA Analysis: DeckBuilder Class

### Method under test: `buildDeckWithoutExplodeAndDefuse(int numPlayers)`
- **TC1: Less than minimum allowed players ** ( :white_check_mark: )
    - **State of the system**: numPlayers = 1
    - **Expected output**: IllegalArgumentException "Number of players must be between 2 and 4"

- **TC2: Minimum allowed players ** ( :white_check_mark: )
    - **State of the system**: numPlayers = 2
    - **Expected output**: deck has 59 cards, of which 3 are "DEFUSE"'s. deck is shuffled.

- **TC3: Maximum allowed players ** ( :x: )
    - **State of the system**: numPlayers = 4
    - **Expected output**: deck has 57 cards, of which 1 is a "DEFUSE". deck is shuffled.

- **TC4: More than maximum allowed players ** ( :x: )
    - **State of the system**: numPlayers = 5
    - **Expected output**: IllegalArgumentException "Number of players must be between 2 and 4"

### Helper functions
### Method under test: `initializeFullDeck()`
- **TC1: NAME OF THE TEST CASE** ( :x: )
    - **State of the system**: abc
    - **Expected output**: abc

### Method under test: `calculateDefusesToAdd()`
- **TC1: NAME OF THE TEST CASE** ( :x: )
    - **State of the system**: abc
    - **Expected output**: abc

### Method under test: `addPlayerDefuses()`
- **TC1: NAME OF THE TEST CASE** ( :x: )
    - **State of the system**: abc
    - **Expected output**: abc

### Method under test: `shuffleDeck()`
- **TC1: NAME OF THE TEST CASE** ( :x: )
    - **State of the system**: abc
    - **Expected output**: abc

### Method under test: `removeSpecificCards(List<Card> deck, CardType type, int count)`
- **TC1: NAME OF THE TEST CASE** ( :x: )
    - **State of the system**: abc
    - **Expected output**: abc

