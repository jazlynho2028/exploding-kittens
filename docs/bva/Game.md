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

- **TC8: populate hands when drawPile is short by one card** ( :white_check_mark: )
  - **Name of the test**: populatePlayerHands_insufficientCards_throwsException
  - **State of the system**: Game has 3 players; drawPile contains 1 card fewer than the sufficient amount of cards (5 x 3)
  - **Expected output**:
    - IllegalArgumentException with message "error.emptyDrawPile"

### Method under test: `startGame()`

- **TC9: call startGame() when game has not started yet** ( :white_check_mark: )
  - **Name of the test**: startGame_gameNotStarted_initializesGameSuccessfully
  - **State of the system**: 
    - isGameOngoing is false 
    - draw pile is initialized with standard player starting hands already dealt out
    - turnManager is at its initial state (round 0, draw count 0)
  - **Expected output**:
    - isGameOngoing is true
    - Exploding Kittens are successfully inserted into the draw pile
    - drawPile.shuffle() is called exactly once
    - turnManger.incrementRount() and incrementDrawCount() are executed successfully

- **TC10: call startGame() when game is already in progress** ( :white_check_mark: )
  - **Name of the test**: startGame_gameAlreadyStarted_throwsIllegalStateException
  - **State of the system**:
    - isGameOngoing is true
  - **Expected output**:
    - IllegalStateException with message "error.gameAlreadyStarted"
    - no modifications made to drawPile
    - turnManager states are untouched

### Method under test: `addExplodingKittensToDrawPile()`

- **TC11: add exploding kitten cards to the lower valid player boundary** ( :white_check_mark: )
  - **Name of the test**: addExplodingKittensToDrawPile_twoPlayers_addsOneKitten
  - **State of the system**:
    - Game is initialized with 2 players 
    - drawPile is completely empty before calling method 
  - **Expected output**:
    - 1 (2 - 1) Exploding Kitten card is added to the drawPile 
    - the added card has ID "exploding_kitten-1" and has CardType.EXPLODING_KITTEN

- **TC12: add exploding kitten cards to game with more than one valid player** ( :white_check_mark: )
  - **Name of the test**: addExplodingKittensToDrawPile_threePlayers_addsTwoKittens
  - **State of the system**:
    - Game is initialized with 3 players
    - drawPile is completely empty before calling method
  - **Expected output**:
    - 2 (3 - 1) Exploding Kitten cards are added to the drawPile
    - the added cards have ID "exploding_kitten-1" and "exploding_kitten-2" and have CardType.EXPLODING_KITTEN

- **TC13: add exploding kitten cards to game with upper valid player boundary** ( :white_check_mark: )
  - **Name of the test**: addExplodingKittensToDrawPile_fourPlayers_addsThreeKittens
  - **State of the system**:
    - Game is initialized with 4 players
    - drawPile is completely empty before calling method
  - **Expected output**:
    - 3 (4 - 1) Exploding Kitten cards are added to the drawPile
    - the added cards have ID "exploding_kitten-1", "exploding_kitten-2", and "exploding_kitten-3" and have CardType.EXPLODING_KITTEN

- **TC14: add exploding kitten cards to game with insufficient players** ( :white_check_mark: )
  - **Name of the test**: addExplodingKittensToDrawPile_onePlayer_gameThrowsException
  - **State of the system**:
    - Game is forced to be initialized with 1 player
    - drawPile is completely empty before calling method
  - **Expected output**: Game constructor throws IllegalArgumentException immediately with the message "error.invalidPlayerCount"

- **TC15: add exploding kitten cards to game with too many players** ( :white_check_mark: )
  - **Name of the test**: addExplodingKittensToDrawPile_fivePlayers_gameThrowsException
  - **State of the system**:
    - Game is forced to be initialized with 5 players
    - drawPile is completely empty before calling method
  - **Expected output**: Game constructor throws IllegalArgumentException immediately with the message "error.invalidPlayerCount" 

### Method under test: `getCurrentPlayerIds()`

- **TC16: get IDs when current player's hand is completely empty** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerHandIds_emptyHand_returnsEmptyList
  - **State of the system**:
    - Game is initialized
    - current active player has 0 cards in their hand
  - **Expected output**: 
    - method returns valid, non-null List<String> instance
    - returned list size is 0

- **TC17: get IDs when current player's hand has exactly one card** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerHandIds_singleCardInHand_returnsListWithOneId
  - **State of the system**:
    - Game is initialized
    - current active player has 1 card in their hand
    - card in hand has ID "defuse-5"
  - **Expected output**:
    - method returns List<String> instance with 1 element
    - returned list size is 1 
    - element in list has value "defuse-5"

- **TC18: get IDs when current player's hand has more than one card** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerHandIds_normalHandSize_returnsAllCardIds
  - **State of the system**:
    - Game is initialized
    - current active player has 6 cards in their hand
    - cards in hand have IDs "exploding_kitten-1", "defuse-1", "defuse-2", "defuse-3", "feral_cat-1", "feral_cat-2"
  - **Expected output**:
    - method returns List<String> instance with 6 elements
    - returned list size is 6
    - element in list have values: "exploding_kitten-1", "defuse-1", "defuse-2", "defuse-3", "feral_cat-1", "feral_cat-2" in the same order

### Method under test: ` canPlaySelected()`

- **TC19: player selected zero cards** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_zeroCardsSelected_returnsFalse
  - **State of the system**:
    - Game is initialized
    - current active player has marked 0 cards as selected in their hand 
  - **Expected output**:
    - return false

- **TC20: player selected exactly one card** ( :x: )
  - **Name of the test**: canPlaySelected_oneCardSelected_delegatesToValidOneCard
  - **State of the system**:
    - Game is initialized
    - current active player has marked 1 card as selected in their hand
  - **Expected output**:
    - return true (assuming card matches single-card rules like Attack or Skip)

- **TC21: player selected exactly two cards** ( :x: )
  - **Name of the test**: canPlaySelected_twoCardsSelected_delegatesToValidTwoCards
  - **State of the system**:
    - Game is initialized
    - current active player has marked 2 cards as selected in their hand
  - **Expected output**:
    - return true (assuming card matches double-card rules like pair of cat cards)

- **TC22: player selected exactly three cards** ( :x: )
  - **Name of the test**: canPlaySelected_threeCardsSelected_delegatesToValidThreeCards
  - **State of the system**:
    - Game is initialized
    - current active player has marked 3 cards as selected in their hand
  - **Expected output**:
    - return true (assuming card matches triple-card rules like three-of-a-kind cards)

- **TC23: player selected exactly four cards** ( :x: )
  - **Name of the test**: canPlaySelected_fourCardsSelected_returnsFalse
  - **State of the system**:
    - Game is initialized
    - current active player has marked 4 cards as selected in their hand
  - **Expected output**:
    - return false 
