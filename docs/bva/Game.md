# BVA Analysis: Game Class
### Method under test: `Game(List<Player> players, Deck drawPile, Deck discardPile)`
- **TC1: Constructor called** ( :white_check_mark: )
  - **Name of the test**: constructor_anyInput_initializeFieldsFalse
  - **State of the system**: N/A
  - **Expected output**: isGameOngoing = false, isFaceUp = false

### Method under test: `setUp()`
- **TC2: Set up for 1 player** ( :white_check_mark: )
  - **Name of the test**: setUp_invalidNumPlayers_failed
  - **State of the system**: players.size = 1
  - **Expected output**: throw IllegalArgumentException "error.minPlayers"

- **TC3: Set up for 2 players** ( :white_check_mark: )
  - **Name of the test**: setUp_validNumPlayers_initializeGame
  - **State of the system**: players.size = 2
  - **Expected output**:
    - player1.addCardToHand with DEFUSE_5 called
    - player2.addCardToHand with DEFUSE_4 called
    - player.addCardToHand called with drawPile.removeTop 5 times for each player

- **TC4: Set up for 4 players** ( :white_check_mark: )
  - **Name of the test**: setUp_validNumPlayers_initializeGame
  - **State of the system**: players.size = 4
  - **Expected output**:
    - player1.addCardToHand with DEFUSE_5 called
    - player2.addCardToHand with DEFUSE_4 called
    - player3.addCardToHand with DEFUSE_3 called
    - player4.addCardToHand with DEFUSE_2 called
    - player.addCardToHand called with drawPile.removeTop 5 times for each player

- **TC5: Set up for 5 players** ( :white_check_mark: )
  - **Name of the test**: setUp_invalidNumPlayers_failed
  - **State of the system**: players.size = 5
  - **Expected output**: throw IllegalArgumentException "error.maxPlayers"

- **TC6: Draw pile remove top throws exception** ( :white_check_mark: )
  - **Name of the test**: setUp_drawPileThrowsException_failed
  - **State of the system**: players.size = 2, drawPile.removeTop throws IllegalStateException "error.emptyDeck"
  - **Expected output**: throw IllegalStateException "error.emptyDeck"

### Method under test: `startGame()`
- **TC7: Game is already ongoing** ( :white_check_mark: )
  - **Name of the test**: startGame_gameIsOngoing_failed
  - **State of the system**: isGameOngoing = true
  - **Expected output**: throw IllegalStateException "error.gameAlreadyStarted"

- **TC8: Start game with 2 players** ( :white_check_mark: )
  - **Name of the test**: startGame_gameIsNotOngoing_startFirstRound
  - **State of the system**: players.size = 2, isGameOngoing = false
  - **Expected output**:
    - drawPile.addCard called once (N-1=1) with EXPLODINGKITTEN_1
    - drawPile.shuffle called
    - isGameOngoing = true

- **TC9: Start game with 4 players** ( :white_check_mark: )
  - **Name of the test**: startGame_gameIsNotOngoing_startFirstRound
  - **State of the system**: players.size = 4, isGameOngoing = false
  - **Expected output**:
    - drawPile.addCard called three times (N-1=3) with EXPLODINGKITTEN_1 to 3
    - drawPile.shuffle called
    - isGameOngoing = true

### Method under test: `getPlayerNames()`
- **TC10: Get names for two players** ( :white_check_mark: )
  - **Name of the test**: getPlayerNames_validNPlayers_returnNNames
  - **State of the system**: players = [Alice, Bob]
  - **Expected output**: returns ["Alice", "Bob"]

- **TC11: Get names for four players with duplicate names** ( :white_check_mark: )
  - **Name of the test**: getPlayerNames_validNPlayers_returnNNames
  - **State of the system**: players = [Alice, Alice, Audrey, Turkey]
  - **Expected output**: returns ["Alice", "Alice", "Audrey", "Turkey"]

### Method under test: `getCurrentPlayerIndex()`
- **TC12: This method is called** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerIndex_called_success
  - **State of the system**: N/A
  - **Expected output**: returns turnManager.getCurrentPlayerIndex

### Method under test: `getStartingPlayerIndex()`
- **TC13: This method is called** ( :white_check_mark: )
  - **Name of the test**: getStartingPlayerIndex_called_success
  - **State of the system**: N/A
  - **Expected output**: returns GameConstants.STARTING_PLAYER_INDEX

### Method under test: `getCurrentPlayerHandIds()`
- **TC14: Current player is 0, empty hand** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerHandIds_called_returnTurnManagerMethodCall
  - **State of the system**: N/A
  - **Expected output**: returns turnManager.getCurrentPlayerHandIds()

### Method under test: `getCurrentPlayer()`
- **TC19: Current player is 0** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayer_called_returnTurnManagerMethodCall
  - **State of the system**: N/A
  - **Expected output**: returns turnManager.getCurrentPlayer

### Method under test: `canPlaySelected()`
- **TC20: No cards selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCardTypes = []
  - **Expected output**: returns false

- **TC21: One Defuse selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCardTypes = [DEFUSE]
  - **Expected output**: returns false

- **TC22: One Exploding Kitten selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCardTypes = [EXPLODING_KITTEN]
  - **Expected output**: returns false

- **TC23: One Cat Card 1 selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCardTypes = [CAT_CARD_1]
  - **Expected output**: returns false

- **TC24: One Cat Card 2 selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCardTypes = [CAT_CARD_2]
  - **Expected output**: returns false

- **TC25: One Cat Card 3 selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCardTypes = [CAT_CARD_3]
  - **Expected output**: returns false

- **TC26: One Cat Card 4 selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCardTypes = [CAT_CARD_4]
  - **Expected output**: returns false

- **TC27: One Feral Cat selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCardTypes = [FERAL_CAT]
  - **Expected output**: returns false

- **TC28: One Attack selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [ATTACK]
  - **Expected output**: returns true

- **TC29: One Shuffle selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [SHUFFLE]
  - **Expected output**: returns true

- **TC30: One Skip selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [SKIP]
  - **Expected output**: returns true

- **TC31: One See The Future selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [SEE_THE_FUTURE]
  - **Expected output**: returns true

- **TC32: One Catomic Bomb selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [CATOMIC_BOMB]
  - **Expected output**: returns true

- **TC33: One Super Skip selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [SUPER_SKIP]
  - **Expected output**: returns true

- **TC34: One Godcat selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [GODCAT]
  - **Expected output**: returns true

- **TC35: One Clone selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [CLONE]
  - **Expected output**: returns true

- **TC36: One Swap Top And Bottom selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [SWAP_TOP_AND_BOTTOM]
  - **Expected output**: returns true

- **TC37: One Draw From The Bottom selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [DRAW_FROM_THE_BOTTOM]
  - **Expected output**: returns true

- **TC38: One Targeted Attack selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [TARGETED_ATTACK]
  - **Expected output**: returns true

- **TC39: One Winner Winner Catnip Dinner selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [WINNER_WINNER_CATNIP_DINNER]
  - **Expected output**: returns true

- **TC40: One Ragebait selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [RAGEBAIT]
  - **Expected output**: returns true

- **TC41: One Recycle selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [RECYCLE]
  - **Expected output**: returns true

- **TC42: One Double Up selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [DOUBLE_UP]
  - **Expected output**: returns true

- **TC43: One Mild Draw selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [MILD_DRAW]
  - **Expected output**: returns true

### Method under test: `playSelectedCards()`
- **TC44: Selected cards cannot be played** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_invalidPlay_failed
  - **State of the system**: canPlaySelected returns false
  - **Expected output**: throws IllegalStateException "error.cannotPlaySelectedCards"

- **TC45: Valid play with unknown card type** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithUnknownCardType_failed
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [DEFUSE]
  - **Expected output**: throws IllegalStateException "error.cannotPlaySelectedCards"

- **TC46: Valid play with one Attack** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlay_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [ATTACK]
  - **Expected output**:
    - card1.toggleSelected is called
    - turnManager.removeCardFromCurrentPlayerHand(card1) is called
    - discardPile.addCard(card1) is called
    - applyAttack is called

- **TC47: Valid play with one Shuffle** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlay_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [SHUFFLE]
  - **Expected output**:
    - card1.toggleSelected is called
    - turnManager.removeCardFromCurrentPlayerHand(card1) is called
    - discardPile.addCard(card1) is called
    - applyShuffle is called

- **TC48: Valid play with one Skip** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlay_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [SKIP]
  - **Expected output**:
    - card1.toggleSelected is called
    - turnManager.removeCardFromCurrentPlayerHand(card1) is called
    - discardPile.addCard(card1) is called
    - applySkip is called

- **TC49: Valid play with one See The Future** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlay_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [SEE_THE_FUTURE]
  - **Expected output**:
    - card1.toggleSelected is called
    - turnManager.removeCardFromCurrentPlayerHand(card1) is called
    - discardPile.addCard(card1) is called
    - applySeeTheFuture is called

- **TC50: Valid play with one Catomic Bomb** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlay_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [CATOMIC_BOMB]
  - **Expected output**:
    - card1.toggleSelected is called
    - turnManager.removeCardFromCurrentPlayerHand(card1) is called
    - discardPile.addCard(card1) is called
    - applyCatomicBomb is called

- **TC51: Valid play with one Super Skip** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlay_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [SUPER_SKIP]
  - **Expected output**:
    - card1.toggleSelected is called
    - turnManager.removeCardFromCurrentPlayerHand(card1) is called
    - discardPile.addCard(card1) is called
    - applySuperSkip is called

- **TC52: Valid play with one Godcat** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlay_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [GODCAT]
  - **Expected output**:
    - card1.toggleSelected is called
    - turnManager.removeCardFromCurrentPlayerHand(card1) is called
    - discardPile.addCard(card1) is called
    - applyGodcat is called

- **TC53: Valid play with one Clone** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlay_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [CLONE]
  - **Expected output**:
    - card1.toggleSelected is called
    - turnManager.removeCardFromCurrentPlayerHand(card1) is called
    - discardPile.addCard(card1) is called
    - applyClone is called

- **TC54: Valid play with one Swap Top And Bottom** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlay_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [SWAP_TOP_AND_BOTTOM]
  - **Expected output**:
    - card1.toggleSelected is called
    - turnManager.removeCardFromCurrentPlayerHand(card1) is called
    - discardPile.addCard(card1) is called
    - applySwapTopAndBottom is called

- **TC55: Valid play with one Draw From The Bottom** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlay_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [DRAW_FROM_THE_BOTTOM]
  - **Expected output**:
    - card1.toggleSelected is called
    - turnManager.removeCardFromCurrentPlayerHand(card1) is called
    - discardPile.addCard(card1) is called
    - applyDrawFromTheBottom is called

- **TC56: Valid play with one Targeted Attack** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlay_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [TARGETED_ATTACK]
  - **Expected output**:
    - card1.toggleSelected is called
    - turnManager.removeCardFromCurrentPlayerHand(card1) is called
    - discardPile.addCard(card1) is called
    - applyTargetedAttack is called

- **TC57: Valid play with one Winner Winner Catnip Dinner** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlay_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [WINNER_WINNER_CATNIP_DINNER]
  - **Expected output**:
    - card1.toggleSelected is called
    - turnManager.removeCardFromCurrentPlayerHand(card1) is called
    - discardPile.addCard(card1) is called
    - applyWinnerWinnerCatnipDinner is called

- **TC58: Valid play with one Ragebait** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlay_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [RAGEBAIT]
  - **Expected output**:
    - card1.toggleSelected is called
    - turnManager.removeCardFromCurrentPlayerHand(card1) is called
    - discardPile.addCard(card1) is called
    - applyRagebait is called

- **TC59: Valid play with one Recycle** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlay_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [RECYCLE]
  - **Expected output**:
    - card1.toggleSelected is called
    - turnManager.removeCardFromCurrentPlayerHand(card1) is called
    - discardPile.addCard(card1) is called
    - applyRecycle is called

- **TC60: Valid play with one Double Up** ( :white_check_mark )
  - **Name of the test**: playSelectedCards_validPlay_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [DOUBLE_UP]
  - **Expected output**:
    - card1.toggleSelected is called
    - turnManager.removeCardFromCurrentPlayerHand(card1) is called
    - discardPile.addCard(card1) is called
    - applyDoubleUp is called

- **TC61: Valid play with one Mild Draw** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlay_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [MILD_DRAW]
  - **Expected output**:
    - card1.toggleSelected is called
    - turnManager.removeCardFromCurrentPlayerHand(card1) is called
    - discardPile.addCard(card1) is called
    - applyMildDraw is called

### Method under test: `getTopDiscardId()`
- **TC62: Empty discard pile** ( :white_check_mark: )
  - **Name of the test**: getTopDiscardId_emptyDiscardPile_failed
  - **State of the system**: discardPile.peekTop throws InvalidStateException "error.emptyDeck"
  - **Expected output**: throws IllegalStateException "error.emptyDeck"

- **TC63: Non-empty discard pile** ( :white_check_mark: )
  - **Name of the test**: getTopDiscardId_nonEmptyDiscardPile_returnTopCardId
  - **State of the system**: topDiscardPileId = "SKIP_1"
  - **Expected output**: returns drawPile.peekTop.getId

### Method under test: `getCanDrawFromDiscard()`
- **TC64: Empty discard pile** ( :white_check_mark: )
  - **Name of the test**: canDrawFromDiscard_none_returnFalse
  - **State of the system**: N/A
  - **Expected output**: returns false

### Method under test: `canEndTurn()`
- **TC65: Game is not ongoing, draw count is 0** ( :white_check_mark: )
  - **Name of the test**: canEndTurn_called_returnFalse
  - **State of the system**: isGameOngoing = false, turnManager.getDrawCount = 0
  - **Expected output**: returns false

- **TC66: Game is not ongoing, draw count is 1** ( :white_check_mark: )
  - **Name of the test**: canEndTurn_called_returnFalse
  - **State of the system**: isGameOngoing = false, turnManager.getDrawCount = 1
  - **Expected output**: returns false

- **TC67: Game is not ongoing, draw count is 2** ( :white_check_mark: )
  - **Name of the test**: canEndTurn_called_returnFalse
  - **State of the system**: isGameOngoing = false, turnManager.getDrawCount = 2
  - **Expected output**: returns false

- **TC68: Game is ongoing, draw count is 1** ( :white_check_mark: )
  - **Name of the test**: canEndTurn_called_returnFalse
  - **State of the system**: isGameOngoing = true, turnManager.getDrawCount = 1
  - **Expected output**: returns false

- **TC69: Game is ongoing, draw count is 2** ( :white_check_mark: )
  - **Name of the test**: canEndTurn_called_returnFalse
  - **State of the system**: isGameOngoing = true, turnManager.getDrawCount = 1
  - **Expected output**: returns false

- **TC70: Game is ongoing, draw count is 0** ( :white_check_mark: )
  - **Name of the test**: canEndTurn_gameIsOngoingAndDrawCountZero_returnTrue
  - **State of the system**: isGameOngoing = true, turnManager.getDrawCount = 0
  - **Expected output**: returns true

### Method under test: `isDrawPileEmpty()`
- **TC71: Empty draw pile** ( :white_check_mark: )
  - **Name of the test**: isDrawPileEmpty_emptyDrawPile_returnTrue
  - **State of the system**: drawPile.isEmpty = true
  - **Expected output**: returns true

- **TC72: Non-empty draw pile** ( :white_check_mark: )
  - **Name of the test**: isDrawPileEmpty_called_returnFalse
  - **State of the system**: drawPile.isEmpty = false
  - **Expected output**: returns false

### Method under test: `getCanDraw()`
- **TC75: Game is not ongoing, draw count is 0** ( :white_check_mark: )
  - **Name of the test**: getCanDraw_called_returnFalse
  - **State of the system**: isGameOngoing = false, turnManager.getDrawCount = 0
  - **Expected output**: returns false

- **TC76: Game is not ongoing, draw count is 1** ( :white_check_mark: )
  - **Name of the test**: getCanDraw_called_returnFalse
  - **State of the system**: isGameOngoing = false, turnManager.getDrawCount = 1
  - **Expected output**: returns false

- **TC77: Game is not ongoing, draw count is 2** ( :white_check_mark: )
  - **Name of the test**: getCanDraw_called_returnFalse
  - **State of the system**: isGameOngoing = false, turnManager.getDrawCount = 2
  - **Expected output**: returns false

- **TC78: Game is ongoing, draw count is 0** ( :white_check_mark: )
  - **Name of the test**: getCanDraw_called_returnFalse
  - **State of the system**: isGameOngoing = true, turnManager.getDrawCount = 0
  - **Expected output**: returns false

- **TC79: Game is ongoing, draw count is 1** ( :white_check_mark: )
  - **Name of the test**: getCanDraw_called_returnTrue
  - **State of the system**: isGameOngoing = true, turnManager.getDrawCount = 1
  - **Expected output**: returns true

- **TC80: Game is ongoing, draw count is 2** ( :white_check_mark: )
  - **Name of the test**: getCanDraw_called_returnTrue
  - **State of the system**: isGameOngoing = true, turnManager.getDrawCount = 1
  - **Expected output**: returns true

### Method under test: `changeCurrentPlayerIndex(int newPlayerIndex)`
- **TC81: This method is called** ( :white_check_mark: )
  - **Name of the test**: changeCurrentPlayerIndex_called_callsTurnManager
  - **State of the system**: newPlayerIndex = 0
  - **Expected output**: calls turnManager.setCurrentPlayerIndex with newPlayerIndex

### Method under test: `setFaceUpToFalse()`
- **TC82: Is face up** ( :white_check_mark: )
  - **Name of the test**: setFaceUpToFalse_isFaceUp_setToFalse
  - **State of the system**: isFaceUp = true
  - **Expected output**: isFaceUp = false

- **TC83: Is face down** ( :white_check_mark: )
  - **Name of the test**: setFaceUpToFalse_isFaceDown_setToFalse
  - **State of the system**: isFaceUp = false
  - **Expected output**: isFaceUp = false

### Method under test: `drawFromPile()`
- **TC84: This method is called successfully** ( :white_check_mark: )
  - **Name of the test**: drawFromPile_called_callDrawPileAndTurnManagerMethods
  - **State of the system**: N/A
  - **Expected output**: turnManager.updateAfterDraw is called with drawPile.removeTop

- **TC84: Throw exception** ( :white_check_mark: )
  - **Name of the test**: drawFromPile_called_failed
  - **State of the system**: drawPile.removeTop throws IllegalStateException "error.emptyDeck"
  - **Expected output**: throw IllegalStateException "error.emptyDeck"

### Method under test: `toggleFaceUp()`
- **TC87: Is face up** ( :white_check_mark: )
  - **Name of the test**: toggleFaceUp_called_setToFalse
  - **State of the system**: isFaceUp = true
  - **Expected output**: isFaceUp = false

- **TC88: Is face down** ( :white_check_mark: )
  - **Name of the test**: toggleFaceUp_called_togglesFaceUp
  - **State of the system**: isFaceUp = false
  - **Expected output**: isFaceUp = true

### Method under test: `toggleSelectedCurrentPlayerCardAt(int handCardIndex)`
- **TC89: Hand card index at 0** ( :white_check_mark: )
  - **Name of the test**: toggleSelectedCurrentPlayerCardAt_called_calledPlayerToggle
  - **State of the system**: handCardIndex = 0
  - **Expected output**: turnManager.toggleSelectedPlayerCardAt is called with handCardIndex

- **TC91: Player method throws exception** ( :x: )
  - **Name of the test**: toggleSelectedCurrentPlayerCardAt_indexZero_failed
  - **State of the system**: turnManager.toggleSelectedPlayerCardAt throws InvalidArgumentException "error.handCardIndexOutOfBounds"
  - **Expected output**: throw InvalidArgumentException "error.handCardIndexOutOfBounds"

### Method under test: `advanceTurn()`
- **TC92: Can end turn** ( :x: )
  - **Name of the test**: advanceTurn_canEndTurn_advanceTurnAndDeselectCards
  - **State of the system**: canEndTurn = true
  - **Expected output**: turnManager.advanceTurn is called

- **TC93: Cannot end turn** ( :x: )
  - **Name of the test**: advanceTurn_cannotEndTurn_failed
  - **State of the system**: canEndTurn = false
  - **Expected output**: throws InvalidStateException "error.cannotEndTurn"

