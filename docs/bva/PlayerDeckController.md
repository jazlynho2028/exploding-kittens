### Method under test: `onNameTag(int playerIndex)`
- **TC1: player index is the same as current player index** ( :white-check-mark: )
    - **Name of the test**: onNameTag_playerStaysTheSame_noChange
    - **State of the system**: playerIndex = 0, currentPlayerIndex = 0
    - **Expected output**: no model method is called

- **TC2: player index is different from current player index** ( :white-check-mark: )
    - **Name of the test**: onNameTag_playerChanges_success
    - **State of the system**: playerIndex = 0, currentPlayerIndex = 1
    - **Expected output**: called handleChangeCurrentPlayer(playerIndex)

### Method under test: `handleChangeCurrentPlayer(int playerIndex)`
- **TC3: current player changes successfully** ( :white-check-mark: )
    - **Name of the test**: handleChangeCurrentPlayer_playerChanges_success
    - **State of the system**: playerIndex = 0
    - **Expected output**: called model.changeCurrentPlayerIndexAndSetFaceUpToFalse(playerIndex), called buildAddBindPlayerHandCards()

- **TC4: model throws InvalidStateException** ( :white-check-mark: )
  - **Name of the test**: handleChangeCurrentPlayer_playerChanges_fail
  - **State of the system**: playerIndex = 0
  - **Expected output**: caught exception from model.changeCurrentPlayerIndexAndSetFaceUpToFalse(playerIndex), called onError.accept("Failed to change current player.")

### Method under test: `onDrawPile()`
- **TC5: card is drawn from pile successfully** ( :white-check-mark: )
    - **Name of the test**: onDrawPile_drawsCard_success
    - **State of the system**: N/A
    - **Expected output**: called model.drawFromPile(), called buildAddBindPlayerHandCards()

- **TC6: model throws InvalidStateException** ( :white-check-mark: )
  - **Name of the test**: onDrawPile_drawsCard_fail()
  - **State of the system**: N/A
  - **Expected output**: caught exception from model.drawFromPile(), , called onError.accept("Failed to draw from pile.")

### Method under test: `onHandVisibilityButton()`
- **TC7: this method is called** ( :white-check-mark: )
    - **Name of the test**: onHandVisibilityButton_called_success
    - **State of the system**: N/A
    - **Expected output**: called model.setIsFaceUpToOpposite(), called buildAddBindPlayerHandCards()

### Method under test: `onPlayerHandCardButton(int handCardIndex)`
- **TC8: cards are face down** ( :white-check-mark: )
    - **Name of the test**: onPlayerHandCardButton_cardsFaceDown_callsOnHandVisibility
    - **State of the system**: handCardIndex = 0, isFaceUp = false
    - **Expected output**: called onHandVisibilityButton()

- **TC9: cards are face up** ( :white-check-mark: )
    - **Name of the test**: onPlayerHandCardButton_cardsFaceUp_callsModelMethod
    - **State of the system**: handCardIndex = 0, isFaceUp = true
    - **Expected output**: called model.setIsSelectedOfCurrentPlayerHandCardAtIndexToOpposite(handCardIndex)

### Method under test: `onStartGameButton()`
- **TC10: game starts successfully** ( :white-check-mark: )
  - **Name of the test**: onStartGameButton_called_success
  - **State of the system**: startingPlayerIndex = 0
  - **Expected output**: called model.startGame(), handleChangeCurrentPlayer(startingPlayerIndex)

- **TC11: model throws InvalidStateException** ( :x: )
    - **Name of the test**:
    - **State of the system**: N/A
    - **Expected output**: caught exception from model.startGame(), called onError.accept("Failed to start game.")

### Method under test: `handleError()`
- **TC12: this method is called** ( :x: )
    - **Name of the test**:
    - **State of the system**: N/A
    - **Expected output**: called screenSwitcher.showErrorView()