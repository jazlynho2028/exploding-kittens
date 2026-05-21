### Method under test: `buildAndBindUI()`
- **TC1: this method is called** ( :white-check-mark: )
  - **Name of the test**: buildAndBindUI_called_success
  - **State of the system**: N/A
  - **Expected output**: called view.buildAndAddPlayerHandCards, view.buildAddRenderPlayerNameTags, and bindUI

### Method under test: `onNameTag(int playerIndex)`
- **TC2: player index is the same as current player index** ( :white-check-mark: )
    - **Name of the test**: onNameTag_playerStaysTheSame_noChange
    - **State of the system**: playerIndex = 0, currentPlayerIndex = 0
    - **Expected output**: no model method is called

- **TC3: player index is different from current player index** ( :white-check-mark: )
    - **Name of the test**: onNameTag_playerChanges_success
    - **State of the system**: playerIndex = 0, currentPlayerIndex = 1
    - **Expected output**: called handleChangeCurrentPlayer(playerIndex)

### Method under test: `handleChangeCurrentPlayer(int playerIndex)`
- **TC4: current player changes successfully** ( :white-check-mark: )
    - **Name of the test**: handleChangeCurrentPlayer_playerChanges_success
    - **State of the system**: playerIndex = 0
    - **Expected output**: called model.changeCurrentPlayerIndexAndSetFaceUpToFalse(playerIndex), view.renderPlayerNameTags, view.renderHandVisibilityButton, and buildAddBindPlayerHandCards()

- **TC5: model throws InvalidStateException** ( :white-check-mark: )
  - **Name of the test**: handleChangeCurrentPlayer_playerChanges_fail
  - **State of the system**: playerIndex = 0
  - **Expected output**: caught exception from model.changeCurrentPlayerIndexAndSetFaceUpToFalse(playerIndex), called onError.accept("Failed to change current player.")

### Method under test: `buildAddBindPlayerHandCards()`
- **TC6: this method is called** ( :white-check-mark: )
  - **Name of the test**: buildAddBindPlayerHandCards_called_success
  - **State of the system**: N/A
  - **Expected output**: called view.buildAndAddPlayerHandCards and bindPlayerHandCardButtons

### Method under test: `onDrawPile()`
- **TC7: card is drawn from pile successfully** ( :x: )
    - **Name of the test**: onDrawPile_drawsCard_success
    - **State of the system**: N/A
    - **Expected output**: called model.drawFromPile(), view.renderDrawPile, buildAddBindPlayerHandCards(), and view.renderTurnControlSection

- **TC8: model throws InvalidStateException** ( :white-check-mark: )
  - **Name of the test**: onDrawPile_drawsCard_fail()
  - **State of the system**: N/A
  - **Expected output**: caught exception from model.drawFromPile(), , called onError.accept("Failed to draw from pile.")

### Method under test: `onHandVisibilityButton()`
- **TC9: this method is called** ( :x: )
    - **Name of the test**: onHandVisibilityButton_called_success
    - **State of the system**: N/A
    - **Expected output**: called model.setIsFaceUpToOpposite(), view.renderHandVisibilityButton, and buildAddBindPlayerHandCards()

### Method under test: `onPlayerHandCardButton(int handCardIndex)`
- **TC10: cards are face down** ( :white-check-mark: )
    - **Name of the test**: onPlayerHandCardButton_cardsFaceDown_callsOnHandVisibility
    - **State of the system**: handCardIndex = 0, isFaceUp = false
    - **Expected output**: called onHandVisibilityButton()

- **TC11: cards are face up** ( :x: )
    - **Name of the test**: onPlayerHandCardButton_cardsFaceUp_callsModelMethod
    - **State of the system**: handCardIndex = 0, isFaceUp = true
    - **Expected output**: called model.setIsSelectedOfCurrentPlayerHandCardAtIndexToOpposite(handCardIndex) and view.renderTurnControlSection

### Method under test: `onStartGameButton()`
- **TC12: game starts successfully** ( :x: )
  - **Name of the test**: onStartGameButton_called_success
  - **State of the system**: startingPlayerIndex = 0
  - **Expected output**: called model.startGame(), handleChangeCurrentPlayer(startingPlayerIndex), view.renderDrawPile, and view.buildAndRenderTurnControlSection

- **TC13: model throws InvalidStateException** ( :white-check-mark: )
    - **Name of the test**: onStartGameButton_called_fail
    - **State of the system**: N/A
    - **Expected output**: caught exception from model.startGame(), called onError.accept("Failed to start game.")

### Method under test: `getPlayerDeckScene()`
- **TC14: this method is called ** ( :x: )
  - **Name of the test**: 
  - **State of the system**: N/A
  - **Expected output**: called view.createPlayerDeckScene()