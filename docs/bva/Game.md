# BVA Analysis: Game Class
### Method under test: `Game(List<Player> players, Deck drawPile, Deck discardPile)`
- **TC1: Constructor called with 1 player** ( :white_check_mark: )
  - **Name of the test**: constructor_invalidNumPlayers_failed
  - **State of the system**: players.size = 1
  - **Expected output**: throw IllegalArgumentException "error.minPlayers"

- **TC2: Constructor called with 2 players** ( :white_check_mark: )
  - **Name of the test**: constructor_validNumPlayers_initializeGame
  - **State of the system**: players.size = 2
  - **Expected output**:
    - isGameOngoing = false
    - isFaceUp = false
    - player1.addCardToHand with DEFUSE_5 called
    - player2.addCardToHand with DEFUSE_4 called
    - player.addCardToHand called with drawPile.removeTop 5 times for each player

- **TC3: Constructor called with 4 players** ( :white_check_mark: )
  - **Name of the test**: constructor_validNumPlayers_initializeGame
  - **State of the system**: players.size = 4
  - **Expected output**:
    - isGameOngoing = false
    - isFaceUp = false
    - player1.addCardToHand with DEFUSE_5 called
    - player2.addCardToHand with DEFUSE_4 called
    - player3.addCardToHand with DEFUSE_3 called
    - player4.addCardToHand with DEFUSE_2 called
    - player.addCardToHand called with drawPile.removeTop 5 times for each player

- **TC4: Constructor called with 5 players** ( :white_check_mark: )
  - **Name of the test**: constructor_invalidNumPlayers_failed
  - **State of the system**: players.size = 5
  - **Expected output**: throw IllegalArgumentException "error.maxPlayers"

- **TC5: Draw pile remove top throws exception** ( :white_check_mark: )
  - **Name of the test**: constructor_drawPileThrowsException_failed
  - **State of the system**: players.size = 2, drawPile.removeTop throws IllegalStateException "error.emptyDeck"
  - **Expected output**: throw IllegalStateException "error.emptyDeck"

### Method under test: `startGame()`
- **TC6: Game is already ongoing** ( :white_check_mark: )
  - **Name of the test**: startGame_gameIsOngoing_failed
  - **State of the system**: isGameOngoing = true
  - **Expected output**: throw IllegalStateException "error.gameAlreadyStarted"

- **TC7: Start game with 2 players** ( :white_check_mark: )
  - **Name of the test**: startGame_gameIsNotOngoing_startFirstRound
  - **State of the system**: players.size = 2, isGameOngoing = false
  - **Expected output**:
    - drawPile.addCard called once (N-1=1) with EXPLODINGKITTEN_1
    - drawPile.shuffle called
    - isGameOngoing = true
    - roundCount = 1
    - drawCount = 1

- **TC8: Start game with 4 players** ( :white_check_mark: )
  - **Name of the test**: startGame_gameIsNotOngoing_startFirstRound
  - **State of the system**: players.size = 4, isGameOngoing = false
  - **Expected output**:
    - drawPile.addCard called three times (N-1=3) with EXPLODINGKITTEN_1 to 3
    - drawPile.shuffle called
    - isGameOngoing = true
    - roundCount = 1
    - drawCount = 1

### Method under test: `getCurrentPlayerIndex()`
- **TC9: This method is called** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerIndex_called_success
  - **State of the system**: N/A
  - **Expected output**: returns turnManager.getCurrentPlayerIndex

### Method under test: `getStartingPlayerIndex()`
- **TC10: This method is called** ( :white_check_mark: )
  - **Name of the test**: getStartingPlayerIndex_called_success
  - **State of the system**: N/A
  - **Expected output**: returns GameConstants.STARTING_PLAYER_INDEX

### Method under test: `getCurrentPlayerHandIds()`
- **TC11: Current player is 0, empty hand** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerHandIds_called_returnHandIds
  - **State of the system**: currentPlayerIndex = 0, hand = []
  - **Expected output**: returns []

- **TC12: Current player is 1, hand has one card** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerHandIds_called_returnHandIds
  - **State of the system**: currentPlayerIndex = 1, hand = [SKIP_1]
  - **Expected output**: returns ["SKIP_1"]

- **TC13: Current player is 0, hand has two different cards** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerHandIds_called_returnHandIds
  - **State of the system**: currentPlayerIndex = 1, hand = [SKIP_1, SKIP_2]
  - **Expected output**: returns ["SKIP_1", "SKIP_2"]

- **TC14: Current player is 0, hand has duplicate cards** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerHandIds_called_returnHandIds
  - **State of the system**: currentPlayerIndex = 1, hand = [SKIP_1, SKIP_1]
  - **Expected output**: returns ["SKIP_1", "SKIP_1"]

- **TC15: Current player is 0, hand two different card types** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerHandIds_called_returnHandIds
  - **State of the system**: currentPlayerIndex = 1, hand = [SKIP_1, ATTACK_3]
  - **Expected output**: returns ["SKIP_1", "ATTACK_3"]

### Method under test: `getCurrentPlayer()`
- **TC16: Current player is 0** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayer_called_returnCurrentPlayer
  - **State of the system**: currentPlayerIndex = 0, players = [player1, player2]
  - **Expected output**: returns player1

- **TC17: Current player is 0** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayer_called_returnCurrentPlayer
  - **State of the system**: currentPlayerIndex = 1, players = [player1, player2]
  - **Expected output**: returns player2

### Method under test: `canPlaySelected()`
- **TC18: No cards selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCards = []
  - **Expected output**: returns false

- **TC19: One Defuse selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCards = [DEFUSE_1]
  - **Expected output**: returns false

- **TC20: One Exploding Kitten selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCards = [EXPLODINGKITTEN_3]
  - **Expected output**: returns false

- **TC21: One Cat Card 1 selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCards = [CATCARD1_1]
  - **Expected output**: returns false

- **TC22: One Cat Card 2 selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCards = [CATCARD2_1]
  - **Expected output**: returns false

- **TC23: One Cat Card 3 selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCards = [CATCARD3_4]
  - **Expected output**: returns false

- **TC24: One Cat Card 4 selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCards = [CATCARD4_4]
  - **Expected output**: returns false

- **TC25: One Feral Cat selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCards = [FERALCAT_1]
  - **Expected output**: returns false

- **TC26: One Attack selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCards = [ATTACK_1]
  - **Expected output**: returns true

- **TC27: One Shuffle selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCards = [SHUFFLE_2]
  - **Expected output**: returns true

- **TC28: One Skip selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCards = [SKIP_3]
  - **Expected output**: returns true

- **TC29: One See The Future selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCards = [SEETHEFUTURE_4]
  - **Expected output**: returns true

- **TC30: One Catomic Bomb selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCards = [CATOMICBOMB_1]
  - **Expected output**: returns true

- **TC31: One Super Skip selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCards = [SUPERSKIP_1]
  - **Expected output**: returns true

- **TC32: One Godcat selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCards = [GODCAT_1]
  - **Expected output**: returns true

- **TC33: One Clone selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCards = [CLONE_1]
  - **Expected output**: returns true

- **TC34: One Swap Top And Bottom selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCards = [SWAPTOPANDBOTTOM_1]
  - **Expected output**: returns true

- **TC35: One Draw From The Bottom selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCards = [DRAWFROMTHEBOTTOM_1]
  - **Expected output**: returns true

- **TC36: One Targeted Attack selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCards = [TARGETEDATTACK_1]
  - **Expected output**: returns true

- **TC37: One Winner Winner Catnip Dinner selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCards = [WINNERWINNERCATNIPDINNER_1]
  - **Expected output**: returns true

- **TC38: One Ragebait selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCards = [RAGEBAIT_1]
  - **Expected output**: returns true

- **TC39: One Recycle selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCards = [RECYCLE_1]
  - **Expected output**: returns true

- **TC40: One Double Up selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCards = [DOUBLEUP_1]
  - **Expected output**: returns true

- **TC41: One Mild Draw selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCards = [MILDDRAW_1]
  - **Expected output**: returns true

### Method under test: `canEndTurn()`
- **TC42: Game is not ongoing, draw count is 0** ( :white_check_mark: )
  - **Name of the test**: canEndTurn_called_returnFalse
  - **State of the system**: isGameOngoing = false, drawCount = 0
  - **Expected output**: returns false

- **TC43: Game is not ongoing, draw count is 1** ( :white_check_mark: )
  - **Name of the test**: canEndTurn_called_returnFalse
  - **State of the system**: isGameOngoing = false, drawCount = 1
  - **Expected output**: returns false

- **TC44: Game is not ongoing, draw count is 2** ( :white_check_mark: )
  - **Name of the test**: canEndTurn_called_returnFalse
  - **State of the system**: isGameOngoing = false, drawCount = 2
  - **Expected output**: returns false

- **TC45: Game is ongoing, draw count is 1** ( :white_check_mark: )
  - **Name of the test**: canEndTurn_called_returnFalse
  - **State of the system**: isGameOngoing = true, drawCount = 1
  - **Expected output**: returns false

- **TC46: Game is ongoing, draw count is 2** ( :white_check_mark: )
  - **Name of the test**: canEndTurn_called_returnFalse
  - **State of the system**: isGameOngoing = true, drawCount = 1
  - **Expected output**: returns false

- **TC47: Game is ongoing, draw count is 0** ( :white_check_mark: )
  - **Name of the test**: canEndTurn_gameIsOngoingAndDrawCountZero_returnTrue
  - **State of the system**: isGameOngoing = true, drawCount = 0
  - **Expected output**: returns true

### Method under test: `isDrawPileEmpty()`
- **TC48: Empty draw pile** ( :white_check_mark: )
  - **Name of the test**: isDrawPileEmpty_emptyDrawPile_returnTrue
  - **State of the system**: drawPile = []
  - **Expected output**: returns true

- **TC49: Draw pile has one card** ( :white_check_mark: )
  - **Name of the test**: isDrawPileEmpty_called_returnFalse
  - **State of the system**: drawPile = [SKIP_1]
  - **Expected output**: returns false

- **TC50: Draw pile has two cards of same type** ( :white_check_mark: )
  - **Name of the test**: isDrawPileEmpty_called_returnFalse
  - **State of the system**: drawPile = [SKIP_1, SKIP_2]
  - **Expected output**: returns false

- **TC51: Draw pile has two cards with same ID** ( :white_check_mark: )
  - **Name of the test**: isDrawPileEmpty_called_returnFalse
  - **State of the system**: drawPile = [SKIP_1, SKIP_1]
  - **Expected output**: returns false

- **TC52: Draw pile has two different type cards** ( :white_check_mark: )
  - **Name of the test**: isDrawPileEmpty_called_returnFalse
  - **State of the system**: drawPile = [SKIP_1, ATTACK_3]
  - **Expected output**: returns false

### Method under test: `getCanDraw()`
- **TC53: Game is not ongoing, draw count is 0** ( :x: )
  - **Name of the test**: getCanDraw_called_returnFalse
  - **State of the system**: isGameOngoing = false, drawCount = 0
  - **Expected output**: returns false

- **TC54: Game is not ongoing, draw count is 1** ( :x: )
  - **Name of the test**: getCanDraw_called_returnFalse
  - **State of the system**: isGameOngoing = false, drawCount = 1
  - **Expected output**: returns false

- **TC55: Game is not ongoing, draw count is 2** ( :x: )
  - **Name of the test**: getCanDraw_called_returnFalse
  - **State of the system**: isGameOngoing = false, drawCount = 2
  - **Expected output**: returns false

- **TC56: Game is ongoing, draw count is 0** ( :x: )
  - **Name of the test**: getCanDraw_called_returnFalse
  - **State of the system**: isGameOngoing = true, drawCount = 0
  - **Expected output**: returns false

- **TC57: Game is ongoing, draw count is 1** ( :x: )
  - **Name of the test**: getCanDraw_called_returnTrue
  - **State of the system**: isGameOngoing = true, drawCount = 1
  - **Expected output**: returns true

- **TC58: Game is ongoing, draw count is 2** ( :x: )
  - **Name of the test**: getCanDraw_called_returnTrue
  - **State of the system**: isGameOngoing = true, drawCount = 1
  - **Expected output**: returns true

### Method under test: `changeCurrentPlayerIndex(int newPlayerIndex)`
- **TC59: This method is called** ( :x: )
  - **Name of the test**: changeCurrentPlayerIndex_called_callsTurnManager
  - **State of the system**: newPlayerIndex = 0
  - **Expected output**: calls turnManager.setCurrentPlayerIndex with newPlayerIndex

### Method under test: `setFaceUpToFalse()`
- **TC60: Is face up** ( :x: )
  - **Name of the test**: setFaceUpToFalse_isFaceUp_setToFalse
  - **State of the system**: isFaceUp = true
  - **Expected output**: isFaceUp = false

- **TC61: Is face down** ( :x: )
  - **Name of the test**: setFaceUpToFalse_isFaceDown_setToFalse
  - **State of the system**: isFaceUp = false
  - **Expected output**: isFaceUp = false

### Method under test: `drawFromPile()`
- **TC62: Empty draw pile** ( :x: )
  - **Name of the test**: drawFromPile_emptyDrawPile_failed
  - **State of the system**: drawPile.removeTop throws IllegalStateException "error.emptyDeck"
  - **Expected output**: throw IllegalStateException "error.emptyDeck"

- **TC63: Draw pile has one card** ( :x: )
  - **Name of the test**: drawFromPile_oneCardInDrawPile_addToCurrentPlayerHand
  - **State of the system**: currentPlayerIndex = 0, drawPile = [SKIP_1]
  - **Expected output**:
    - getCurrentPlayer.addCardToHand is called with drawPile.removeTop
    - currentPlayerIndex = 0

### Method under test: `toggleFaceUp()`
- **TC64: Is face up** ( :x: )
  - **Name of the test**: toggleFaceUp_isFaceUp_setToFalse
  - **State of the system**: isFaceUp = true
  - **Expected output**: isFaceUp = false

- **TC65: Is face down** ( :x: )
  - **Name of the test**: toggleFaceUp_isFaceDown_setToTrue
  - **State of the system**: isFaceUp = false
  - **Expected output**: isFaceUp = true

### Method under test: `toggleSelectedCurrentPlayerCardAt(int handCardIndex)`
- **TC66: Hand card index at 0** ( :x: )
  - **Name of the test**: toggleSelectedCurrentPlayerCardAt_indexZero_calledPlayerToggle
  - **State of the system**: handCardIndex = 0
  - **Expected output**: getCurrentPlayer.toggleSelectedHandCardAt is called with handCardIndex

- **TC67: Hand card index at 1** ( :x: )
  - **Name of the test**: toggleSelectedCurrentPlayerCardAt_indexOne_calledPlayerToggle
  - **State of the system**: handCardIndex = 1
  - **Expected output**: getCurrentPlayer.toggleSelectedHandCardAt is called with handCardIndex

- **TC68: Player method throws exception** ( :x: )
  - **Name of the test**: toggleSelectedCurrentPlayerCardAt_indexZero_failed
  - **State of the system**: 
    - handCardIndex = 0
    - getCurrentPlayer.toggleSelectedHandCardAt with handCardIndex throws InvalidArgumentException "error.handCardIndexOutOfBounds"
  - **Expected output**: throws InvalidArgumentException "error.handCardIndexOutOfBounds"

### Method under test: `advanceTurn()`
- **TC69: Can end turn** ( :x: )
  - **Name of the test**: advanceTurn_canEndTurn_advanceTurnAndDeselectCards
  - **State of the system**: canEndTurn = true
  - **Expected output**:
    - turnManager.advanceTurn is called
    - getCurrentPlayer.deselectHandCards is called

- **TC70: Cannot end turn** ( :x: )
  - **Name of the test**: advanceTurn_cannotEndTurn_failed
  - **State of the system**: canEndTurn = false
  - **Expected output**: throws InvalidStateException "error.cannotEndTurn"