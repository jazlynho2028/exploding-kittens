# BVA Analysis: Game Class
### Method under test: `Game()`
- **TC1: construct game with minimum valid players** ( :white_check_mark: )
  - **Name of the test**: constructor_minimumPlayers_initializesGameCorrectly
  - **State of the system**: Game constructed with two player names, a valid drawPile, and a valid discardPile
  - **Expected output**:
    - players list has length 2 (with matching names) 
    - isGameOngoing is false
    - isFaceUp is false
    - drawPile is initialized with a copy of the passed drawPile's cards
    - discardPile initialized with a copy of the passed discardPile's cards
    - turnManager is initialized using the players list
    - each player's hand contains exactly 6 cards
    - each player's first card is a defuse card 

- **TC2: construct game with maximum valid players** ( :white_check_mark: )
  - **Name of the test**: constructor_maximumPlayers_initializesGameCorrectly
  - **State of the system**: Game constructed with four player names, a valid drawPile, and a valid discardPile
  - **Expected output**:
    - players list has length 4 (with matching names) 
    - isGameOngoing is false
    - isFaceUp is false
    - drawPile initialized with a copy of the passed drawPile's cards
    - discardPile initialized with a copy of the passed discardPile's cards
    - turnManager is initialized using the players list
    - each player's hand contains exactly 6 cards 
    - each player's first card is a defuse card

- **TC3: construct game with too little players** ( :x: )
  - **Name of the test**: constructor_tooLittlePlayers_throwsIllegalArgumentException
  - **State of the system**: Game constructed with 1 player name
  - **Expected output**: IllegalArgumentException with message "error.invalidPlayerCount"

- **TC4: construct game with too many players** ( :x: )
  - **Name of the test**: constructor_tooManyPlayers_throwsIllegalArgumentException
  - **State of the system**: Game constructed with 5 player names
  - **Expected output**: IllegalArgumentException with message "error.invalidPlayerCount"

### Method under test: `startGame()`

- **TC6: start game with minimum valid players** ( :x: )
  - **Name of the test**: startGame_minimumPlayers_initializesGameAndDecks
  - **State of the system**: Game successfully constructed with 2 player names; isGameOngoing is false
  - **Expected output**: 
    - isGameOngoing is true
    - canDraw is true
    - players list has length 2
    - each player hand has 8 cards (1 Defuse, 7 other) 
    - drawPile contains N-1=1 Exploding Kitten card
    - drawPile contains 6-N=4 Defuse cards
    - turnManager initialized at array index 0

- **TC7:start game with maximum valid players** ( :x: )
  - **Name of the test**: startGame_maximumPlayers_initializesGameAndDecks
  - **State of the system**: Game successfully constructed with 4 player names; isGameOngoing is false
  - **Expected output**: 
    - isGameOngoing is true
    - canDraw is true
    - players list has length 4
    - each player hand as 8 cards (1 Defuse, 7 other) 
    - drawPile contains N-1=3 Exploding Kitten card
    - drawPile contains 6-N=2 Defuse cards
    - turnManager initialized at array index 0

- **TC8: start game with more than one valid player** ( :x: )
  - **Name of the test**: startGame_moreThanOnePlayer_initializesGameAndDecks
  - **State of the system**: Game successfully constructed with 3 player names; isGameOngoing is false
  - **Expected output**:
    - isGameOngoing is true
    - canDraw is true
    - players list has length 3
    - each player hand has 8 cards (1 Defuse, 7 other)
    - drawPile contains N-1=2 Exploding Kitten card
    - drawPile contains 6-N=3 Defuse cards
    - turnManager initialized at array index 0

- **TC9: start game when with too little players** ( :x: )
  - **Name of the test**: startGame_tooLittlePlayers_throwsGameException
  - **State of the system**: Game constructed with 1 player name; startGame() is called
  - **Expected output**: GameException (with key "error.invalidPlayerCount")

- **TC10: start game with too many players** ( :x: )
  - **Name of the test**: startGame_tooManyPlayers_throwsGameException
  - **State of the system**: Game constructed with 5 player names; startGame() is called
  - **Expected output**: GameException (with key "error.invalidPlayerCount")

- **TC11: start game when game is already ongoing** ( :x: )
  - **Name of the test**: startGame_gameIsAlreadyOngoing_throwsGameException
  - **State of the system**: isGameOngoing is true; startGame() is called
  - **Expected output**: GameException (with key "error.gameAlreadyStarted")