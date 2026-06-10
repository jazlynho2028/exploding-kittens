# BVA Analysis: Game Class

### Method under test: `Game(List<Player> players, Deck drawPile, Deck discardPile)`
- **TC1: Constructor called** ( :white_check_mark: )
  - **Name of the test**: constructor_anyInput_initializeFieldsFalse
  - **State of the system**: N/A
  - **Expected output**: 
    - isGameOngoing = false
    - isFaceUp = false
    - canPlay = false

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
    - changeCurrentPlayerIndex is called with STARTING_PLAYER_INDEX
    - isGameOngoing = true
    - canPlay = true

- **TC9: Start game with 4 players** ( :white_check_mark: )
  - **Name of the test**: startGame_gameIsNotOngoing_startFirstRound
  - **State of the system**: players.size = 4, isGameOngoing = false
  - **Expected output**:
    - drawPile.addCardToTop called three times (N-1=3) with EXPLODINGKITTEN_1 to 3
    - drawPile.shuffle called
    - changeCurrentPlayerIndex is called with STARTING_PLAYER_INDEX
    - isGameOngoing = true
    - canPlay = true

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
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = []
  - **Expected output**: returns false

- **TC18: One Defuse selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [DEFUSE]
  - **Expected output**: returns false

- **TC19: One Exploding Kitten selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [EXPLODING_KITTEN]
  - **Expected output**: returns false

- **TC20: One Cat Card 1 selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [CAT_CARD_1]
  - **Expected output**: returns false

- **TC21: One Cat Card 2 selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [CAT_CARD_2]
  - **Expected output**: returns false

- **TC22: One Cat Card 3 selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [CAT_CARD_3]
  - **Expected output**: returns false

- **TC23: One Cat Card 4 selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [CAT_CARD_4]
  - **Expected output**: returns false

- **TC24: One Feral Cat selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_invalidCards_returnFalse
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [FERAL_CAT]
  - **Expected output**: returns false

- **TC25: Cannot play** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_cannotPlay_returnFalse
  - **State of the system**: canPlay = false
  - **Expected output**: returns false

- **TC27: One Attack selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [ATTACK]
  - **Expected output**: returns true

- **TC28: One Shuffle selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [SHUFFLE]
  - **Expected output**: returns true

- **TC29: One Skip selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [SKIP]
  - **Expected output**: returns true

- **TC30: One See The Future selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [SEE_THE_FUTURE]
  - **Expected output**: returns true

- **TC31: One Catomic Bomb selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [CATOMIC_BOMB]
  - **Expected output**: returns true

- **TC32: One Super Skip selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [SUPER_SKIP]
  - **Expected output**: returns true

- **TC33: One Godcat selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [GODCAT]
  - **Expected output**: returns true

- **TC34: One Clone selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [CLONE]
  - **Expected output**: returns true

- **TC35: One Swap Top And Bottom selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [SWAP_TOP_AND_BOTTOM]
  - **Expected output**: returns true

- **TC36: One Draw From The Bottom selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [DRAW_FROM_THE_BOTTOM]
  - **Expected output**: returns true

- **TC37: One Targeted Attack selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [TARGETED_ATTACK]
  - **Expected output**: returns true

- **TC38: One Winner Winner Catnip Dinner selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [WINNER_WINNER_CATNIP_DINNER]
  - **Expected output**: returns true

- **TC39: One Ragebait selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [RAGEBAIT]
  - **Expected output**: returns true

- **TC40: One Recycle selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [RECYCLE]
  - **Expected output**: returns true

- **TC41: One Double Up selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [DOUBLE_UP]
  - **Expected output**: returns true

- **TC42: One Mild Shuffle selected** ( :white_check_mark: )
  - **Name of the test**: canPlaySelected_validCards_returnTrue
  - **State of the system**: 
    - canPlay = true
    - selectedCardTypes = [MILD_SHUFFLE]
  - **Expected output**: returns true

### Method under test: `playSelectedCards()`
- **TC43: Selected cards cannot be played** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_invalidPlay_failed
  - **State of the system**: canPlaySelected returns false
  - **Expected output**: throws IllegalStateException "error.cannotPlaySelectedCards"

- **TC44: Valid play with unknown card type** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithUnknownCardType_failed
  - **State of the system**:
    - canPlaySelected returns true
    - selectedCardTypes = [DEFUSE]
    - getCurrentPlayer = player
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - returns CardType.DEFUSE

- **TC45: Player method throws exception** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlay_failed
  - **State of the system**:
    - canPlaySelected returns true
    - selectedCardTypes = [ATTACK]
    - getCurrentPlayer = player
    - player.removeCardFromHand with card1 throws IllegalStateException "error.cardNotInHand"
  - **Expected output**: throw IllegalStateException "error.cardNotInHand"

- **TC46: Valid play with one Attack** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: 
    - canPlaySelected returns true
    - selectedCardTypes = [ATTACK]
    - getCurrentPlayer = player
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyAttack is called
    - returns CardType.ATTACK

- **TC47: Valid play with one Shuffle** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: 
    - canPlaySelected returns true
    - selectedCardTypes = [SHUFFLE]
    - getCurrentPlayer = player
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyShuffle is called
    - returns CardType.SHUFFLE

- **TC48: Valid play with one Skip** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: 
    - canPlaySelected returns true
    - selectedCardTypes = [SKIP]
    - getCurrentPlayer = player
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applySkip is called
    - returns CardType.SKIP

- **TC49: Valid play with one See The Future** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithoutApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: 
    - canPlaySelected returns true
    - selectedCardTypes = [SEE_THE_FUTURE]
    - getCurrentPlayer = player
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applySeeTheFuture is called
    - returns CardType.SEE_THE_FUTURE

- **TC50: Valid play with one Catomic Bomb** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: 
    - canPlaySelected returns true
    - selectedCardTypes = [CATOMIC_BOMB]
    - getCurrentPlayer = player
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyCatomicBomb is called
    - returns CardType.CATOMIC_BOMB

- **TC51: Valid play with one Super Skip** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: 
    - canPlaySelected returns true
    - selectedCardTypes = [SUPER_SKIP]
    - getCurrentPlayer = player
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applySuperSkip is called
    - returns CardType.SUPER_SKIP

- **TC52: Valid play with one Godcat** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_godcatPlayed_returnsGodcat
  - **State of the system**: 
    - canPlaySelected returns true
    - selectedCardTypes = [GODCAT]
    - getCurrentPlayer = player
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyGodcat is called
    - returns CardType.GODCAT

- **TC53: Valid play with one Clone** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: 
    - canPlaySelected returns trueselectedCardTypes = [CLONE]
    - getCurrentPlayer = player
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyClone is called
    - returns CardType.CLONE

- **TC54: Valid play with one Swap Top And Bottom** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: 
    - canPlaySelected returns true
    - selectedCardTypes = [SWAP_TOP_AND_BOTTOM]
    - getCurrentPlayer = player
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applySwapTopAndBottom is called
    - returns CardType.SWAP_TOP_AND_BOTTOM

- **TC55: Valid play with one Draw From The Bottom** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: 
    - canPlaySelected returns true
    - selectedCardTypes = [DRAW_FROM_THE_BOTTOM]
    - getCurrentPlayer = player
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyDrawFromTheBottom is called
    - returns CardType.DRAW_FROM_THE_BOTTOM

- **TC56: Valid play with one Targeted Attack** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithoutApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: 
    - canPlaySelected returns true
    - selectedCardTypes = [TARGETED_ATTACK]
    - getCurrentPlayer = player
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyTargetedAttack is called
    - returns CardType.TARGETED_ATTACK

- **TC57: Valid play with one Winner Winner Catnip Dinner** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: 
    - canPlaySelected returns true
    - selectedCardTypes = [WINNER_WINNER_CATNIP_DINNER]
    - getCurrentPlayer = player
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyWinnerWinnerCatnipDinner is called
    - returns CardType.WINNER_WINNER_CATNIP_DINNER

- **TC58: Valid play with one Ragebait** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: 
    - canPlaySelected returns true
    - selectedCardTypes = [RAGEBAIT]
    - getCurrentPlayer = player
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyRagebait is called
    - returns CardType.RAGEBAIT

- **TC59: Valid play with one Recycle** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: 
    - canPlaySelected returns true
    - selectedCardTypes = [RECYCLE]
    - getCurrentPlayer = player
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyRecycle is called
    - returns CardType.RECYCLE

- **TC60: Valid play with one Double Up** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: 
    - canPlaySelected returns true
    - selectedCardTypes = [DOUBLE_UP]
    - getCurrentPlayer = player
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyDoubleUp is called
    - returns CardType.DOUBLE_UP

- **TC61: Valid play with one Mild Shuffle** ( :white_check_mark: )
  - **Name of the test**: playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard
  - **State of the system**: 
    - canPlaySelected returns true
    - selectedCardTypes = [MILD_SHUFFLE]
    - getCurrentPlayer = player
  - **Expected output**:
    - card1.toggleSelected is called
    - player.removeCardFromHand with card1 is called
    - discardPile.addCardToTop(card1) is called
    - applyMildDraw is called
    - returns CardType.MILD_DRAW

### Method under test: `getTopDiscardId()`
- **TC62: Empty discard pile** ( :white_check_mark: )
  - **Name of the test**: getTopDiscardId_emptyDiscardPile_returnEmptyString
  - **State of the system**: discardPile.isEmpty = true
  - **Expected output**: return "global.empty"

- **TC63: Non-empty discard pile** ( :white_check_mark: )
  - **Name of the test**: getTopDiscardId_nonEmptyDiscardPile_returnTopCardId
  - **State of the system**: 
    - discardPile.isEmpty = false
    - topDiscardPileId = "SKIP_1"
  - **Expected output**: returns drawPile.peekTop.getId

### Method under test: `canEndTurn()`
- **TC64: Draw count is 0** ( :white_check_mark: )
  - **Name of the test**: canEndTurn_drawCountZero_returnTrue
  - **State of the system**: turnManager.getDrawCount = 0
  - **Expected output**: returns true

- **TC65: Draw count is 1** ( :white_check_mark: )
  - **Name of the test**: canEndTurn_positiveDrawCount_returnFalse
  - **State of the system**: turnManager.getDrawCount = 1
  - **Expected output**: returns false

- **TC66: Draw count is 2** ( :white_check_mark: )
  - **Name of the test**: canEndTurn_positiveDrawCount_returnFalse
  - **State of the system**: turnManager.getDrawCount = 2
  - **Expected output**: returns false

### Method under test: `isDrawPileEmpty()`
- **TC67: Empty draw pile** ( :white_check_mark: )
  - **Name of the test**: isDrawPileEmpty_emptyDrawPile_returnTrue
  - **State of the system**: drawPile.isEmpty = true
  - **Expected output**: returns true

- **TC68: Non-empty draw pile** ( :white_check_mark: )
  - **Name of the test**: isDrawPileEmpty_called_returnFalse
  - **State of the system**: drawPile.isEmpty = false
  - **Expected output**: returns false

### Method under test: `getCanDraw()`
- **TC69: Game is not ongoing, draw count is 0** ( :white_check_mark: )
  - **Name of the test**: getCanDraw_called_returnFalse
  - **State of the system**: isGameOngoing = false, turnManager.getDrawCount = 0
  - **Expected output**: returns false

- **TC70: Game is not ongoing, draw count is 1** ( :white_check_mark: )
  - **Name of the test**: getCanDraw_called_returnFalse
  - **State of the system**: isGameOngoing = false, turnManager.getDrawCount = 1
  - **Expected output**: returns false

- **TC71: Game is not ongoing, draw count is 2** ( :white_check_mark: )
  - **Name of the test**: getCanDraw_called_returnFalse
  - **State of the system**: isGameOngoing = false, turnManager.getDrawCount = 2
  - **Expected output**: returns false

- **TC72: Game is ongoing, draw count is 0** ( :white_check_mark: )
  - **Name of the test**: getCanDraw_called_returnFalse
  - **State of the system**: isGameOngoing = true, turnManager.getDrawCount = 0
  - **Expected output**: returns false

- **TC73: Game is ongoing, draw count is 1** ( :white_check_mark: )
  - **Name of the test**: getCanDraw_called_returnTrue
  - **State of the system**: isGameOngoing = true, turnManager.getDrawCount = 1
  - **Expected output**: returns true

- **TC74: Game is ongoing, draw count is 2** ( :white_check_mark: )
  - **Name of the test**: getCanDraw_called_returnTrue
  - **State of the system**: isGameOngoing = true, turnManager.getDrawCount = 1
  - **Expected output**: returns true

### Method under test: `getCanPlay()`
- **TC75: Game is not ongoing, cannot play** ( :white_check_mark: )
  - **Name of the test**: getCanPlay_called_returnFalse
  - **State of the system**: 
    - isGameOngoing = false
    - canPlay = false
  - **Expected output**: returns false

- **TC76: Game is ongoing, cannot play** ( :white_check_mark: )
  - **Name of the test**: getCanPlay_called_returnFalse
  - **State of the system**:
    - isGameOngoing = true
    - canPlay = false
  - **Expected output**: returns false

- **TC77: Game is ongoing, can play** ( :white_check_mark: )
  - **Name of the test**: getCanPlay_called_returnTrue
  - **State of the system**:
    - isGameOngoing = true
    - canPlay = true
  - **Expected output**: returns true

### Method under test: `changeCurrentPlayerIndex(int newPlayerIndex)`
- **TC78: This method is called successfully** ( :white_check_mark: )
  - **Name of the test**: changeCurrentPlayerIndex_called_callsTurnManager
  - **State of the system**: 
    - newPlayerIndex = 0
    - aliveIndices = {0}
  - **Expected output**: 
    - isFaceUp = false
    - calls turnManager.setCurrentPlayerIndex with newPlayerIndex

- **TC79: Player at index is dead** ( :white_check_mark: )
  - **Name of the test**: changeCurrentPlayerIndex_alivePlayerIndex_failed
  - **State of the system**: 
    - newPlayerIndex = 0
    - aliveIndices = {1}
  - **Expected output**: throw IllegalStateException "error.playerIsDead"

- **TC80: Turn manager method throws exception** ( :white_check_mark: )
  - **Name of the test**: changeCurrentPlayerIndex_called_failed
  - **State of the system**: 
    - newPlayerIndex = 1
    - aliveIndices = {0, 1}
    - turnManager.setCurrentPlayerIndex with newPlayerIndex throws IllegalArgumentException "error.invalidPlayerIndex"
  - **Expected output**: throw IllegalArgumentException "error.invalidPlayerIndex"

### Method under test: `drawFromPile()`
- **TC81: This method is called with non-exploding card** ( :white_check_mark: )
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
    - canPlay = false
    - returns card

- **TC82: This method is called with exploding kitten** ( :white_check_mark: )
  - **Name of the test**: drawFromPile_explodingCard_returnsExplodingCard
  - **State of the system**:
    - card = drawPile.peekTop
    - card.getType = EXPLODING_KITTEN
    - player = getCurrentPlayer
  - **Expected output**:
    - turnManager.decrementDrawCount is called
    - player.deselectHandCards is called
    - canPlay = false
    - returns card

- **TC83: Throw exception from drawPile** ( :white_check_mark: )
  - **Name of the test**: drawFromPile_drawPileException_failed
  - **State of the system**: drawPile.removeTop throws IllegalStateException "error.emptyDeck"
  - **Expected output**: throw IllegalStateException "error.emptyDeck"

- **TC84: Throw exception from turnManager** ( :white_check_mark: )
  - **Name of the test**: drawFromPile_turnManagerException_failed
  - **State of the system**: turnManager.decrementDrawCount throws IllegalStateException "error.negativeDrawCount"
  - **Expected output**: throw IllegalStateException "error.negativeDrawCount"

### Method under test: `toggleFaceUp()`
- **TC85: Is face up** ( :white_check_mark: )
  - **Name of the test**: toggleFaceUp_called_setToFalse
  - **State of the system**: isFaceUp = true
  - **Expected output**: isFaceUp = false

- **TC86: Is face down** ( :white_check_mark: )
  - **Name of the test**: toggleFaceUp_called_togglesFaceUp
  - **State of the system**: isFaceUp = false
  - **Expected output**: isFaceUp = true

### Method under test: `toggleSelectedCurrentPlayerCardAt(int handCardIndex)`
- **TC87: Hand card index at 0** ( :white_check_mark: )
  - **Name of the test**: toggleSelectedCurrentPlayerCardAt_called_calledPlayerToggle
  - **State of the system**:
    - player = getCurrentPlayer
    - handCardIndex = 0
  - **Expected output**: player.toggleSelectedPlayerCardAt is called with handCardIndex

- **TC88: Player method throws exception** ( :white_check_mark: )
  - **Name of the test**: toggleSelectedCurrentPlayerCardAt_indexZero_failed
  - **State of the system**: player.toggleSelectedPlayerCardAt throws InvalidArgumentException "error.handCardIndexOutOfBounds"
  - **Expected output**: throws InvalidArgumentException "error.handCardIndexOutOfBounds"

### Method under test: `endTurn()`
- **TC89: Can end turn** ( :white_check_mark: )
  - **Name of the test**: endTurn_canEndTurnNoWinnerWinner_endTurnAndDeselectCards
  - **State of the system**: canEndTurn = true
  - **Expected output**:
    - currenPlayer.deselectHandCards is called
    - reachedWinnerWinnerCondition returns false
    - canPlay = true
    - isFaceUp = false
    - turnManager.incrementTurn is called with getAliveIndices
    - turnManager.incrementDrawCount is called

- **TC90: Winner winner condition reached, winner at index 0** ( :x: )
  - **Name of the test**: endTurn_canEndTurnReachedWinnerWinner_endGame
  - **State of the system**:
    - canEndTurn = true
    - 2 players
    - currentPlayer at index 0
  - **Expected output**:
    - getCurrentPlayer.deselectHandCards is called
    - reachedWinnerWinnerCondition returns true
    - player.eliminate is called with all players except currentPlayer
    - canPlay = false
    - isFaceUp = true
    - isGameOngoing = false

- **TC90: Winner winner condition reached, winner at last index** ( :x: )
  - **Name of the test**: endTurn_canEndTurnReachedWinnerWinner_endGame
  - **State of the system**:
    - canEndTurn = true
    - 2 players
    - currentPlayer at index 1
  - **Expected output**:
    - getCurrentPlayer.deselectHandCards is called
    - reachedWinnerWinnerCondition returns true
    - player.eliminate is called with all players except currentPlayer
    - canPlay = false
    - isFaceUp = true
    - isGameOngoing = false

- **TC92: Cannot end turn** ( :white_check_mark: )
  - **Name of the test**: endTurn_cannotEndTurn_failed
  - **State of the system**: canEndTurn = false
  - **Expected output**: throws InvalidStateException "error.cannotEndTurn"

### Method under test: `getDrawPileSize()`
- **TC93: This method is called** ( :white_check_mark: )
  - **Name of the test**: getDrawPileSize_called_returnDrawPileMethodCall
  - **State of the system**: drawPile.size = N, N = [0, 1, 2]
  - **Expected output**: returns drawPile.size

### Method under test: `playExplode()`
- **TC94: Empty draw pile** ( :white_check_mark: )
  - **Name of the test**: playExplode_emptyDrawPile_failed
  - **State of the system**: drawPile.removeTop throws IllegalStateException "error.emptyDeck"
  - **Expected output**: throws IllegalStateException "error.emptyDeck"

- **TC95: All two alive, kill one player** ( :white_check_mark: )
  - **Name of the test**: playExplode_twoAlivePlayers_oneWins
  - **State of the system**: 
    - 2 players, both alive
    - currentPlayerIndex = 0
    - isGameOngoing = true
  - **Expected output**:
    - drawPile.removeTop is called
    - getCurrentPlayer.deselectHandCards is called
    - getCurrentPlayer.eliminate is called
    - aliveIndices = {1}
    - isGameOngoing = false

- **TC96: Three players, one wins** ( :white_check_mark: )
  - **Name of the test**: playExplode_twoAlivePlayers_oneWins
  - **State of the system**:
    - 3 players, index 1 is dead
    - currentPlayerIndex = 0
    - isGameOngoing = true
  - **Expected output**:
    - drawPile.removeTop is called
    - getCurrentPlayer.deselectHandCards is called
    - getCurrentPlayer.eliminate is called
    - aliveIndices = {2}
    - isGameOngoing = false;

- **TC97: All three alive, kill one player** ( :x: )
  - **Name of the test**: playExplode_threeAlive_gameContinues
  - **State of the system**:
    - 3 players, all alive
    - currentPlayerIndex = 2
    - isGameOngoing = true
  - **Expected output**:
    - drawPile.removeTop is called
    - getCurrentPlayer.deselectHandCards is called
    - getCurrentPlayer.eliminate is called
    - aliveIndices = {0, 1}
    - isGameOngoing = true
    - canPlay = true
    - isFaceUp = false
    - turnManager.incrementTurn is called with getAliveIndices
    - turnManager.incrementDrawCount is called

- **TC98: One player is alive, kill one player** ( :x: )
  - **Name of the test**: playExplode_atLeastTwoAlive_gameContinues
  - **State of the system**:
    - 4 players, index 0 is dead
    - currentPlayerIndex = 3
    - isGameOngoing = true
  - **Expected output**:
    - drawPile.removeTop is called
    - getCurrentPlayer.deselectHandCards is called
    - getCurrentPlayer.eliminate is called
    - aliveIndices = {1, 2}
    - isGameOngoing = true
    - canPlay = true
    - isFaceUp = false
    - turnManager.incrementTurn is called with getAliveIndices
    - turnManager.incrementDrawCount is called

### Method under test: `isDefusable()`
- **TC99: Empty hand** ( :white_check_mark: )
  - **Name of the test**: isDefusable_noDefuser_returnFalse
  - **State of the system**:
    - currentPlayerHandCardTypes = []
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return false

- **TC100: Hand with one card type, no Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_noDefuser_returnFalse
  - **State of the system**:
    - currentPlayerHandCardTypes = [ATTACK]
    - topDiscardType = ATTACK
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return false

- **TC101: Hand with two different card types, no Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_noDefuser_returnFalse
  - **State of the system**:
    - currentPlayerHandCardTypes = [ATTACK, SKIP]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return false

- **TC102: Hand with two same card types, no Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_noDefuser_returnFalse
  - **State of the system**:
    - currentPlayerHandCardTypes = [SKIP, SKIP]
    - topDiscardType = ATTACK
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return false

- **TC103: Hand with Clone, top discard type is not Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_noDefuser_returnFalse
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, SKIP]
    - topDiscardType = ATTACK
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return false

- **TC104: Hand with two Clones, top discard type is not Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_noDefuser_returnFalse
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, CLONE]
    - topDiscardType = ATTACK
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return false

- **TC105: Hand with one card type, has Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [DEFUSE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC106: Hand with two different card types, has Defuse at end** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [SKIP, DEFUSE]
    - topDiscardType = SKIP
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC107: Hand with two different card types, has Defuse at front** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [DEFUSE, SKIP]
    - topDiscardType = ATTACK
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC108: Hand with two same card types, has Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [DEFUSE, DEFUSE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC109: Hand with one card type, has Godcat** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [GODCAT]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC110: Hand with two different card types, has Godcat at end** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [SKIP, GODCAT]
    - topDiscardType = SKIP
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC111: Hand with two different card types, has Godcat at front** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [GODCAT, SKIP]
    - topDiscardType = ATTACK
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC112: Hand with two same card types, has Godcat** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [GODCAT, GODCAT]
    - topDiscardType = DEFUSE
  - **Expected output**: return true

- **TC113: Hand with Defuse and Godcat** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [DEFUSE, GODCAT]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC114: Hand with Godcat and Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [GODCAT, DEFUSE]
    - topDiscardType = ATTACK
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC115: Hand with one card type, has Clone** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC116: Hand with two different card types, has Clone at end** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [SKIP, CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC117: Hand with two different card types, has Clone at front** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, SKIP]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC118: Hand with two same card types, has Clone** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**: return true

- **TC119: Hand with Defuse and Clone** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [DEFUSE, CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC120: Hand with Clone and Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, DEFUSE]
    - topDiscardType = ATTACK
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC121: Hand with Clone and Godcat, use Godcat** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, GODCAT]
    - topDiscardType = ATTACK
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC122: Hand with Godcat and Clone, use Clone** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [GODCAT, CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

- **TC123: Hand with Godcat, Clone, Defuse** ( :white_check_mark: )
  - **Name of the test**: isDefusable_hasDefuser_returnTrue
  - **State of the system**:
    - currentPlayerHandCardTypes = [GODCAT, CLONE, DEFUSE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - discardCard.peekTop returns topDiscardCard
    - return true

### Method under test: `playDefuse(int drawPileIndex)`
- **TC124: Empty hand** ( :white_check_mark: )
  - **Name of the test**: playDefuse_noDefuser_failed
  - **State of the system**: 
    - currentPlayerHandCardTypes = []
    - topDiscardType = DEFUSE
  - **Expected output**: throws IllegalStateException "error.currentPlayerNoDefuser"

- **TC125: Hand with one card type, no Defuse** ( :white_check_mark: )
  - **Name of the test**: playDefuse_noDefuser_failed
  - **State of the system**: 
    - currentPlayerHandCardTypes = [ATTACK]
    - topDiscardType = ATTACK
  - **Expected output**: throws IllegalStateException "error.currentPlayerNoDefuser"

- **TC126: Hand with two different card types, no Defuse** ( :white_check_mark: )
  - **Name of the test**: playDefuse_noDefuser_failed
  - **State of the system**: 
    - currentPlayerHandCardTypes = [ATTACK, SKIP]
    - topDiscardType = DEFUSE
  - **Expected output**: throws IllegalStateException "error.currentPlayerNoDefuser"

- **TC127: Hand with two same card types, no Defuse** ( :white_check_mark: )
  - **Name of the test**: playDefuse_noDefuser_failed
  - **State of the system**:
    - currentPlayerHandCardTypes = [SKIP, SKIP]
    - topDiscardType = ATTACK
  - **Expected output**: throws IllegalStateException "error.currentPlayerNoDefuser"

- **TC128: Hand with Clone, top discard type is not Defuse** ( :white_check_mark: )
  - **Name of the test**: playDefuse_noDefuser_failed
  - **State of the system**:
    -  currentPlayerHandCardTypes = [CLONE, SKIP]
    - topDiscardType = ATTACK
  - **Expected output**: throws IllegalStateException "error.currentPlayerNoDefuser"

- **TC129: Hand with two Clones, top discard type is not Defuse** ( :white_check_mark: )
  - **Name of the test**: playDefuse_noDefuser_failed
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, CLONE]
    - topDiscardType = ATTACK
  - **Expected output**: throws IllegalStateException "error.currentPlayerNoDefuser"

- **TC130: Hand with one card type, has Defuse** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [DEFUSE]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC131: Hand with two different card types, has Defuse at end** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [SKIP, DEFUSE]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 1 is called
    - discardPile.addCard with card at index 1 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC132: Hand with two different card types, has Defuse at front** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [DEFUSE, SKIP]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC133: Hand with two same card types, has Defuse** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [DEFUSE, DEFUSE]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC134: Hand with one card type, has Godcat** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [GODCAT]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC135: Hand with two different card types, has Godcat at end** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [SKIP, GODCAT]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 1 is called
    - discardPile.addCard with card at index 1 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC136: Hand with two different card types, has Godcat at front** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [GODCAT, SKIP]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC137: Hand with two same card types, has Godcat** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [GODCAT, GODCAT]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC138: Hand with Defuse and Godcat** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [DEFUSE, GODCAT]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC139: Hand with Godcat and Defuse** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**: currentPlayerHandCardTypes = [GODCAT, DEFUSE]
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 1 is called
    - discardPile.addCard with card at index 1 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC140: Hand with one card type, has Clone** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC141: Hand with two different card types, has Clone at end** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**:
    - currentPlayerHandCardTypes = [SKIP, CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 1 is called
    - discardPile.addCard with card at index 1 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC142: Hand with two different card types, has Clone at front** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, SKIP]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC143: Hand with two same card types, has Clone** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC144: Hand with Defuse and Clone** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**:
    - currentPlayerHandCardTypes = [DEFUSE, CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 0 is called
    - discardPile.addCard with card at index 0 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC145: Hand with Clone and Defuse** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, DEFUSE]
    - topDiscardType = ATTACK
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand(card2) is called
    - discardPile.addCard with card at index 1 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC146: Hand with Clone and Godcat, use Godcat** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**:
    - currentPlayerHandCardTypes = [CLONE, GODCAT]
    - topDiscardType = ATTACK
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 1 is called
    - discardPile.addCard with card at index 1 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC147: Hand with Godcat and Clone, use Clone** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**:
    - currentPlayerHandCardTypes = [GODCAT, CLONE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 1 is called
    - discardPile.addCard with card at index 1 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC148: Hand with Godcat, Clone, Defuse** ( :white_check_mark: )
  - **Name of the test**: playDefuse_hasDefuser_reinsertExplodingKitten
  - **State of the system**:
    - currentPlayerHandCardTypes = [GODCAT, CLONE, DEFUSE]
    - topDiscardType = DEFUSE
  - **Expected output**:
    - getCurrentPlayer.removeCardFromHand with card at index 2 is called
    - discardPile.addCard with card at index 2 is called
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex is called

- **TC149: Invalid draw pile index** ( :white_check_mark: )
  - **Name of the test**: playDefuse_invalidDrawPileIndex_failed
  - **State of the system**:
    - currentPlayerHandCardTypes = [DEFUSE, DEFUSE]
    - drawPile.insertCardAt with drawPile.removeTop and drawPileIndex throws IllegalArgumentException "error.invalidDeckIndex"
  - **Expected output**: throws IllegalArgumentException "error.invalidDeckIndex"

### Method under test: `applyShuffle()`
- **TC150: Apply shuffle card effect** ( :white_check_mark: )
  - **Name of the test**: applyShuffle_called_shufflesDrawPile
  - **State of the system**: Shuffle card effect is applied
  - **Expected output**: drawPile.shuffle() is called

- **TC151: Draw pile has zero cards** ( :white_check_mark: )
  - **Name of the test**: applySwapTopAndBottom_emptyDeck_remainsEmpty
  - **State of the system**: draw pile is empty
  - **Expected output**: draw pile remains empty

- **TC152: Draw pile has exactly one card** ( :white_check_mark: )
  - **Name of the test**: applySwapTopAndBottom_oneCard_deckUnchanged
  - **State of the system**: draw pile has two cards ['CARD 1']
  - **Expected output**: draw pile has two cards; card order is ['CARD 1']

- **TC153: Draw pile has more than one card** ( :white_check_mark: )
  - **Name of the test**: applySwapTopAndBottom_moreThanOneCard_swapped
  - **State of the system**: draw pile has four cards ['CARD 1', 'CARD 2', 'CARD 3', 'CARD 4']
  - **Expected output**:
    - drawPile.addCardToTop(CARD_4) called
    - drawPile.addCardToBottom(CARD_1) called
    - draw pile has four cards and card order is ['CARD 4', 'CARD 2', 'CARD 3', 'CARD']

- **TC154: Top and bottom cards are the same type** ( :white_check_mark: )
  - **Name of the test**: applySwapTopAndBottom_sameType_swapped
  - **State of the system**: draw pile has four cards ['EXPLODING KITTEN 1', 'CARD 2', 'CARD 3', 'EXPLODING KITTEN 2']
  - **Expected output**:
    - drawPile.addCardToTop(EXPLODINGKITTEN_2) called
    - drawPile.addCardToBottom(EXPLODINGKITTEN_1) called;
    - draw pile has four cards; card order is ['EXPLODING KITTEN 2', 'CARD 2', 'CARD 3', 'EXPLODING KITTEN 1']

### Method under test: `applySkip()`
- **TC155: Cannot end turn** ( :white_check_mark: )
  - **Name of the test:** applySkip_cannotEndTurn_decrementDrawCount
  - **State of the system:** canEndTurn = false
  - **Expected output:** turnManager.decrementDrawCount is called

- **TC156: Can end turn** ( :white_check_mark: )
  - **Name of the test:** applySkip_canEndTurn_decrementDrawCountAndEndTurn
  - **State of the system:** canEndTurn = true
  - **Expected output:** 
    - turnManager.decrementDrawCount is called
    - endTurn is called

- **TC157: Game method end turn throws** ( :white_check_mark: )
  - **Name of the test:** applySkip_canEndTurnThrows_failed
  - **State of the system:** 
    - canEndTurn = true
    - endTurn throws IllegalStateException "error.cannotEndTurn"
  - **Expected output:**
    - turnManager.decrementDrawCount is called
    - throw IllegalStateException "error.cannotEndTurn"

### Method under test: `getSeeTheFutureCardIds()`
- **TC161: Empty list** ( :white_check_mark: )
  - **Name of the test:** getSeeTheFutureCardIds_called_returnTopDrawPileCards
  - **State of the system:** topCards = []
  - **Expected output:** return []

- **TC162: One card** ( :white_check_mark: )
  - **Name of the test:** getSeeTheFutureCardIds_called_returnTopDrawPileCards
  - **State of the system:** topCards = [SKIP_1]
  - **Expected output:** return ["SKIP_1"]

- **TC163: Two different card ids** ( :white_check_mark: )
  - **Name of the test:** getSeeTheFutureCardIds_called_returnTopDrawPileCards
  - **State of the system:** topCards = [SKIP_1, SKIP_2]
  - **Expected output:** return ["SKIP_1", "SKIP_2"]

- **TC164: Two different card types** ( :white_check_mark: )
  - **Name of the test:** getSeeTheFutureCardIds_called_returnTopDrawPileCards
  - **State of the system:** topCards = [SKIP_1, ATTACK_1]
  - **Expected output:** return ["SKIP_1", "ATTACK_1"]

- **TC165: Two same cards** ( :white_check_mark: )
  - **Name of the test:** getSeeTheFutureCardIds_called_returnTopDrawPileCards
  - **State of the system:** topCards = [SKIP_1, SKIP_1]
  - **Expected output:** return ["SKIP_1", "SKIP_1"]

### Method under test: `applyCatomicBomb()`
- **TC166: Draw pile is empty** ( :white_check_mark: )
  - **Name of the test**: applyCatomicBomb_emptyDeck_remainsEmpty
  - **State of the system**:
    - draw pile has no cards
    - canEndTurn returns true
  - **Expected output**:
    - draw pile remains empty
    - turnManager.decrementDrawCount is called
    - endTurn is called

- **TC167: No Exploding Kittens in draw pile** ( :white_check_mark: )
  - **Name of the test**: applyCatomicBomb_noExplodingKittens_deckUnchanged
  - **State of the system**:
    - draw pile contains ['SKIP_1', 'ATTACK_1', 'SHUFFLE_1'], none are CardType 'EXPLODING_KITTEN'
    - canEndTurn returns false
  - **Expected output**:
    - addCardToBottom called for SKIP_1, ATTACK_1, SHUFFLE_1 in order
    - draw pile order is unchanged and remains ['SKIP_1', 'ATTACK_1', 'SHUFFLE_1']
    - turnManager.decrementDrawCount is called

- **TC168: One Exploding Kitten at top of draw pile** ( :white_check_mark: )
  - **Name of the test**: applyCatomicBomb_oneExplodingKittenAlreadyOnTop_deckUnchanged
  - **State of the system**:
    - top card of draw pile has one CardType 'EXPLODING_KITTEN' with order ['EXPLODING_KITTEN_1' , 'ATTACK_1', 'SHUFFLE_1']
    - canEndTurn returns true
  - **Expected output**:
    - addCardToBottom called for ATTACK_1, SHUFFLE_1
    - addCardToTop called for EXPLODINGKITTEN_1
    - draw pile order remains ['EXPLODINGKITTEN_1', 'ATTACK_1', 'SHUFFLE_1']
    - turnManager.decrementDrawCount is called
    - endTurn is called

- **TC169: One Exploding Kitten in the middle of draw pile** ( :white_check_mark: )
  - **Name of the test**: applyCatomicBomb_oneExplodingKittenInMiddle_movedToTop
  - **State of the system**:
    - draw pile contains ['SKIP_1', 'ATTACK_1', 'EXPLODING_KITTEN_1', 'SHUFFLE_1']
    - canEndTurn returns false
  - **Expected output**:
    - addCardToBottom called for SKIP_1, ATTACK_1, SHUFFLE_1
    - addCardToTop called for EXPLODINGKITTEN_1
    - draw pile order is ['EXPLODINGKITTEN_1', 'SKIP_1', 'ATTACK_1', 'SHUFFLE_1']
    - turnManager.decrementDrawCount is called

- **TC170: Multiple Exploding Kittens scattered throughput draw pile** ( :white_check_mark: )
  - **Name of the test**: applyCatomicBomb_multipleExplodingKittens_allMovedToTop
  - **State of the system**:
    - draw pile contains ['SKIP_1', 'EXPLODING KITTEN_1', 'ATTACK_1', 'EXPLODING KITTEN_2', 'SHUFFLE_1']
    - canEndTurn returns true
  - **Expected output**:
    - addCardToBottom called for SKIP_1, ATTACK_1, SHUFFLE_1
    - addCardToTop called for EXPLODINGKITTEN_1, EXPLODINGKITTEN_2
    - draw pile order is ['EXPLODINGKITTEN_1', 'EXPLODINGKITTEN_2', 'SKIP_1', 'ATTACK_1', 'SHUFFLE_1']
    - turnManager.decrementDrawCount is called
    - endTurn is called

- **TC171: All cards in draw pile are Exploding Kittens** ( :white_check_mark: )
  - **Name of the test**: applyCatomicBomb_allExplodingKittens_deckOrderUnchanged
  - **State of the system**:
    - draw pile contains ['EXPLODING KITTEN_1', 'EXPLODING KITTEN_2', 'EXPLODING KITTEN_3']
    - canEndTurn returns false
  - **Expected output**:
    - addCardToTop called for EXPLODINGKITTEN_1, EXPLODINGKITTEN_2, EXPLODINGKITTEN_3
    - draw pile order unchanged
    - turnManager.decrementDrawCount is called

### Method under test: `applySuperSkip()`
- **TC172: Super Skip called** ( :white_check_mark: )
  - **Name of the test:** applySuperSkip_called_endTurn
  - **State of the system:** N/A
  - **Expected output:** 
    - turnManager.setDrawCount is called with NUM_DRAW_COUNT_AFTER_SUPER_SKIP
    - endTurn is called

- **TC172: game method end turn throws exception** ( :white_check_mark: )
  - **Name of the test:** applySuperSkip_endTurnThrows_failed
  - **State of the system:** endTurn throws IllegalStateException "error.cannotEndTurn"
  - **Expected output:**
    - turnManager.setDrawCount is called with NUM_DRAW_COUNT_AFTER_SUPER_SKIP
    - throw IllegalStateException "error.cannotEndTurn"

### Method under test: `applyGodcat(CardType cardType)`
- **TC178: Invalid card type Godcat** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_invalidCardType_throwsException
  - **State of the system**: CardType.GODCAT passed as cardType
  - **Expected output**: throws exception "error.cannotPlaySelectedCards"

- **TC179: Invalid card type Exploding Kitten** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_invalidCardType_throwsException
  - **State of the system**: CardType.EXPLODING_KITTEN passed as cardType
  - **Expected output**: throws exception "error.cannotPlaySelectedCards"

- **TC180: Invalid card type Defuse** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_invalidCardType_throwsException
  - **State of the system**: CardType.DEFUSE passed as cardType
  - **Expected output**: throws exception "error.cannotPlaySelectedCards"

- **TC181: Valid card type Attack** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.ATTACK passed as cardType
  - **Expected output**: applyAttack() is called

- **TC182: Valid card type Shuffle** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.SHUFFLE passed as cardType
  - **Expected output**: applyShuffle() is called

- **TC183: Valid card type Skip** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.SKIP passed as cardType
  - **Expected output**: applySkip() is called

- **TC184: Valid card type See the Future** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validPlayWithoutApplyMethod_noApplyCalled
  - **State of the system**: CardType.SEE_THE_FUTURE passed as cardType
  - **Expected output**: N/A

- **TC185: Valid card type Catomic Bomb** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.CATOMIC_BOMB passed as cardType
  - **Expected output**: applyCatomicBomb() is called

- **TC186: Valid card type Super Skip** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.SUPER_SKIP passed as cardType
  - **Expected output**: applySuperSkip() is called

- **TC187: Valid card type Clone** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.CLONE passed as cardType
  - **Expected output**: applyClone() is called

- **TC188: Valid card type Swap Top and Bottom** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.SWAP_TOP_AND_BOTTOM passed as cardType
  - **Expected output**: applySwapTopAndBottom() is called

- **TC189: Valid card type Draw From the Bottom** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.DRAW_FROM_THE_BOTTOM passed as cardType
  - **Expected output**: applyDrawFromTheBottom() is called

- **TC190: Valid card type Targeted Attack** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validPlayWithoutApplyMethod_noApplyCalled
  - **State of the system**: CardType.TARGETED_ATTACK passed as cardType
  - **Expected output**: N/A

- **TC191: Valid card type Winner Winner Catnip Dinner** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.WINNER_WINNER_CATNIP_DINNER passed as cardType
  - **Expected output**: applyWinnerWinnerCatnipDinner() is called

- **TC192: Valid card type Ragebait** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.RAGEBAIT passed as cardType
  - **Expected output**: applyRagebait() is called

- **TC193: Valid card type Recycle** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.RECYCLE passed as cardType
  - **Expected output**: applyRecycle() is called

- **TC194: Valid card type Double Up** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.DOUBLE_UP passed as cardType
  - **Expected output**: applyDoubleUp() is called

- **TC195: Valid card type Mild Shuffle** ( :white_check_mark: )
  - **Name of the test**: applyGodcat_validCardType_correctApplyCalled
  - **State of the system**: CardType.MILD_SHUFFLE passed as cardType
  - **Expected output**: applyMildShuffle() is called

### Method under test: `applySwapTopAndBottom()`
- **TC196: Draw pile has zero cards** ( :white_check_mark: )
  - **Name of the test**: applySwapTopAndBottom_emptyDeck_remainsEmpty
  - **State of the system**: draw pile is empty
  - **Expected output**: draw pile remains empty

- **TC197: Draw pile has exactly one card** ( :white_check_mark: )
  - **Name of the test**: applySwapTopAndBottom_oneCard_deckUnchanged
  - **State of the system**: draw pile has two cards ['CARD 1']
  - **Expected output**: draw pile has two cards; card order is ['CARD 1']

- **TC198: Draw pile has more than one card** ( :white_check_mark: )
  - **Name of the test**: applySwapTopAndBottom_moreThanOneCard_swapped
  - **State of the system**: draw pile has four cards ['CARD 1', 'CARD 2', 'CARD 3', 'CARD 4']
  - **Expected output**:
    - drawPile.addCardToTop(CARD_4) called
    - drawPile.addCardToBottom(CARD_1) called
    - draw pile has four cards and card order is ['CARD 4', 'CARD 2', 'CARD 3', 'CARD']

- **TC199: Top and bottom cards are the same type** ( :white_check_mark: )
  - **Name of the test**: applySwapTopAndBottom_sameType_swapped
  - **State of the system**: draw pile has four cards ['EXPLODING KITTEN 1', 'CARD 2', 'CARD 3', 'EXPLODING KITTEN 2']
  - **Expected output**:
    - drawPile.addCardToTop(EXPLODINGKITTEN_2) called
    - drawPile.addCardToBottom(EXPLODINGKITTEN_1) called;
    - draw pile has four cards; card order is ['EXPLODING KITTEN 2', 'CARD 2', 'CARD 3', 'EXPLODING KITTEN 1']

# Method under test: `applyTargetedAttack(int targetPlayerIndex)`
- **TC200: Targeted Attack with minimum players (2), first player targets next player** ( :white_check_mark: )
  - **Name of the test**: applyTargetedAttack_validTargets_successfullyCalled
  - **State of the system**: 
    - 2 players
    - currentPlayerIndex = 0
    - targetPlayerIndex = 1
  - **Expected output**:
    - deselectHandCards() called on player 0
    - incrementTurn() called 1 time with getAliveIndices
    - addAttackDrawCount() called

- **TC201: Targeted Attack with minimum players (2), last player targets first player (wrap)** ( :white_check_mark: )
  - **Name of the test**: applyTargetedAttack_validTargets_successfullyCalled
  - **State of the system**: 
    - 2 players
    - currentPlayerIndex = 1
    - targetPlayerIndex = 0
  - **Expected output**:
    - deselectHandCards() called on player 1
    - incrementTurn() called 1 time with getAliveIndices
    - addAttackDrawCount() called

- **TC202: Targeted Attack with maximum players (4), first player targets last player** ( :white_check_mark: )
  - **Name of the test**: applyTargetedAttack_validTargets_successfullyCalled
  - **State of the system**: 
    - 4 players
    - currentPlayerIndex = 0
    - targetPlayerIndex = 3
  - **Expected output**:
    - deselectHandCards() called on player 0
    - incrementTurn() called 3 times with getAliveIndices
    - addAttackDrawCount() called

- **TC203: Targeted Attack with maximum players (4), last player targets first player (wrap)** ( :white_check_mark: )
  - **Name of the test**: applyTargetedAttack_validTargets_successfullyCalled
  - **State of the system**: 
    - 4 players, currentPlayerIndex = 3
    - targetPlayerIndex = 0
  - **Expected output**:
    - deselectHandCards() called on player 3
    - incrementTurn() called 1 time with getAliveIndices
    - addAttackDrawCount() called

### Method under test: `addAttackDrawCount()`
- **TC204: Draw count is below the attack threshold** ( :white_check_mark: )
  - **Name of the test**: addAttackDrawCount_drawCountZero_SetTwo
  - **State of the system**: turnManager.drawCount = 1
  - **Expected output**: turnManager.drawCount = 2

- **TC205: Draw count is at or above the attack threshold** ( :white_check_mark: )
  - **Name of the test**: addAttackDrawCount_drawCountTwo_addsTwo
  - **State of the system**: turnManager.drawCount = 2
  - **Expected output**: turnManager.drawCount = 4

### Method under test: `reachedWinnerWinnerCondition()`
- **TC206: Is not activated** ( :white_check_mark: )
  - **Name of the test**: reachedWinnerWinnerCondition_notActivated_returnFalse
  - **State of the system**: getCurrentPlayer.isWinnerWinnerActivated = false
  - **Expected output**: return false

- **TC207: Is activated, one more round** ( :white_check_mark: )
  - **Name of the test**: reachedWinnerWinnerCondition_wrongNumberOfRounds_returnFalse
  - **State of the system**:
    - getCurrentPlayer.isWinnerWinnerActivated = true
    - getCurrentPlayer.getWinnerWinnerActivatedRound = 1
    - turnManager.getRoundCount = WINNER_WINNER_REQUIRED_ROUNDS
  - **Expected output**: return false

- **TC207: Is activated, one round over** ( :white_check_mark: )
  - **Name of the test**: reachedWinnerWinnerCondition_wrongNumberOfRounds_returnFalse
  - **State of the system**:
    - getCurrentPlayer.isWinnerWinnerActivated = true
    - getCurrentPlayer.getWinnerWinnerActivatedRound = 1
    - turnManager.getRoundCount = WINNER_WINNER_REQUIRED_ROUNDS + 2
  - **Expected output**: return false

- **TC208: Is activated, required rounds reached** ( :white_check_mark: )
  - **Name of the test**: reachedWinnerWinnerCondition_reachedRequirement_returnTrue
  - **State of the system**:
    - getCurrentPlayer.isWinnerWinnerActivated = true
    - getCurrentPlayer.getWinnerWinnerActivatedRound = 1
    - turnManager.getRoundCount = WINNER_WINNER_REQUIRED_ROUNDS + 1
  - **Expected output**: return true

### Method under test: `applyWinnerWinnerCatnipDinner()`
- **TC209: Player throws exception** ( :x: )
  - **Name of the test**: applyWinnerWinnerCatnipDinner_called_activateWinnerWinnerCount
  - **State of the system**:
    - turnManager.getRoundCount = 0
    - getCurrentPlayer.activateWinnerWinnerFromRound called with turnManager.getRoundCount throws IllegalArgumentException "error.invalidRound"
  - **Expected output**: throw IllegalArgumentException "error.invalidRound" 

- **TC210: Player throws exception** ( :x: )
  - **Name of the test**: applyWinnerWinnerCatnipDinner_called_activateWinnerWinnerCount
  - **State of the system**: turnManager.getRoundCount = 1
  - **Expected output**: getCurrentPlayer.activateWinnerWinnerFromRound is called with turnManager.getRoundCount

### Method under test: `getAliveIndices()`
- **TC211: No alive players** ( :white_check_mark: )
  - **Name of the test**: getAliveIndices_noAlivePlayers_returnEmptySet
  - **State of the system**: 2 dead players
  - **Expected output**: return {}

- **TC212: One alive player** ( :white_check_mark: )
  - **Name of the test**: getAliveIndices_oneAlivePlayer_returnAliveIndices
  - **State of the system**:
    - 2 dead players
    - 1 alive player at index 0
  - **Expected output**: return {0}

- **TC213: Two alive players** ( :white_check_mark: )
  - **Name of the test**: getAliveIndices_twoAlivePlayers_returnAliveIndices
  - **State of the system**:
    - 1 dead player
    - 2 alive players at indices 0 and 2
  - **Expected output**: return {0, 2}

- **TC214: All alive players** ( :white_check_mark: )
  - **Name of the test**: getAliveIndices_allAlivePlayers_returnAliveIndices
  - **State of the system**:
    - 4 alive players at indices 0, 1, 2, 3
  - **Expected output**: return {0, 1, 2, 3}

### Method under test: `getWinnerName()`
- **TC215: All players are alive** ( :white_check_mark: )
  - **Name of the test**: getWinnerName_notExactlyOneAlive_failed
  - **State of the system**: 
    - playerNames = ["Alice", "Bob"]
    - 2 alive players
  - **Expected output**: throw IllegalStateException "error.noWinner"

- **TC216: One player is alive** ( :white_check_mark: )
  - **Name of the test**: getWinnerName_notExactlyOneAlive_failed
  - **State of the system**: 
    - playerNames = ["Alice", "Bob", "Bob"]
    - one alive player at index 2 
  - **Expected output**: throw IllegalStateException "error.noWinner"

- **TC217: All players alive** ( :white_check_mark: )
  - **Name of the test**: getWinnerName_notExactlyOneAlive_failed
  - **State of the system**:
    - playerNames = ["Alice", "Bob", "Alice", "Steve"]
    - 4 alive players
  - **Expected output**: throw IllegalStateException "error.noWinner"

- **TC218: Two players, has winner** ( :white_check_mark: )
  - **Name of the test**: getWinnerName_oneAlive_returnWinnerName
  - **State of the system**: 
    - playerNames = ["Jeff", "Jeff"]
    - winner at index 1
  - **Expected output**: return "Jeff"

- **TC219: Three players, has winner** ( :white_check_mark: )
  - **Name of the test**: getWinnerName_oneAlive_returnWinnerName
  - **State of the system**:
    - playerNames = ["Audrey", "Jeff", "Chicken"]
    - winner at index 0
  - **Expected output**: return "Audrey"