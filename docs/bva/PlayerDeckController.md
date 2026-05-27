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
- **TC3: This method is executed successfully** ( :x: )
  - **Name of the test**: buildDependentUI_called_success
  - **State of the system**: currentPlayerHandIds = [], isFaceUp = true, canDraw = true, playerNames = [], currentPlayerIndex = 0, isGameOngoing = true
  - **Expected output**: called view.buildAndAddPlayerHandCards and view.buildAddRenderPlayerNameTags

- **TC4: Caught exception from model** ( :x: )
  - **Name of the test**: buildDependentUI_called_failed
  - **State of the system**: model.getCurrentPlayerHandIds throws RuntimeException "An error occurred.", isFaceUp = true, canDraw = true
  - **Expected output**: onError accepts exception

## Method under test: `bindUI()`
- **TC5: This method is called** ( :x: )
  - **Name of the test**: bindUI_called_success
  - **State of the system**: N/A
  - **Expected output**: called view.bindDrawPileButton, view.bindHandVisibilityButton, view.bindStartGameButton, view.bindNameTags, view.bindPlayerHandCardButtons

### Method under test: `onNameTag(int playerIndex)`
- **TC6: Player index is the same as current player index** ( :x: )
    - **Name of the test**: onNameTag_playerStaysTheSame_noChange
    - **State of the system**: playerIndex = 0, currentPlayerIndex = 0
    - **Expected output**: no model method is called

- **TC7: Player index is different from current player index** ( :x: )
    - **Name of the test**: onNameTag_playerChanges_success
    - **State of the system**: playerIndex = 1, currentPlayerIndex = 0
    - **Expected output**: called handleChangeCurrentPlayer(playerIndex)

- **TC8: Caught exception from model** ( :x: )
  - **Name of the test**: onNameTag_called_failed
  - **State of the system**: playerIndex = 0, currentPlayerIndex = 0, model.getCurrentPlayerIndex throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `handleChangeCurrentPlayer(int playerIndex)`
- **TC9: This method is executed successfully** ( :x: )
    - **Name of the test**: handleChangeCurrentPlayer_playerChanges_success
    - **State of the system**: playerIndex = 0, currentPlayerIndex = 0, isGameOngoing = true, isFaceUp = true
    - **Expected output**: called model.changeCurrentPlayerIndexAndSetFaceUpToFalse(playerIndex), view.renderPlayerNameTags, view.renderHandVisibilityButton, and buildAddBindPlayerHandCards

- **TC10: Caught exception from model** ( :x: )
  - **Name of the test**: handleChangeCurrentPlayer_playerChanges_failed
  - **State of the system**: playerIndex = 0, model.changeCurrentPlayer throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `buildAddBindPlayerHandCards()`
- **TC11: This method is called** ( :x: )
  - **Name of the test**: buildAddBindPlayerHandCards_called_success
  - **State of the system**: currentPlayerHandIds = [], isFaceUp = true, canDraw = true
  - **Expected output**: called view.buildAndAddPlayerHandCards and bindPlayerHandCardButtons

### Method under test: `onDrawPile()`
- **TC12: This method is executed successfully** ( :x: )
    - **Name of the test**: onDrawPile_drawsCard_success
    - **State of the system**: canDraw = true, isDrawPileEmpty = true, canPlaySelected = true, canEndTurn = true
    - **Expected output**: called model.drawFromPile, view.renderDrawPile, buildAddBindPlayerHandCards, and view.renderTurnControlSection

- **TC13: Caught exception from model** ( :x: )
  - **Name of the test**: onDrawPile_drawsCard_failed
  - **State of the system**: model.drawFromPile throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `onHandVisibilityButton()`
- **TC14: This method is executed successfully** ( :white_check_mark: )
    - **Name of the test**: onHandVisibilityButton_called_success
    - **State of the system**: isFaceUp = true
    - **Expected output**: called model.setIsFaceUpToOpposite, view.renderHandVisibilityButton, and buildAddBindPlayerHandCards

- **TC15: Caught exception from model** ( :x: )
  - **Name of the test**: onHandVisibilityButton_called_failed
  - **State of the system**: model.setIsFaceUpToOpposite throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

### Method under test: `onPlayerHandCardButton(int handCardIndex)`
- **TC16: Cards are face up** ( :x: )
  - **Name of the test**: onPlayerHandCardButton_cardsFaceUp_success
  - **State of the system**: handCardIndex = 0, isFaceUp = true, canPlaySelected = true, canEndTurn = false
  - **Expected output**: called model.setIsSelectedOfCurrentPlayerHandCardAtIndexToOpposite and view.renderTurnControlSection

- **TC17: Cards are face down** ( :x: )
    - **Name of the test**: onPlayerHandCardButton_cardsFaceDown_callsOnHandVisibility
    - **State of the system**: handCardIndex = 0, isFaceUp = false
    - **Expected output**: called onHandVisibilityButton

- **TC18: Caught exception from model** ( :x: )
  - **Name of the test**: onPlayerHandCardButton_called_failed
  - **State of the system**: model.getIsFaceUp throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception


### Method under test: `onStartGameButton()`
- **TC19: game starts successfully** ( :x: )
  - **Name of the test**: onStartGameButton_called_success
  - **State of the system**: startingPlayerIndex = 0, canDraw = true, isDrawPileEmpty = true, isGameOngoing = true, canPlaySelected = true, canEndTurn = true
  - **Expected output**: called model.startGame, handleChangeCurrentPlayer, view.renderDrawPile, and view.buildAndRenderTurnControlSection

- **TC20: Caught exception from model** ( :x: )
  - **Name of the test**: onStartGameButton_called_failed
  - **State of the system**: model.startGame throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception