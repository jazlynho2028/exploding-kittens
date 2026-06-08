# BVA Analysis: PlayerDeckController Class

## Method under test: `buildPlayerDeckScene()`
- **TC1: This method is called** ( :white_check_mark: )
  - **Name of the test**: buildPlayerDeckScene_called_success
  - **State of the system**: N/A
  - **Expected output**: 
    - buildDependenUI is called
    - bindUI is called
    - returns view.createPlayerDeckScene

- **TC2: Caught exception** ( :white_check_mark: )
  - **Name of the test**: buildPlayerDeckScene_called_failed
  - **State of the system**: buildDependentUI throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `buildDependentUI()`
- **TC3: This method is executed successfully** ( :white_check_mark: )
  - **Name of the test**: buildDependentUI_called_success
  - **State of the system**: 
    - currentPlayerHandIds = []
    - isFaceUp = true
    - canDraw = true
    - playerNames = []
    - currentPlayerIndex = 0
    - isGameOngoing = true
  - **Expected output**: 
    - view.buildAndAddPlayerHandCards is called with currentPlayerHandIds, isFaceUp, and canDraw
    - view.buildAddRenderPlayerNameTags is called with playerNames, currentPlayerIndex, and isGameOngoing

## Method under test: `bindUI()`
- **TC4: This method is called** ( :white_check_mark: )
  - **Name of the test**: bindUI_called_success
  - **State of the system**: N/A
  - **Expected output**: 
    - view.bindDrawPileButton is called
    - view.bindHandVisibilityButton is called
    - view.bindStartGameButtonview.bindNameTags is called
    - view.bindPlayCardsButton is called
    - view.bindEndTurnButton is called
    - view.bindNameTags is called
    - view.bindPlayerHandCardButtons is called

### Method under test: `onNameTag(int playerIndex)`
- **TC5: Player index is the same as current player index** ( :white_check_mark: )
  - **Name of the test**: onNameTag_playerStaysTheSame_noChange
  - **State of the system**: 
    - playerIndex = 0
    - currentPlayerIndex = 0
  - **Expected output**: no model method is called

- **TC6: Player index is different from current player index** ( :white_check_mark: )
  - **Name of the test**: onNameTag_playerChanges_success
  - **State of the system**: 
    - playerIndex = 1
    - currentPlayerIndex = 0
  - **Expected output**: 
    - called handleChangeCurrentPlayer with playerIndex
    - calls updateTurnControls

- **TC7: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onNameTag_called_failed
  - **State of the system**: 
    - playerIndex = 0
    - currentPlayerIndex = 0
    - model.getCurrentPlayerIndex throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

- **TC8: pendingTargetAction is present** ( :white_check_mark: )
  - **Name of the test**: onNameTag_pendingTargetActionPresent_executesAction
  - **State of the system**:
    - pendingTargetAction = Optional.of(mockAction)
    - playerIndex = 1
    - model.getCurrentPlayerIndex() returns 2
    - model.getIsGameOngoing() returns true
    - model.canPlaySelected() returns true
    - model.canEndTurn() returns false
  - **Expected output**:
    - mockAction.accept(1) is called
    - pendingTargetAction becomes empty
    - view.renderPlayerNameTags(2, true) is called
    - handleChangeCurrentPlayer(2) is called
    - view.renderTurnControlSection(true, false) is called

### Method under test: `handleChangeCurrentPlayer(int playerIndex)`
- **TC8: This method is executed successfully** ( :white_check_mark: )
    - **Name of the test**: handleChangeCurrentPlayer_playerChanges_success
    - **State of the system**: 
      - playerIndex = 0
      - currentPlayerIndex = 0
      - isGameOngoing = true
      - isFaceUp = true
    - **Expected output**:
      - model.changeCurrentPlayerIndex is called with playerIndex
      - model.setIsFaceUpToFalse is called
      - view.renderPlayerNameTags is called with currentPlayerIndex and isGameOngoing
      - view.renderHandVisibilityButton is called with isFaceUp
      - rebindHandCards is called

### Method under test: `rebindHandCards()`
- **TC9: This method is called** ( :white_check_mark: )
  - **Name of the test**: rebindHandCards_called_success
  - **State of the system**: 
    - currentPlayerHandIds = []
    - isFaceUp = true
    - canDraw = true
  - **Expected output**:
    - view.buildAndAddPlayerHandCards is called with currentPlayerHandIds, isFaceUp, and canDraw
    - view.bindPlayerHandCardButtons is called

### Method under test: `onDrawPile()`
- **TC10: Draw non-exploding card successfully** ( :white_check_mark: )
    - **Name of the test**: onDrawPile_drawNonExplodingCard_success
    - **State of the system**: 
      - canDraw = true
      - isDrawPileEmpty = true
      - canPlaySelected = true
      - canEndTurn = true
    - **Expected output**: 
      - model.drawFromPile = DEFUSE_1
      - view.renderDrawPile is called with canDraw and isDrawPileEmpty
      - rebindHandCards is called
      - view.renderTurnControlSection is called with canPlaySelected and canEndTurn

- **TC11: Draw Exploding Kitten, has Defuse** ( :white_check_mark: )
  - **Name of the test**: onDrawPile_drawExplodingCard_buildExplodeOverlay
  - **State of the system**:
    - hasDefuse = true
  - **Expected output**:
    - model.drawFromPile = EXPLODINGKITTEN_1
    - view.bindDefuseButton is called
    - view.buildExplodeOverlay with hasDefuse, model.drawFromPile, and model.getDrawPileSize-1 is called

- **TC12: Draw Exploding Kitten, no Defuse** ( :white_check_mark: )
  - **Name of the test**: onDrawPile_drawExplodingCard_buildExplodeOverlay
  - **State of the system**:
    - hasDefuse = false
  - **Expected output**:
    - model.drawFromPile = EXPLODINGKITTEN_1
    - view.bindExplodeButton is called
    - view.buildExplodeOverlay with hasDefuse, model.drawFromPile, and model.getDrawPileSize-1 is called

- **TC13: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onDrawPile_drawsCard_failed
  - **State of the system**: model.drawFromPile throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `onHandVisibilityButton()`
- **TC14: This method is executed successfully** ( :white_check_mark: )
    - **Name of the test**: onHandVisibilityButton_called_success
    - **State of the system**: isFaceUp = true
    - **Expected output**: 
      - model.toggleFaceUp is called
      - view.renderHandVisibilityButton is called with isFaceUp
      - rebindHandCards is called

- **TC15: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onHandVisibilityButton_called_failed
  - **State of the system**: model.toggleFaceUp throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `onPlayerHandCardButton(int handCardIndex)`
- **TC16: Cards are face up** ( :white_check_mark: )
  - **Name of the test**: onPlayerHandCardButton_cardsFaceUp_success
  - **State of the system**: 
    - handCardIndex = 0
    - isFaceUp = true
    - canPlaySelected = true
    - canEndTurn = false
  - **Expected output**: 
    - model.toggleSelectedPlayerCardAt is called with handCardIndex
    - view.renderTurnControlSection is called with canPlaySelected and canEndTurn

- **TC17: Cards are face down** ( :white_check_mark: )
    - **Name of the test**: onPlayerHandCardButton_cardsFaceDown_callsOnHandVisibility
    - **State of the system**: 
      - handCardIndex = 0
      - isFaceUp = false
    - **Expected output**: onHandVisibilityButton is called

- **TC18: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onPlayerHandCardButton_called_failed
  - **State of the system**: model.getIsFaceUp throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `onStartGameButton()`
- **TC19: Game starts successfully** ( :white_check_mark: )
  - **Name of the test**: onStartGameButton_called_success
  - **State of the system**: 
    - startingPlayerIndex = 0
    - canDraw = true
    - isDrawPileEmpty = true
    - isGameOngoing = true
    - canPlaySelected = true
    - canEndTurn = true
  - **Expected output**: 
    - model.startGame is called
    - handleChangeCurrentPlayer is called with startingPlayerIndex
    - view.renderDrawPile is called
    - view.buildAndRenderTurnControlSection is called

- **TC20: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onStartGameButton_called_failed
  - **State of the system**: model.startGame throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `onPlayCardsButton()`
- **TC21: Cards play successfully, no additional UI change** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_noAdditionalUIChange_success
  - **State of the system**: 
    - canDrawFromDiscard = true
    - topDiscardId = "SEETHEFUTURE_1"
    - canPlaySelected = true
    - canEndTurn = true
    - playSelectedCards returns CardType.SEE_THE_FUTURE
  - **Expected output**: 
    - model.playSelectedCards is called
    - view.renderDiscardPile with model.canDrawFromDiscard is called
    - model.getTopDiscardId is called
    - view.renderDrawPile is called
    - rebindHandCards is called
    - view.renderTurnControlSection with model.canPlaySelected is called
    - model.canEndTurn is called

- **TC22: Cards play successfully, Skip card** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_skipPlayed_updatedPlayer
  - **State of the system**:
    - canDrawFromDiscard = true
    - topDiscardId = "SKIP_1"
    - canPlaySelected = true
    - canEndTurn = true
    - currentPlayerIndex = 0
    - playSelectedCards returns CardType.SKIP
  - **Expected output**:
    - model.playSelectedCards is called
    - view.renderDiscardPile with model.canDrawFromDiscard is called
    - model.getTopDiscardId is called
    - view.renderDrawPile is called
    - rebindHandCards is called
    - view.renderTurnControlSection with model.canPlaySelected is called
    - model.canEndTurn is called
    - handleChangeCurrentPlayer is called with currentPlayerIndex

- **TC23: Cards play successfully, Godcat card** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_godcatPlayed_overlayShown
  - **State of the system**:
    - canDrawFromDiscard = true
    - topDiscardId = "SKIP_1"
    - canPlaySelected = true
    - canEndTurn = true
    - playSelectedCards returns CardType.GODCAT
  - **Expected output**:
    - model.playSelectedCards is called
    - view.renderDiscardPile with model.canDrawFromDiscard is called
    - model.getTopDiscardId is called
    - view.renderDrawPile is called
    - rebindHandCards is called
    - view.renderTurnControlSection with model.canPlaySelected is called
    - model.canEndTurn is called
    - view.bindGodcatConfirmButton is called
    - view.showGodcatOverlay is called

- **TC24: Targeted Attack card played** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_targetedAttackPlayed_targetSelectionEnabled
  - **State of the system**:
    - canDrawFromDiscard = true
    - topDiscardId = "TARGETED_ATTACK_1"
    - canPlaySelected = true
    - canEndTurn = true
    - currentPlayerIndex = 0
    - playSelectedCards returns CardType.TARGETED_ATTACK
  - **Expected output**:
    - model.playSelectedCards is called
    - view.renderDiscardPile(canDrawFromDiscard, topDiscardId) is called
    - rebindHandCards is called
    - view.renderTurnControlSection(canPlaySelected, canEndTurn) is called
    - pendingTargetAction becomes present
    - view.renderPlayerNameTags(currentPlayerIndex, false) is called
    - view.renderTurnControlSection(false, false) is called

- **TC25: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_called_failed
  - **State of the system**: model.playSelectedCards throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `onEndTurnButton()`
- **TC25: Turn ends successfully** ( :white_check_mark: )
  - **Name of the test**: onEndTurnButton_called_success
  - **State of the system**: 
    - currentPlayerIndex = 0
    - canDraw = true
    - isDrawPileEmpty = true
    - isGameOngoing = true
    - canPlaySelected = true
    - canEndTurn = true
  - **Expected output**:
    - model.advanceTurn is called
    - handleChangeCurrentPlayer is called with currentPlayerIndex
    - view.renderDrawPile is called with canDraw and isDrawPileEmpty
    - view.renderTurnControlSection is called with canPlaySelected and canEndTurn

- **TC26: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onEndTurnButton_called_failed
  - **State of the system**: model.advanceTurn throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `onDefuseButton()`
- **TC27: Defuse Exploding Kitten successfully** ( :white_check_mark: )
  - **Name of the test**: onDefuseButton_called_success
  - **State of the system**: 
    - currentPlayerIndex = 0
    - currentPlayerHandIds = []
    - isFaceUp = true
    - canDraw = true
    - isDrawPileEmpty = true
    - canPlaySelected = true
    - canEndTurn = true
  - **Expected output**:
    - model.playDefuse is called with view.getExplodingKittenInsertIndex
    - view.hideOverlay is called
    - view.buildAndAddPlayerHandCards is called with currentPlayerHandIds, isFaceUp, and canDraw
    - view.bindPlayerHandCardButtons is called
    - handleChangeCurrentPlayer is called with currentPlayerIndex
    - view.renderDrawPile is called with canDraw and isDrawPileEmpty
    - view.renderTurnControls is called with canPlaySelected and canEndTurn

- **TC28: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onDefuseButton_called_failed
  - **State of the system**: model.playDefuse with view.getExplodingKittenInsertIndex throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `onExplodeButton()`
- **TC29: Explode successfully** ( :white_check_mark: )
  - **Name of the test**: onExplodeButton_called_success
  - **State of the system**:
    - currentPlayerIndex = 0
    - canDraw = true
    - isDrawPileEmpty = true
    - canPlaySelected = true
    - canEndTurn = true
  - **Expected output**:
    - model.playExplode is called
    - view.hideOverlay is called
    - handleChangeCurrentPlayer is called with currentPlayerIndex
    - view.renderDrawPile is called with canDraw and isDrawPileEmpty
    - view.renderTurnControls is called with canPlaySelected and canEndTurn

- **TC30: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onExplodeButton_called_failed
  - **State of the system**: model.playExplode throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `onGodcatConfirm()`
- **TC31: Confirm called successfully** ( :white_check_mark: )
  - **Name of the test**: onGodcatConfirm_validCardType_success
  - **State of the system**: view.getSelectedGodcatCardType returns CardType.ATTACK
  - **Expected output**: onConfirmGodcatCard() is called with CardType.ATTACK

- **TC32: Caught exception** ( :white_check_mark: )
  - **Name of the test**: 
  - **State of the system**: view.geonGodcatConfirm_modelThrowsException_failedtSelectedGodcatCardType throws RuntimeException with message "An error occurred."
  - **Expected output**: onError is called with the exception message

### Method under test: `onConfirmGodcatCard(CardType cardType)`
- **TC33: Valid card type** ( :white_check_mark: )
  - **Name of the test**: onConfirmGodcatCard_validCardType_applyGodcatCalled
  - **State of the system**: CardType.ATTACK passed as cardType
  - **Expected output**: 
    - model.applyGodcat(CardType.ATTACK) is called
    - view.hideOverlay() is called

- **TC34: Invalid card type** ( :white_check_mark: )
  - **Name of the test**: onConfirmGodcatCard_modelThrowsException_failed
  - **State of the system**: CardType.EXPLODING_KITTEN passed as cardType; model.applyGodcat() throws exception
  - **Expected output**: onError is called with the exception message