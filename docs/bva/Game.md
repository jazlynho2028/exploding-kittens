# BVA Analysis: Game Class
### Method under test: `Game(List<Player> players, Deck drawPile, Deck discardPile)`
- **TC1: Constructor called with 1 player** ( :x: )
  - **Name of the test**: constructor_onePlayer_failed
  - **State of the system**: players.size = 1
  - **Expected output**:
    - IllegalArgumentException "error.minPlayers"

- **TC2: Constructor called with 2 players** ( :x: )
  - **Name of the test**: constructor_twoPlayers_initializeGame
  - **State of the system**: players.size = 2
  - **Expected output**:
    - isGameOngoing = false
    - isFaceUp = false
    - populatePlayerHands is called

- **TC3: Constructor called with 4 players** ( :x: )
  - **Name of the test**: constructor_fourPlayers_initializeGame
  - **State of the system**: players.size = 4
  - **Expected output**:
    - isGameOngoing = false
    - isFaceUp = false
    - populatePlayerHands is called

- **TC4: Constructor called with 5 players** ( :x: )
  - **Name of the test**: constructor_fivePlayers_failed
  - **State of the system**: players.size = 5
  - **Expected output**:
    - IllegalArgumentException "error.maxPlayers"

### Method under test: `startGame()`
- **TC5: Game is already ongoing** ( :x: )
  - **Name of the test**: startGame_gameIsOngoing_failed
  - **State of the system**: isGameOngoing = true
  - **Expected output**:
    - IllegalStateException "error.gameAlreadyStarted"

- **TC6: Start game with 2 players** ( :x: )
  - **Name of the test**: startGame_twoPlayers_startFirstRound
  - **State of the system**: players.size = 2, isGameOngoing = false
  - **Expected output**:
    - drawPile.addCard called once (N-1=1) with EXPLODINGKITTEN_1
    - drawPile.shuffle called
    - isGameOngoing = true
    - roundCount = 1
    - drawCount = 1

- **TC7: Start game with 4 players** ( :x: )
  - **Name of the test**: startGame_fourPlayers_startFirstRound
  - **State of the system**: players.size = 4, isGameOngoing = false
  - **Expected output**:
    - drawPile.addCard called three times (N-1=3) with EXPLODINGKITTEN_1 to 3
    - drawPile.shuffle called
    - isGameOngoing = true
    - roundCount = 1
    - drawCount = 1

### Method under test: `getCurrentPlayerHandIds()`
- **TC8: Current player is 0, empty hand** ( :x: )
  - **Name of the test**: getCurrentPlayerHandIds_emptyHand_emptyIds
  - **State of the system**: currentPlayerIndex = 0, hand = []
  - **Expected output**: returns []

- **TC9: Current player is 1, hand has one card** ( :x: )
  - **Name of the test**: getCurrentPlayerHandIds_emptyHand_emptyIds
  - **State of the system**: currentPlayerIndex = 1, hand = [SKIP_1]
  - **Expected output**: returns ["SKIP_1"]

- **TC10: Current player is 0, hand has two different cards** ( :x: )
  - **Name of the test**: getCurrentPlayerHandIds_emptyHand_emptyIds
  - **State of the system**: currentPlayerIndex = 1, hand = [SKIP_1, SKIP_2]
  - **Expected output**: returns ["SKIP_1", "SKIP_2"]

- **TC11: Current player is 0, hand has duplicate cards** ( :x: )
  - **Name of the test**: getCurrentPlayerHandIds_emptyHand_emptyIds
  - **State of the system**: currentPlayerIndex = 1, hand = [SKIP_1, SKIP_1]
  - **Expected output**: returns ["SKIP_1", "SKIP_1"]

### Method under test: `canPlaySelected()`
- **TC12: Current player is 0, empty hand** ( :x: )
  - **Name of the test**: getCurrentPlayerHandIds_emptyHand_emptyIds
  - **State of the system**: currentPlayerIndex = 0, hand = []
  - **Expected output**: returns []


### Method under test: `startGame()`
- **TC1: start game with minimum valid players** ( :x: )
  - **Name of the test**: testConstructor_MinValidPlayers
  - **State of the system**: Game constructed with two players
  - **Expected output**:
    - players list has length 2
    - isGameOngoing is true
    - canDraw is false
    - isFaceUp is false
    - drawPile is initialized as empty collection 
    - discardPile initialized as empty collection 
    - turnManager is null

- **TC2: start game with maximum valid players** ( :x: )
  - **Name of the test**: testConstructor_MaxValidPlayers
  - **State of the system**: Game constructed with four players
  - **Expected output**:
    - players list has length 4
    - isGameOngoing is true
    - canDraw is false
    - isFaceUp is false
    - drawPile initialized as empty collection 
    - discardPile initialized as empty collection 
    - turnManager is null

- **TC3: start game with too little players** ( :x: )
  - **Name of the test**: testConstructor_TooFewPlayers
  - **State of the system**: Game constructed with 1 player
  - **Expected output**: IllegalArgumentException "error.minPlayers"

- **TC4: start game with too many players** ( :x: )
  - **State of the system**: Game constructed with 5 players
  - **Expected output**: IllegalArgumentException "error.maxPlayers"

### Method under test: `startGame()`

- **TC6: start game with minimum valid players** ( :x: )
  - **Name of the test**: testStartGame_MinValidPlayersSuccess
  - **State of the system**: Game successfully constructed with 2 players; isGameOngoing is false
  - **Expected output**: 
    - isGameOngoing is true
    - canDraw is true
    - players list has length 2
    - each player hand has 8 cards (1 Defuse, 7 other) 
    - drawPile contains N-1=1 Exploding Kitten card
    - drawPile contains 6-N=4 Defuse cards
    - turnManager initialized at array index 0

- **TC7:start game with maximum valid players** ( :x: )
  - **State of the system**: Game successfully constructed with 4 players; isGameOngoing is false
  - **Expected output**: 
    - isGameOngoing is true
    - canDraw is true
    - players list has length 4
    - each player hand as 8 cards (1 Defuse, 7 other) 
    - drawPile contains N-1=3 Exploding Kitten card
    - drawPile contains 6-N=2 Defuse cards
    - turnManager initialized at array index 0

- **TC8: start game with more than one valid player** ( :x: )
  - **State of the system**: Game successfully constructed with 3 players; isGameOngoing is false
  - **Expected output**:
    - isGameOngoing is true
    - canDraw is true
    - players list has length 3
    - each player hand has 8 cards (1 Defuse, 7 other)
    - drawPile contains N-1=2 Exploding Kitten card
    - drawPile contains 6-N=3 Defuse cards
    - turnManager initialized at array index 0