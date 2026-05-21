### Method under test: `buildAndBindUI()`
- **TC1: this method is called** ( :x: )
  - **Name of the test**: 
  - **State of the system**: 
  - **Expected output**: called view.buildAndAddPlayerHandCards, view.buildAddRenderPlayerNameTags, and bindUI

### Method under test: `bindUI()`
- **TC2: this method is called** ( :x: )
  - **Name of the test**:
  - **State of the system**:
  - **Expected output**: called bindNameTags and bindPlayerHandCardButtons

### Method under test: `onNameTag(int playerIndex)`
- **TC3: player index is the same as current player index** ( :white-check-mark: )
    - **Name of the test**: onNameTag_playerStaysTheSame_noChange
    - **State of the system**: playerIndex = 0, currentPlayerIndex = 0
    - **Expected output**: no model method is called

- **TC4: player index is different from current player index** ( :white-check-mark: )
    - **Name of the test**: onNameTag_playerChanges_success
    - **State of the system**: playerIndex = 0, currentPlayerIndex = 1
    - **Expected output**: called handleChangeCurrentPlayer(playerIndex)

### Method under test: `handleChangeCurrentPlayer(int playerIndex)`
- **TC5: current player changes successfully** ( :x: )
    - **Name of the test**: handleChangeCurrentPlayer_playerChanges_success
    - **State of the system**: playerIndex = 0
    - **Expected output**: called model.changeCurrentPlayerIndexAndSetFaceUpToFalse(playerIndex), view.renderPlayerNameTags, view.renderHandVisibilityButton, and buildAddBindPlayerHandCards()

- **TC6: model throws InvalidStateException** ( :white-check-mark: )
  - **Name of the test**: handleChangeCurrentPlayer_playerChanges_fail
  - **State of the system**: playerIndex = 0
  - **Expected output**: caught exception from model.changeCurrentPlayerIndexAndSetFaceUpToFalse(playerIndex), called onError.accept("Failed to change current player.")

### Method under test: `buildAddBindPlayerHandCards()`
- **TC7: this method is called** ( :x: )
  - **Name of the test**:
  - **State of the system**: N/A
  - **Expected output**: called view.buildAndAddPlayerHandCards and bindPlayerHandCardButtons

### Method under test: `onDrawPile()`
- **TC8: card is drawn from pile successfully** ( :x: )
    - **Name of the test**: onDrawPile_drawsCard_success
    - **State of the system**: N/A
    - **Expected output**: called model.drawFromPile(), view.renderDrawPile, buildAddBindPlayerHandCards(), and view.renderTurnControlSection

- **TC9: model throws InvalidStateException** ( :white-check-mark: )
  - **Name of the test**: onDrawPile_drawsCard_fail()
  - **State of the system**: N/A
  - **Expected output**: caught exception from model.drawFromPile(), , called onError.accept("Failed to draw from pile.")

### Method under test: `onHandVisibilityButton()`
- **TC10: this method is called** ( :x: )
    - **Name of the test**: onHandVisibilityButton_called_success
    - **State of the system**: N/A
    - **Expected output**: called model.setIsFaceUpToOpposite(), view.renderHandVisibilityButton, and buildAddBindPlayerHandCards()

### Method under test: `onPlayerHandCardButton(int handCardIndex)`
- **TC11: cards are face down** ( :white-check-mark: )
    - **Name of the test**: onPlayerHandCardButton_cardsFaceDown_callsOnHandVisibility
    - **State of the system**: handCardIndex = 0, isFaceUp = false
    - **Expected output**: called onHandVisibilityButton()

- **TC12: cards are face up** ( :x: )
    - **Name of the test**: onPlayerHandCardButton_cardsFaceUp_callsModelMethod
    - **State of the system**: handCardIndex = 0, isFaceUp = true
    - **Expected output**: called model.setIsSelectedOfCurrentPlayerHandCardAtIndexToOpposite(handCardIndex) and view.renderTurnControlSection

### Method under test: `onStartGameButton()`
- **TC13: game starts successfully** ( :x: )
  - **Name of the test**: onStartGameButton_called_success
  - **State of the system**: startingPlayerIndex = 0
  - **Expected output**: called model.startGame(), handleChangeCurrentPlayer(startingPlayerIndex), view.renderDrawPile, and view.buildAndRenderTurnControlSection

- **TC14: model throws InvalidStateException** ( :white-check-mark: )
    - **Name of the test**: onStartGameButton_called_fail
    - **State of the system**: N/A
    - **Expected output**: caught exception from model.startGame(), called onError.accept("Failed to start game.")

### Method under test: `getPlayerDeckScene()`
- **TC3: this method is called ** ( :x: )
  - **Name of the test**: 
  - **State of the system**: N/A
  - **Expected output**: called view.createPlayerDeckScene()