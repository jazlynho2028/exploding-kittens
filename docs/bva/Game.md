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
    - drawPile.addCardToTop called once (N-1=1) with EXPLODINGKITTEN_1
    - drawPile.shuffle called
    - isGameOngoing = true

- **TC9: Start game with 4 players** ( :white_check_mark: )
  - **Name of the test**: startGame_gameIsNotOngoing_startFirstRound
  - **State of the system**: players.size = 4, isGameOngoing = false
  - **Expected output**:
    - drawPile.addCardToTop called three times (N-1=3) with EXPLODINGKITTEN_1 to 3
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
  - **State of the system**: currentPlayerIndex = i, i = {0, 1, 2}
  - **Expected output**: returns turnManager.getCurrentPlayerIndex

### Method under test: `getStartingPlayerIndex()`
- **TC13: This method is called** ( :white_check_mark: )
  - **Name of the test**: getStartingPlayerIndex_called_success
  - **State of the system**: N/A
  - **Expected output**: returns GameConstants.STARTING_PLAYER_INDEX

### Method under test: `getCurrentPlayer()`
- **TC14: Current player is 0** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayer_called_returnCurrentPlayer
  - **State of the system**:
    - players = [player1, player2]
    - turnManager.getCurrentPlayerIndex = 0
  - **Expected output**: returns player1

- **TC15: Current player is 1** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayer_called_returnCurrentPlayer
  - **State of the system**:
    - players = [player1, player2]
    - turnManager.getCurrentPlayerIndex = 1
  - **Expected output**: returns player2

### Method under test: `getCurrentPlayerHandIds()`
- **TC16: Current player is 0, empty hand** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerHandIds_called_returnPlayerMethodCall
  - **State of the system**: getCurrentPlayer = player
  - **Expected output**: returns player.getHandIds

### Method under test: `canPlaySelected()`
- **TC17: No cards selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCardTypes = []
  - **Expected output**: returns false

- **TC18: One Defuse selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCardTypes = [DEFUSE]
  - **Expected output**: returns false

- **TC19: One Exploding Kitten selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCardTypes = [EXPLODING_KITTEN]
  - **Expected output**: returns false

- **TC20: One Cat Card 1 selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCardTypes = [CAT_CARD_1]
  - **Expected output**: returns false

- **TC21: One Cat Card 2 selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCardTypes = [CAT_CARD_2]
  - **Expected output**: returns false

- **TC22: One Cat Card 3 selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCardTypes = [CAT_CARD_3]
  - **Expected output**: returns false

- **TC23: One Cat Card 4 selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCardTypes = [CAT_CARD_4]
  - **Expected output**: returns false

- **TC24: One Feral Cat selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: selectedCardTypes = [FERAL_CAT]
  - **Expected output**: returns false

- **TC25: One Attack selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [ATTACK]
  - **Expected output**: returns true

- **TC26: One Shuffle selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [SHUFFLE]
  - **Expected output**: returns true

- **TC27: One Skip selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [SKIP]
  - **Expected output**: returns true

- **TC28: One See The Future selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [SEE_THE_FUTURE]
  - **Expected output**: returns true

- **TC29: One Catomic Bomb selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [CATOMIC_BOMB]
  - **Expected output**: returns true

- **TC30: One Super Skip selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [SUPER_SKIP]
  - **Expected output**: returns true

- **TC31: One Godcat selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [GODCAT]
  - **Expected output**: returns true

- **TC32: One Clone selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [CLONE]
  - **Expected output**: returns true

- **TC33: One Swap Top And Bottom selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [SWAP_TOP_AND_BOTTOM]
  - **Expected output**: returns true

- **TC34: One Draw From The Bottom selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [DRAW_FROM_THE_BOTTOM]
  - **Expected output**: returns true

- **TC35: One Targeted Attack selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [TARGETED_ATTACK]
  - **Expected output**: returns true

- **TC36: One Winner Winner Catnip Dinner selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [WINNER_WINNER_CATNIP_DINNER]
  - **Expected output**: returns true

- **TC37: One Ragebait selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [RAGEBAIT]
  - **Expected output**: returns true

- **TC38: One Recycle selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [RECYCLE]
  - **Expected output**: returns true

- **TC39: One Double Up selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [DOUBLE_UP]
  - **Expected output**: returns true

- **TC40: One Mild Shuffle selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: selectedCardTypes = [MILD_SHUFFLE]
  - **Expected output**: returns true

### Method under test: `playSelectedCards()`
- **TC41: Selected cards cannot be played** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_invalidPlay_failed
  - **State of the system**: canPlaySelected returns false
  - **Expected output**: throws IllegalStateException "error.cannotPlaySelectedCards"

- **TC42: Valid play with unknown card type** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithUnknownCardType_failed
  - **State of the system**:
    - canPlaySelected returns true
    - selectedCardTypes = [DEFUSE]
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - returns CardType.DEFUSE

- **TC43: Player method throws exception** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlay_failed
  - **State of the system**:
    - canPlaySelected returns true
    - selectedCardTypes = [ATTACK]
    - getCurrentPlayer = player
    - player.removeCardFromHand with card1 throws IllegalStateException "error.cardNotInHand"
  - **Expected output**: throw IllegalStateException "error.cardNotInHand"

- **TC44: Valid play with one Attack** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [ATTACK]
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyAttack is called
    - returns CardType.ATTACK

- **TC45: Valid play with one Shuffle** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [SHUFFLE]
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyShuffle is called
    - returns CardType.SHUFFLE

- **TC46: Valid play with one Skip** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [SKIP]
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applySkip is called
    - returns CardType.SKIP

- **TC47: Valid play with one See The Future** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithoutApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [SEE_THE_FUTURE]
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applySeeTheFuture is called
    - returns CardType.SEE_THE_FUTURE

- **TC48: Valid play with one Catomic Bomb** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [CATOMIC_BOMB]
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyCatomicBomb is called
    - returns CardType.CATOMIC_BOMB

- **TC49: Valid play with one Super Skip** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [SUPER_SKIP]
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applySuperSkip is called
    - returns CardType.SUPER_SKIP

- **TC50: Valid play with one Godcat** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_godcatPlayed_returnsGodcat
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [GODCAT]
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyGodcat is called
    - returns CardType.GODCAT

- **TC51: Valid play with one Clone** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [CLONE]
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyClone is called
    - returns CardType.CLONE

- **TC52: Valid play with one Swap Top And Bottom** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [SWAP_TOP_AND_BOTTOM]
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applySwapTopAndBottom is called
    - returns CardType.SWAP_TOP_AND_BOTTOM

- **TC53: Valid play with one Draw From The Bottom** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [DRAW_FROM_THE_BOTTOM]
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyDrawFromTheBottom is called
    - returns CardType.DRAW_FROM_THE_BOTTOM

- **TC54: Valid play with one Targeted Attack** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithoutApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [TARGETED_ATTACK]
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyTargetedAttack is called
    - returns CardType.TARGETED_ATTACK

- **TC55: Valid play with one Winner Winner Catnip Dinner** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [WINNER_WINNER_CATNIP_DINNER]
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyWinnerWinnerCatnipDinner is called
    - returns CardType.WINNER_WINNER_CATNIP_DINNER

- **TC56: Valid play with one Ragebait** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [RAGEBAIT]
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyRagebait is called
    - returns CardType.RAGEBAIT

- **TC57: Valid play with one Recycle** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [RECYCLE]
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyRecycle is called
    - returns CardType.RECYCLE

- **TC58: Valid play with one Double Up** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [DOUBLE_UP]
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyDoubleUp is called
    - returns CardType.DOUBLE_UP

- **TC59: Valid play with one Mild Shuffle** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: canPlaySelected returns true, selectedCardTypes = [MILD_SHUFFLE]
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyMildDraw is called
    - returns CardType.MILD_DRAW

### Method under test: `getTopDiscardId()`
- **TC60: Empty discard pile** ( :white_check_mark: )
  - **Name of the test**: getTopDiscardId_emptyDiscardPile_failed
  - **State of the system**: discardPile.peekTop throws InvalidStateException "error.emptyDeck"
  - **Expected output**: throws IllegalStateException "error.emptyDeck"

- **TC61: Non-empty discard pile** ( :white_check_mark: )
  - **Name of the test**: getTopDiscardId_nonEmptyDiscardPile_returnTopCardId
  - **State of the system**: topDiscardPileId = "SKIP_1"
  - **Expected output**: returns drawPile.peekTop.getId

### Method under test: `getCanDrawFromDiscard()`
- **TC62: Empty discard pile** ( :white_check_mark: )
  - **Name of the test**: canDrawFromDiscard_none_returnFalse
  - **State of the system**: N/A
  - **Expected output**: returns false

### Method under test: `canEndTurn()`
- **TC63: Draw count is 0** ( :white_check_mark: )
  - **Name of the test**: canEndTurn_drawCountZero_returnTrue
  - **State of the system**: turnManager.getDrawCount = 0
  - **Expected output**: returns true

- **TC64: Draw count is 1** ( :white_check_mark: )
  - **Name of the test**: canEndTurn_positiveDrawCount_returnFalse
  - **State of the system**: turnManager.getDrawCount = 1
  - **Expected output**: returns false

- **TC65: Draw count is 2** ( :white_check_mark: )
  - **Name of the test**: canEndTurn_positiveDrawCount_returnFalse
  - **State of the system**: turnManager.getDrawCount = 2
  - **Expected output**: returns false

### Method under test: `isDrawPileEmpty()`
- **TC66: Empty draw pile** ( :white_check_mark: )
  - **Name of the test**: isDrawPileEmpty_emptyDrawPile_returnTrue
  - **State of the system**: drawPile.isEmpty = true
  - **Expected output**: returns true

- **TC67: Non-empty draw pile** ( :white_check_mark: )
  - **Name of the test**: isDrawPileEmpty_called_returnFalse
  - **State of the system**: drawPile.isEmpty = false
  - **Expected output**: returns false

### Method under test: `getCanDraw()`
- **TC68: Game is not ongoing, draw count is 0** ( :white_check_mark: )
  - **Name of the test**: getCanDraw_called_returnFalse
  - **State of the system**: isGameOngoing = false, turnManager.getDrawCount = 0
  - **Expected output**: returns false

- **TC69: Game is not ongoing, draw count is 1** ( :white_check_mark: )
  - **Name of the test**: getCanDraw_called_returnFalse
  - **State of the system**: isGameOngoing = false, turnManager.getDrawCount = 1
  - **Expected output**: returns false

- **TC70: Game is not ongoing, draw count is 2** ( :white_check_mark: )
  - **Name of the test**: getCanDraw_called_returnFalse
  - **State of the system**: isGameOngoing = false, turnManager.getDrawCount = 2
  - **Expected output**: returns false

- **TC71: Game is ongoing, draw count is 0** ( :white_check_mark: )
  - **Name of the test**: getCanDraw_called_returnFalse
  - **State of the system**: isGameOngoing = true, turnManager.getDrawCount = 0
  - **Expected output**: returns false

- **TC72: Game is ongoing, draw count is 1** ( :white_check_mark: )
  - **Name of the test**: getCanDraw_called_returnTrue
  - **State of the system**: isGameOngoing = true, turnManager.getDrawCount = 1
  - **Expected output**: returns true

- **TC73: Game is ongoing, draw count is 2** ( :white_check_mark: )
  - **Name of the test**: getCanDraw_called_returnTrue
  - **State of the system**: isGameOngoing = true, turnManager.getDrawCount = 1
  - **Expected output**: returns true

### Method under test: `changeCurrentPlayerIndex(int newPlayerIndex)`
- **TC74: This method is called successfully** ( :white_check_mark: )
  - **Name of the test**: changeCurrentPlayerIndex_called_callsTurnManager
  - **State of the system**: newPlayerIndex = 0
  - **Expected output**: calls turnManager.setCurrentPlayerIndex with newPlayerIndex

- **TC75: Turn manager method throws exception** ( :white_check_mark: )
  - **Name of the test**: changeCurrentPlayerIndex_called_failed
  - **State of the system**: turnManager.setCurrentPlayerIndex with newPlayerIndex throws IllegalArgumentException "error.invalidPlayerIndex"
  - **Expected output**: throw IllegalArgumentException "error.invalidPlayerIndex"

### Method under test: `setFaceUpToFalse()`
- **TC76: Is face up** ( :white_check_mark: )
  - **Name of the test**: setFaceUpToFalse_called_setToFalse
  - **State of the system**: isFaceUp = true
  - **Expected output**: isFaceUp = false

- **TC77: Is face down** ( :white_check_mark: )
  - **Name of the test**: setFaceUpToFalse_called_setToFalse
  - **State of the system**: isFaceUp = false
  - **Expected output**: isFaceUp = false

### Method under test: `drawFromPile()`
- **TC78: This method is called with non-exploding card** ( :white_check_mark: )
  - **Name of the test**: drawFromPile_nonExplodingCard_returnsDrawnCard
  - **State of the system**:
    - card = drawPile.peekTop
    - card.getType = ATTACK
    - player = getCurrentPlayer
  - **Expected output**:
    - drawPile.removeTop is called
    - player.addCardToHand is called with card
    - turnManager.decrementDrawCount is called
    - player.deselectHandCards is called
    - returns card

- **TC79: This method is called with exploding kitten** ( :white_check_mark: )
  - **Name of the test**: drawFromPile_explodingCard_returnsExplodingCard
  - **State of the system**:
    - card = drawPile.peekTop
    - card.getType = EXPLODING_KITTEN
    - player = getCurrentPlayer
  - **Expected output**:
    - turnManager.decrementDrawCount is called
    - player.deselectHandCards is called
    - returns card

- **TC80: Throw exception from drawPile** ( :white_check_mark: )
  - **Name of the test**: drawFromPile_drawPileException_failed
  - **State of the system**: drawPile.removeTop throws IllegalStateException "error.emptyDeck"
  - **Expected output**: throw IllegalStateException "error.emptyDeck"

- **TC81: Throw exception from turnManager** ( :white_check_mark: )
  - **Name of the test**: drawFromPile_turnManagerException_failed
  - **State of the system**: turnManager.decrementDrawCount throws IllegalStateException "error.negativeDrawCount"
  - **Expected output**: throw IllegalStateException "error.negativeDrawCount"

### Method under test: `toggleFaceUp()`
- **TC82: Is face up** ( :white_check_mark: )
  - **Name of the test**: toggleFaceUp_called_setToFalse
  - **State of the system**: isFaceUp = true
  - **Expected output**: isFaceUp = false

- **TC83: Is face down** ( :white_check_mark: )
  - **Name of the test**: toggleFaceUp_called_togglesFaceUp
  - **State of the system**: isFaceUp = false
  - **Expected output**: isFaceUp = true

### Method under test: `toggleSelectedCurrentPlayerCardAt(int handCardIndex)`
- **TC84: Hand card index at 0** ( :white_check_mark: )
  - **Name of the test**: toggleSelectedCurrentPlayerCardAt_called_calledPlayerToggle
  - **State of the system**:
    - player = getCurrentPlayer
    - handCardIndex = 0
  - **Expected output**: player.toggleSelectedPlayerCardAt is called with handCardIndex

- **TC85: Player method throws exception** ( :white_check_mark: )
  - **Name of the test**: toggleSelectedCurrentPlayerCardAt_indexZero_failed
  - **State of the system**: player.toggleSelectedPlayerCardAt throws InvalidArgumentException "error.handCardIndexOutOfBounds"
  - **Expected output**: throws InvalidArgumentException "error.handCardIndexOutOfBounds"

### Method under test: `advanceTurn()`
- **TC86: Can end turn** ( :white_check_mark: )
  - **Name of the test**: advanceTurn_canEndTurn_advanceTurnAndDeselectCards
  - **State of the system**:
    - canEndTurn = true
    - player = getCurrentPlayer
  - **Expected output**:
    - player.deselectHandCards is called
    - turnManager.incrementTurn is called with players
    - turnManager.incrementDrawCount is called

- **TC87: Cannot end turn** ( :white_check_mark: )
  - **Name of the test**: advanceTurn_cannotEndTurn_failed
  - **State of the system**: canEndTurn = false
  - **Expected output**: throws InvalidStateException "error.cannotEndTurn"

### Method under test: `getDrawPileSize()`
- **TC88: This method is called** ( :white_check_mark: )
  - **Name of the test**: getDrawPileSize_called_returnDrawPileMethodCall
  - **State of the system**: drawPile.size = N, N = [0, 1, 2]
  - **Expected output**: returns drawPile.size

### Method under test: `playExplode()`
- **TC89: Empty draw pile** ( :white_check_mark: )
  - **Name of the test**: playExplode_emptyDrawPile_failed
  - **State of the system**: drawPile.removeTop throws IllegalStateException "error.emptyDeck"
  - **Expected output**: throws IllegalStateException "error.emptyDeck"

- **TC90: This method is called successfully** ( :white_check_mark: )
  - **Name of the test**: playExplode_called_success
  - **State of the system**: N/A
  - **Expected output**:
    - drawPile.removeTop is called
    - getCurrentPlayer.deselectHandCards is called
    - getCurrentPlayer.eliminate is called
    - turnManager.incrementTurn is called with players
    - turnManager.incrementDrawCount is called

### Method under test: `isDefusable()`
- **TC91: Empty hand** ( :white_check_mark: )
  - **Name of the test**: isDefusable_noDefuser_returnFalse
  - **State of the system**:
    - currentPlayerHandCardTypes = []
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return false

- **TC92: Hand with one card type, no Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_noDefuser_returnFalse
  - **State of the system**:
    - currentPlayerHandCardTypes = [ATTACK]
    - topDiscardType = ATTACK
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return false

- **TC93: Hand with two different card types, no Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_noDefuser_returnFalse
  - **State of the system**:
    - currentPlayerHandCardTypes = [ATTACK, SKIP]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return false

- **TC94: Hand with two same card types, no Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_noDefuser_returnFalse
  - **State of the system**:
    - currentPlayerHandCardTypes = [SKIP, SKIP]
    - topDiscardType = ATTACK
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return false

- **TC95: Hand with Clone, top discard type is not Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_noDefuser_returnFalse
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, SKIP]
    - topDiscardType = ATTACK
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return false

- **TC96: Hand with two Clones, top discard type is not Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_noDefuser_returnFalse
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, CLONE]
    - topDiscardType = ATTACK
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return false

- **TC97: Hand with one card type, has Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [DEFUSE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC98: Hand with two different card types, has Defuse at end** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [SKIP, DEFUSE]
    - topDiscardType = SKIP
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC99: Hand with two different card types, has Defuse at front** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [DEFUSE, SKIP]
    - topDiscardType = ATTACK
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC100: Hand with two same card types, has Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [DEFUSE, DEFUSE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC101: Hand with one card type, has Godcat** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [GODCAT]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC102: Hand with two different card types, has Godcat at end** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [SKIP, GODCAT]
    - topDiscardType = SKIP
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC103: Hand with two different card types, has Godcat at front** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [GODCAT, SKIP]
    - topDiscardType = ATTACK
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC104: Hand with two same card types, has Godcat** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [GODCAT, GODCAT]
    - topDiscardType = DEFUSE
  - **Expected output**: return true

- **TC105: Hand with Defuse and Godcat** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [DEFUSE, GODCAT]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC106: Hand with Godcat and Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [GODCAT, DEFUSE]
    - topDiscardType = ATTACK
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC107: Hand with one card type, has Clone** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC108: Hand with two different card types, has Clone at end** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [SKIP, CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC109: Hand with two different card types, has Clone at front** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, SKIP]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC110: Hand with two same card types, has Clone** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**: return true

- **TC111: Hand with Defuse and Clone** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [DEFUSE, CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC112: Hand with Clone and Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, DEFUSE]
    - topDiscardType = ATTACK
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC113: Hand with Clone and Godcat, use Godcat** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, GODCAT]
    - topDiscardType = ATTACK
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC114: Hand with Godcat and Clone, use Clone** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [GODCAT, CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC115: Hand with Godcat, Clone, Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [GODCAT, CLONE, DEFUSE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

### Method under test: `playDefuse(int drawPileIndex)`
- **TC116: Empty hand** ( :white_check_mark: )
  - **Name of the test**: playDefuse_noDefuser_failed
  - **State of the system**: getCurrentPlayer.getHand = []
  - **Expected output**: throws IllegalStateException "error.currentPlayerNoDefuser"

- **TC117: Hand with one card type, no Defuse** ( :white_check_mark: )
  - **Name of the test**: playDefuse_noDefuser_failed
  - **State of the system**: currentPlayerHandCardTypes = [ATTACK]
  - **Expected output**: return false

- **TC118: Hand with two different card types, no Defuse** ( :white_check_mark: )
  - **Name of the test**: playDefuse_noDefuser_failed
  - **State of the system**: currentPlayerHandCardTypes = [ATTACK, SKIP]
  - **Expected output**: return false

- **TC119: Hand with two same card types, no Defuse** ( :white_check_mark: )
  - **Name of the test**: playDefuse_noDefuser_failed
  - **State of the system**: currentPlayerHandCardTypes = [SKIP, SKIP]
  - **Expected output**: return false

- **TC120: Hand with one card type, has Defuse** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [DEFUSE]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC121: Hand with two different card types, has Defuse at end** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [SKIP, DEFUSE]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 1 is called
    - discardPile.addCard with card at index 1 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC122: Hand with two different card types, has Defuse at front** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [DEFUSE, SKIP]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC123: Hand with two same card types, has Defuse** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [DEFUSE, DEFUSE]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC124: Hand with one card type, has Godcat** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [GODCAT]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC125: Hand with two different card types, has Godcat at end** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [SKIP, GODCAT]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 1 is called
    - discardPile.addCard with card at index 1 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC126: Hand with two different card types, has Godcat at front** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [GODCAT, SKIP]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC127: Hand with two same card types, has Godcat** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [GODCAT, GODCAT]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC128: Hand with Defuse and Godcat** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [DEFUSE, GODCAT]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC129: Hand with Godcat and Defuse** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [GODCAT, DEFUSE]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 1 is called
    - discardPile.addCard with card at index 1 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC130: Hand with one card type, has Clone** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC131: Hand with two different card types, has Clone at end** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**:
    - currentPlayerHandCardTypes = [SKIP, CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 1 is called
    - discardPile.addCard with card at index 1 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC132: Hand with two different card types, has Clone at front** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, SKIP]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC133: Hand with two same card types, has Clone** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC134: Hand with Defuse and Clone** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**:
    - currentPlayerHandCardTypes = [DEFUSE, CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC135: Hand with Clone and Defuse** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, DEFUSE]
    - topDiscardType = ATTACK
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand(card2) is called
    - discardPile.addCard with card at index 1 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC136: Hand with Clone and Godcat, use Godcat** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, GODCAT]
    - topDiscardType = ATTACK
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 1 is called
    - discardPile.addCard with card at index 1 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC137: Hand with Godcat and Clone, use Clone** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**:
    - currentPlayerHandCardTypes = [GODCAT, CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 1 is called
    - discardPile.addCard with card at index 1 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC138: Hand with Godcat, Clone, Defuse** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**:
    - currentPlayerHandCardTypes = [GODCAT, CLONE, DEFUSE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 2 is called
    - discardPile.addCard with card at index 2 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC139: Invalid draw pile index** ( :white_check_mark: )
  - **Name of the test**: playDefuse_invalidDrawPileIndex_failed
  - **State of the system**:
    - currentPlayerHandCardTypes = [DEFUSE, DEFUSE]
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex throws IllegalArgumentException "error.invalidDeckIndex"
  - **Expected output**: throws IllegalArgumentException "error.invalidDeckIndex"

### Method under test: `applySkip()`
- **TC140: Skip on a standard single turn (drawCount = 1)** ( :white_check_mark: )
  - **Name of the test:** applySkip_drawCountOne_TurnAdvances
  - **State of the system:** game is ongoing, drawCount = 1 (normal, unattacked turn)
  - **Expected output:** drawCount resets to 1 (from incrementDrawCount), currentPlayerIndex becomes 1, turn has advanced automatically

- **TC141: Skip while under attack (drawCount = 2)** ( :white_check_mark: )
  - **Name of the test:** applySkip_drawCountTwo_TurnNotAdvanced
  - **State of the system:** game is ongoing, drawCount = 2, currentPlayerIndex = 0
  - **Expected output:** drawCount becomes 1, currentPlayerIndex remains 0, turn is NOT advanced, player must still draw a card

- **TC142: Skip under stacked attacks (drawCount = 3+)** ( :white_check_mark: )
  - **Name of the test:** applySkip_drawCountThree_TurnNotAdvanced
  - **State of the system:** game is ongoing, drawCount = 3, currentPlayerIndex = 0
  - **Expected output:** drawCount becomes 2, currentPlayerIndex remains 0, turn is NOT advanced

- **TC143: Skip completes turn for the last player in order (wraparound)** ( :white_check_mark: )
  - **Name of the test:** applySkip_lastPlayer_turnWraps
  - **State of the system:** game is ongoing, drawCount = 1, currentPlayerIndex = numPlayers - 1
  - **Expected output:** currentPlayerIndex wraps to 0, drawCount resets to 1 (from incremetDrawCount)

- **TC144: Skip with minimum player count (2 players)** ( :white_check_mark: )
  - **Name of the test:** applySkip_twoPlayers_turnAdvances
  - **State of the system:**  game is ongoing, 2 players, drawCount = 1, currentPlayerIndex = 0
  - **Expected output:** currentPlayerIndex becomes 1, drawCount resets to 1, the single other player is now active

- **TC145: Skip on a standard single turn with maximum players (4 players)** ( :white_check_mark: )
  - **Name of the test:** applySkip_fourPlayers_turnAdvances
  - **State of the system:**  game is ongoing, drawCount = 1, 4 players, currentPlayerIndex = 0
  - **Expected output:** drawCount resets to 1 (from incrementDrawCount), currentPlayerIndex = 1, turn has advanced automatically

### Method under test: `getSeeTheFutureCardIds()`
- **TC146: Empty list** ( :white_check_mark: )
  - **Name of the test:** getSeeTheFutureCardIds_called_returnTopDrawPileCards
  - **State of the system:** topCards = []
  - **Expected output:** return []

- **TC147: One card** ( :white_check_mark: )
  - **Name of the test:** getSeeTheFutureCardIds_called_returnTopDrawPileCards
  - **State of the system:** topCards = [SKIP_1]
  - **Expected output:** return ["SKIP_1"]

- **TC148: Two different card ids** ( :white_check_mark: )
  - **Name of the test:** getSeeTheFutureCardIds_called_returnTopDrawPileCards
  - **State of the system:** topCards = [SKIP_1, SKIP_2]
  - **Expected output:** return ["SKIP_1", "SKIP_2"]

- **TC149: Two different card types** ( :white_check_mark: )
  - **Name of the test:** getSeeTheFutureCardIds_called_returnTopDrawPileCards
  - **State of the system:** topCards = [SKIP_1, ATTACK_1]
  - **Expected output:** return ["SKIP_1", "ATTACK_1"]

- **TC150: Two same cards** ( :white_check_mark: )
  - **Name of the test:** getSeeTheFutureCardIds_called_returnTopDrawPileCards
  - **State of the system:** topCards = [SKIP_1, SKIP_1]
  - **Expected output:** return ["SKIP_1", "SKIP_1"]

### Method under test: `applyCatomicBomb()`
- **TC90: Draw pile is empty** ( :white_check_mark: )
  - **Name of the test**: applyCatomicBomb_emptyDeck_remainsEmpty
  - **State of the system**: 
    - draw pile has no cards
    - canEndTurn returns true
  - **Expected output**:
    - draw pile remains empty
    - turnManager.decrementDrawCount is called
    - advanceTurn is called

- **TC91: No Exploding Kittens in draw pile** ( :white_check_mark: )
  - **Name of the test**: applyCatomicBomb_noExplodingKittens_deckUnchanged
  - **State of the system**: 
    - draw pile contains ['SKIP_1', 'ATTACK_1', 'SHUFFLE_1'], none are CardType 'EXPLODING_KITTEN'
    - canEndTurn returns false
  - **Expected output**:
    - addCardToBottom called for SKIP_1, ATTACK_1, SHUFFLE_1 in order
    - draw pile order is unchanged and remains ['SKIP_1', 'ATTACK_1', 'SHUFFLE_1']
    - turnManager.decrementDrawCount is called

- **TC92: One Exploding Kitten at top of draw pile** ( :white_check_mark: )
  - **Name of the test**: applyCatomicBomb_oneExplodingKittenAlreadyOnTop_deckUnchanged
  - **State of the system**: 
    - top card of draw pile has one CardType 'EXPLODING_KITTEN' with order ['EXPLODING_KITTEN_1' , 'ATTACK_1', 'SHUFFLE_1']
    - canEndTurn returns true
  - **Expected output**:
    - addCardToBottom called for ATTACK_1, SHUFFLE_1
    - addCardToTop called for EXPLODINGKITTEN_1
    - draw pile order remains ['EXPLODINGKITTEN_1', 'ATTACK_1', 'SHUFFLE_1']
    - turnManager.decrementDrawCount is called
    - advanceTurn is called

- **TC93: One Exploding Kitten in the middle of draw pile** ( :white_check_mark: )
  - **Name of the test**: applyCatomicBomb_oneExplodingKittenInMiddle_movedToTop
  - **State of the system**: 
    - draw pile contains ['SKIP_1', 'ATTACK_1', 'EXPLODING_KITTEN_1', 'SHUFFLE_1']
    - canEndTurn returns false
  - **Expected output**:
    - addCardToBottom called for SKIP_1, ATTACK_1, SHUFFLE_1
    - addCardToTop called for EXPLODINGKITTEN_1
    - draw pile order is ['EXPLODINGKITTEN_1', 'SKIP_1', 'ATTACK_1', 'SHUFFLE_1']
    - turnManager.decrementDrawCount is called

- **TC94: Multiple Exploding Kittens scattered throughput draw pile** ( :white_check_mark: )
  - **Name of the test**: applyCatomicBomb_multipleExplodingKittens_allMovedToTop
  - **State of the system**: 
    - draw pile contains ['SKIP_1', 'EXPLODING KITTEN_1', 'ATTACK_1', 'EXPLODING KITTEN_2', 'SHUFFLE_1']
    - canEndTurn returns true
  - **Expected output**:
    - addCardToBottom called for SKIP_1, ATTACK_1, SHUFFLE_1
    - addCardToTop called for EXPLODINGKITTEN_1, EXPLODINGKITTEN_2
    - draw pile order is ['EXPLODINGKITTEN_1', 'EXPLODINGKITTEN_2', 'SKIP_1', 'ATTACK_1', 'SHUFFLE_1']
    - turnManager.decrementDrawCount is called
    - advanceTurn is called

- **TC95: All cards in draw pile are Exploding Kittens** ( :white_check_mark: )
  - **Name of the test**: applyCatomicBomb_allExplodingKittens_deckOrderUnchanged
  - **State of the system**: 
    - draw pile contains ['EXPLODING KITTEN_1', 'EXPLODING KITTEN_2', 'EXPLODING KITTEN_3']
    - canEndTurn returns false
  - **Expected output**:
    - addCardToTop called for EXPLODINGKITTEN_1, EXPLODINGKITTEN_2, EXPLODINGKITTEN_3
    - draw pile order unchanged
    - turnManager.decrementDrawCount is called

### Method under test: `applySuperSkip()`
- **TC157: Super Skip on a standard single turn (drawCount = 1)** ( :white_check_mark: )
    - **Name of the test:** applySuperSkip_drawCountOne_TurnAdvances
    - **State of the system:** game is ongoing, drawCount = 1, currentPlayerIndex = 0
    - **Expected output:** decrementDrawCount() called once, turn advances to currentPlayerIndex = 1

- **TC158: Super Skip while under attack (drawCount = 2)** ( :white_check_mark: )
    - **Name of the test:** applySuperSkip_drawCountTwo_TurnAdvances
    - **State of the system:** game is ongoing, drawCount = 2, currentPlayerIndex = 0
    - **Expected output:** decrementDrawCount() called twice, turn advances to currentPlayerIndex = 1

- **TC159: Super Skip under stacked attacks (drawCount = 3+)** ( :white_check_mark: )
    - **Name of the test:** applySuperSkip_drawCountThree_TurnAdvances
    - **State of the system:** game is ongoing, drawCount = 3, currentPlayerIndex = 0
    - **Expected output:** decrementDrawCount() called three times, turn advances to currentPlayerIndex = 1

- **TC160: Super Skip completes turn for the last player in order (wraparound)** ( :white_check_mark: )
    - **Name of the test:** applySuperSkip_lastPlayer_turnWraps
    - **State of the system:** game is ongoing, drawCount = 1, currentPlayerIndex = numPlayers - 1
    - **Expected output:** decrementDrawCount() called once, currentPlayerIndex wraps to 0

- **TC161: Super Skip with minimum player count (2 players)** ( :white_check_mark: )
    - **Name of the test:** applySuperSkip_twoPlayers_turnAdvances
    - **State of the system:** game is ongoing, 2 players, drawCount = 1, currentPlayerIndex = 0
    - **Expected output:** decrementDrawCount() called once, currentPlayerIndex wraps to 0

- **TC162: Super Skip with maximum player count (4 players)** ( :white_check_mark: )
    - **Name of the test:** applySuperSkip_fourPlayers_turnAdvances
    - **State of the system:**  game is ongoing, 4 players, drawCount = 1, currentPlayerIndex = 0
    - **Expected output:** decrementDrawCount() called once, turn advances to currentPlayerIndex = 1

### Method under test: `applyGodcat(CardType cardType)`
- **TC163: Invalid card type Godcat** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_invalidCardType_throwsException
  - **State of the system**: CardType.GODCAT passed as cardType
  - **Expected output**: throws exception "error.cannotPlaySelectedCards"

- **TC164: Invalid card type Exploding Kitten** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_invalidCardType_throwsException
  - **State of the system**: CardType.EXPLODING_KITTEN passed as cardType
  - **Expected output**: throws exception "error.cannotPlaySelectedCards"

- **TC165: Invalid card type Defuse** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_invalidCardType_throwsException
  - **State of the system**: CardType.DEFUSE passed as cardType
  - **Expected output**: throws exception "error.cannotPlaySelectedCards"

- **TC166: Valid card type Attack** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.ATTACK passed as cardType
  - **Expected output**: applyAttack() is called

- **TC167: Valid card type Shuffle** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.SHUFFLE passed as cardType
  - **Expected output**: applyShuffle() is called

- **TC168: Valid card type Skip** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.SKIP passed as cardType
  - **Expected output**: applySkip() is called

- **TC169: Valid card type See the Future** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validPlayWithoutApplyMethod_noApplyCalled
  - **State of the system**: CardType.SEE_THE_FUTURE passed as cardType
  - **Expected output**: N/A

- **TC170: Valid card type Catomic Bomb** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.CATOMIC_BOMB passed as cardType
  - **Expected output**: applyCatomicBomb() is called

- **TC171: Valid card type Super Skip** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.SUPER_SKIP passed as cardType
  - **Expected output**: applySuperSkip() is called

- **TC172: Valid card type Clone** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.CLONE passed as cardType
  - **Expected output**: applyClone() is called

- **TC173: Valid card type Swap Top and Bottom** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.SWAP_TOP_AND_BOTTOM passed as cardType
  - **Expected output**: applySwapTopAndBottom() is called

- **TC174: Valid card type Draw From the Bottom** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.DRAW_FROM_THE_BOTTOM passed as cardType
  - **Expected output**: applyDrawFromTheBottom() is called

- **TC175: Valid card type Targeted Attack** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validPlayWithoutApplyMethod_noApplyCalled
  - **State of the system**: CardType.TARGETED_ATTACK passed as cardType
  - **Expected output**: N/A

- **TC176: Valid card type Winner Winner Catnip Dinner** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.WINNER_WINNER_CATNIP_DINNER passed as cardType
  - **Expected output**: applyWinnerWinnerCatnipDinner() is called

- **TC177: Valid card type Ragebait** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.RAGEBAIT passed as cardType
  - **Expected output**: applyRagebait() is called

- **TC178: Valid card type Recycle** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.RECYCLE passed as cardType
  - **Expected output**: applyRecycle() is called

- **TC179: Valid card type Double Up** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.DOUBLE_UP passed as cardType
  - **Expected output**: applyDoubleUp() is called

- **TC180: Valid card type Mild Shuffle** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.MILD_SHUFFLE passed as cardType
  - **Expected output**: applyMildShuffle() is called

### Method under test: `applySwapTopAndBottom()`
- **TC181: Draw pile has zero cards** ( :white_check_mark: )
  - **Name of the test**: applySwapTopAndBottom_emptyDeck_remainsEmpty
  - **State of the system**: draw pile is empty
  - **Expected output**: draw pile remains empty

- **TC182: Draw pile has exactly one card** ( :white_check_mark: )
  - **Name of the test**: applySwapTopAndBottom_oneCard_deckUnchanged
  - **State of the system**: draw pile has two cards ['CARD 1']
  - **Expected output**: draw pile has two cards; card order is ['CARD 1']

- **TC183: Draw pile has more than one card** ( :white_check_mark: )
  - **Name of the test**: applySwapTopAndBottom_moreThanOneCard_swapped
  - **State of the system**: draw pile has four cards ['CARD 1', 'CARD 2', 'CARD 3', 'CARD 4']
  - **Expected output**:
    - drawPile.addCardToTop(CARD_4) called
    - drawPile.addCardToBottom(CARD_1) called
    - draw pile has four cards and card order is ['CARD 4', 'CARD 2', 'CARD 3', 'CARD']

- **TC184: Top and bottom cards are the same type** ( :white_check_mark: )
  - **Name of the test**: applySwapTopAndBottom_sameType_swapped
  - **State of the system**: draw pile has four cards ['EXPLODING KITTEN 1', 'CARD 2', 'CARD 3', 'EXPLODING KITTEN 2']
  - **Expected output**:
    - drawPile.addCardToTop(EXPLODINGKITTEN_2) called
    - drawPile.addCardToBottom(EXPLODINGKITTEN_1) called;
    - draw pile has four cards; card order is ['EXPLODING KITTEN 2', 'CARD 2', 'CARD 3', 'EXPLODING KITTEN 1']

# Method under test: `applyTargetedAttack(int targetPlayerIndex)`
- **TC185: Targeted Attack with minimum players (2), first player targets next player** ( :white_check_mark: )
    - **Name of the test**: applyTargetedAttack_validTargets_successfullyCalled
    - **State of the system**: 2 players, currentPlayerIndex = 0, targetPlayerIndex = 1
    - **Expected output**:
        - deselectHandCards() called on player 0
        - incrementTurn() called 1 time
        - addAttackDrawCount() called

- **TC186: Targeted Attack with minimum players (2), last player targets first player (wrap)** ( :white_check_mark: )
    - **Name of the test**: applyTargetedAttack_validTargets_successfullyCalled
    - **State of the system**: 2 players, currentPlayerIndex = 1, targetPlayerIndex = 0
    - **Expected output**:
        - deselectHandCards() called on player 1
        - incrementTurn() called 1 time
        - addAttackDrawCount() called

- **TC187: Targeted Attack with maximum players (4), first player targets last player** ( :white_check_mark: )
    - **Name of the test**: applyTargetedAttack_validTargets_successfullyCalled
    - **State of the system**: 4 players, currentPlayerIndex = 0, targetPlayerIndex = 3
    - **Expected output**:
        - deselectHandCards() called on player 0
        - incrementTurn() called 3 times
        - addAttackDrawCount() called

- **TC188: Targeted Attack with maximum players (4), last player targets first player (wrap)** ( :white_check_mark: )
    - **Name of the test**: applyTargetedAttack_validTargets_successfullyCalled
    - **State of the system**: 4 players, currentPlayerIndex = 3, targetPlayerIndex = 0
    - **Expected output**:
        - deselectHandCards() called on player 3
        - incrementTurn() called 1 time
        - addAttackDrawCount() called

### Method under test: `addAttackDrawCount()`
- **TC189: Draw count is below the attack threshold** ( :white_check_mark: )
    - **Name of the test**: addAttackDrawCount_drawCountZero_SetTwo
    - **State of the system**:
        - turnManager.drawCount = 1
    - **Expected output**:
        - turnManager.drawCount = 2

- **TC190: Draw count is at or above the attack threshold** ( :white_check_mark: )
    - **Name of the test**: addAttackDrawCount_drawCountTwo_addsTwo
    - **State of the system**:
        - turnManager.drawCount = 2
    - **Expected output**:
        - turnManager.drawCount = 4