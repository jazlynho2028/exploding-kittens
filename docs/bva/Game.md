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

- **TC3: construct game with too little players** ( :white_check_mark: )
  - **Name of the test**: constructor_tooLittlePlayers_throwsIllegalArgumentException
  - **State of the system**: Game constructed with 1 player name
  - **Expected output**: IllegalArgumentException with message "error.invalidPlayerCount"

- **TC4: construct game with too many players** ( :white_check_mark: )
  - **Name of the test**: constructor_tooManyPlayers_throwsIllegalArgumentException
  - **State of the system**: Game constructed with 5 player names
  - **Expected output**: IllegalArgumentException with message "error.invalidPlayerCount"

### Method under test: `popularPlayerHand()`

- **TC5: populate hands with minimum valid players** ( :white_check_mark: )
  - **Name of the test**: populatePlayerHands_minimumPlayers_allocatesCorrectCards
  - **State of the system**: Game has exactly 2 players; drawPile contains a sufficient amount of cards (5 x 2)
  - **Expected output**: 
    - Player 1 gets a DEFUSE card with ID "defuse-5"
    - Player 2 gets a DEFUSE card with ID "defuse-4"
    - Each player receives 5 cards drawn from the top of drawPile
    - total cards remaining in drawPile decreases by (5 x 2) 

- **TC6: populate hands with maximum valid players** ( :white_check_mark: )
  - **Name of the test**: populatePlayerHands_maximumPlayers_allocatesCorrectCards
  - **State of the system**: Game has exactly 4 players; drawPile contains a sufficient amount of cards (5 x 4)
  - **Expected output**:
    - Player 1 gets a DEFUSE card with ID "defuse-5"
    - Player 2 gets a DEFUSE card with ID "defuse-4"
    - Player 3 gets a DEFUSE card with ID "defuse-3"
    - Player 4 gets a DEFUSE card with ID "defuse-2"
    - Each player receives 5 cards drawn from the top of drawPile
    - total cards remaining in drawPile decreases by (5 x 4)

- **TC7: populate hands when drawPile has exactly the minimum required cards** ( :white_check_mark: )
  - **Name of the test**: populatePlayerHands_exactCards_emptiesDeck
  - **State of the system**: Game has 3 players; drawPile contains exactly sufficient amount of cards (5 x 3)
  - **Expected output**:
    - Player 1 gets a DEFUSE card with ID "defuse-5"
    - Player 2 gets a DEFUSE card with ID "defuse-4"
    - Player 3 gets a DEFUSE card with ID "defuse-3"
    - Each player receives 5 cards drawn from the top of drawPile
    - the last remaining card is removed from drawPile without error/exception
    - drawPile has 0 cards remaining

- **TC8: populate hands when drawPile is short by one card** ( :x: )
  - **Name of the test**: populatePlayerHands_insufficientCards_throwsException
  - **State of the system**: Game has 3 players; drawPile contains 1 card fewer than the sufficient amount of cards (5 x 3)
  - **Expected output**:
    - IllegalArgumentException with message "error.emptyDrawPile"