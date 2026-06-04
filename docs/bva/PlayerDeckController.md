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

- **TC7: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onNameTag_called_failed
  - **State of the system**: 
    - playerIndex = 0
    - currentPlayerIndex = 0
    - model.getCurrentPlayerIndex throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

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

- **TC11: Draw Exploding Kitten, has Defuse** ( :x: )
  - **Name of the test**: onDrawPile_drawExplodingCardHasDefuse_buildExplodeOverlay
  - **State of the system**:
    - hasDefuse = true
  - **Expected output**:
    - model.drawFromPile = EXPLODINGKITTEN_1
    - view.bindDefuseButton is called
    - view.buildExplodeOverlay with hasDefuse, model.drawFromPile, and model.getDrawPileSize is called

- **TC12: Draw Exploding Kitten, no Defuse** ( :x: )
  - **Name of the test**: onDrawPile_drawExplodingCardNoDefuse_buildExplodeOverlay
  - **State of the system**:
    - hasDefuse = false
  - **Expected output**:
    - model.drawFromPile = EXPLODINGKITTEN_1
    - view.bindExplodeButton is called
    - view.buildExplodeOverlay with hasDefuse, model.drawFromPile, and model.getDrawPileSize is called

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
- **TC21: Cards play successfully** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_called_success
  - **State of the system**: 
    - canDrawFromDiscard = true
    - topDiscardId = "SKIP_1"
    - canPlaySelected = true
    - canEndTurn = true
  - **Expected output**: 
    - model.playSelectedCards is called
    - view.renderDiscardPile with model.canDrawFromDiscard is called
    - model.getTopDiscardId is called
    - view.renderDrawPile is called
    - rebindHandCards is called
    - view.renderTurnControlSection with model.canPlaySelected is called
    - model.canEndTurn is called

- **TC22: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_called_failed
  - **State of the system**: model.playSelectedCards throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `onEndTurnButton()`
- **TC23: Turn ends successfully** ( :white_check_mark: )
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

- **TC24: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onEndTurnButton_called_failed
  - **State of the system**: model.advanceTurn throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `onDefuseButton()`
- **TC25: Defuse Exploding Kitten successfully** ( :white_check_mark: )
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

- **TC26: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onDefuseButton_called_failed
  - **State of the system**: model.playDefuse with view.getExplodingKittenInsertIndex throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `onExplodeButton()`
- **TC27: Explode successfully** ( :white_check_mark: )
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

- **TC28: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onExplodeButton_called_failed
  - **State of the system**: model.playExplode throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception