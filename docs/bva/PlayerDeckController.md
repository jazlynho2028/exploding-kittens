## Method under test: `buildPlayerDeckScene()`
- **TC1: This method is called** ( :white_check_mark: )
  - **Name of the test**: buildPlayerDeckScene_called_success
  - **State of the system**: N/A
  - **Expected output**: called buildDependenUI, bindUI, returns view.createPlayerDeckScene

- **TC2: Caught exception** ( :white_check_mark: )
  - **Name of the test**: buildPlayerDeckScene_called_failed
  - **State of the system**: buildDependentUI throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `buildDependentUI()`
- **TC3: This method is executed successfully** ( :white_check_mark: )
  - **Name of the test**: buildDependentUI_called_success
  - **State of the system**: currentPlayerHandIds = [], isFaceUp = true, canDraw = true, playerNames = [], currentPlayerIndex = 0, isGameOngoing = true
  - **Expected output**: called view.buildAndAddPlayerHandCards and view.buildAddRenderPlayerNameTags

## Method under test: `bindUI()`
- **TC4: This method is called** ( :white_check_mark: )
  - **Name of the test**: bindUI_called_success
  - **State of the system**: N/A
  - **Expected output**: called view.bindDrawPileButton, view.bindHandVisibilityButton, view.bindStartGameButton, view.bindNameTags, view.bindPlayerHandCardButtons

### Method under test: `onNameTag(int playerIndex)`
- **TC5: Player index is the same as current player index** ( :white_check_mark: )
    - **Name of the test**: onNameTag_playerStaysTheSame_noChange
    - **State of the system**: playerIndex = 0, currentPlayerIndex = 0
    - **Expected output**: no model method is called

- **TC6: Player index is different from current player index** ( :white_check_mark: )
    - **Name of the test**: onNameTag_playerChanges_success
    - **State of the system**: playerIndex = 1, currentPlayerIndex = 0
    - **Expected output**: called handleChangeCurrentPlayer

- **TC7: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onNameTag_called_failed
  - **State of the system**: playerIndex = 0, currentPlayerIndex = 0, model.getCurrentPlayerIndex throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `handleChangeCurrentPlayer(int playerIndex)`
- **TC8: This method is executed successfully** ( :white_check_mark: )
    - **Name of the test**: handleChangeCurrentPlayer_playerChanges_success
    - **State of the system**: playerIndex = 0, currentPlayerIndex = 0, isGameOngoing = true, isFaceUp = true
    - **Expected output**: called model.changeCurrentPlayerIndex, model.setIsFaceUpToFalse, view.renderPlayerNameTags, view.renderHandVisibilityButton, and rebuildHandCards

### Method under test: `rebuildHandCards()`
- **TC9: This method is called** ( :white_check_mark: )
  - **Name of the test**: rebuildHandCards_called_success
  - **State of the system**: currentPlayerHandIds = [], isFaceUp = true, canDraw = true
  - **Expected output**: called view.buildAndAddPlayerHandCards and view.bindPlayerHandCardButtons

### Method under test: `onDrawPile()`
- **TC10: This method is executed successfully** ( :white_check_mark: )
    - **Name of the test**: onDrawPile_drawsCard_success
    - **State of the system**: canDraw = true, isDrawPileEmpty = true, canPlaySelected = true, canEndTurn = true
    - **Expected output**: called model.drawFromPile = CardType.DEFUSE, view.renderDrawPile, rebuildHandCards, and view.renderTurnControlSection

- **TC11: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onDrawPile_drawsCard_failed
  - **State of the system**: model.drawFromPile throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `onHandVisibilityButton()`
- **TC12: This method is executed successfully** ( :white_check_mark: )
    - **Name of the test**: onHandVisibilityButton_called_success
    - **State of the system**: isFaceUp = true
    - **Expected output**: called model.setIsFaceUpToOpposite, view.renderHandVisibilityButton, and rebuildHandCards

- **TC13: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onHandVisibilityButton_called_failed
  - **State of the system**: model.setIsFaceUpToOpposite throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `onPlayerHandCardButton(int handCardIndex)`
- **TC14: Cards are face up** ( :white_check_mark: )
  - **Name of the test**: onPlayerHandCardButton_cardsFaceUp_success
  - **State of the system**: handCardIndex = 0, isFaceUp = true, canPlaySelected = true, canEndTurn = false
  - **Expected output**: called model.setIsSelectedOfCurrentPlayerHandCardAtIndexToOpposite and view.renderTurnControlSection

- **TC15: Cards are face down** ( :white_check_mark: )
    - **Name of the test**: onPlayerHandCardButton_cardsFaceDown_callsOnHandVisibility
    - **State of the system**: handCardIndex = 0, isFaceUp = false
    - **Expected output**: called onHandVisibilityButton

- **TC16: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onPlayerHandCardButton_called_failed
  - **State of the system**: model.getIsFaceUp throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `onStartGameButton()`
- **TC17: Game starts successfully** ( :white_check_mark: )
  - **Name of the test**: onStartGameButton_called_success
  - **State of the system**: startingPlayerIndex = 0, canDraw = true, isDrawPileEmpty = true, isGameOngoing = true, canPlaySelected = true, canEndTurn = true
  - **Expected output**: called model.startGame, handleChangeCurrentPlayer, view.renderDrawPile, and view.buildAndRenderTurnControlSection

- **TC18: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onStartGameButton_called_failed
  - **State of the system**: model.startGame throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `onPlayCardsButton()`
- **TC19: Cards play successfully** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_called_success
  - **State of the system**: canDrawFromDiscard = true, topDiscardId = "SKIP_1", canPlaySelected = true, canEndTurn = true
  - **Expected output**: called model.playSelectedCards, view.renderDiscardPile with model.canDrawFromDiscard and model.getTopDiscardId, view.renderDrawPile, rebindHandCards, and view.renderTurnControlSection with model.canPlaySelected and model.canEndTurn

- **TC20: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_called_failed
  - **State of the system**: model.playSelectedCards throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception