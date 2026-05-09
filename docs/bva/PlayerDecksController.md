### Method under test: `onNameTag(int playerIndex)`
- **TC1: player index is the same as current player index** ( :white-check-mark: )
    - **Name of the test**: OnNameTag_playerStaysTheSame_NoChange
    - **State of the system**: playerIndex = 0, currentPlayerIndex = 0
    - **Expected output**: no model method is called

- **TC2: player index is different from current player index** ( :x: )
    - **Name of the test**:
    - **State of the system**: playerIndex = 0, currentPlayerIndex = 1
    - **Expected output**: called handleChangeCurrentPlayer(playerIndex)

### Method under test: `handleChangeCurrentPlayer(int playerIndex)`
- **TC3: current player changes successfully** ( :x: )
    - **Name of the test**:
    - **State of the system**: playerIndex = 0
    - **Expected output**: called model.changeCurrentPlayerIndexAndSetFaceUpToFalse(playerIndex)

- **TC4: model throws InvalidStateException** ( :x: )
  - **Name of the test**:
  - **State of the system**: playerIndex = 0
  - **Expected output**: caught exception from model.changeCurrentPlayerIndexAndSetFaceUpToFalse(playerIndex), called handleError()

### Method under test: `onDrawPile()`
- **TC5: card is drawn from pile successfully** ( :x: )
    - **Name of the test**:
    - **State of the system**: N/A
    - **Expected output**: called model.drawFromPile()

- **TC6: model throws InvalidStateException** ( :x: )
  - **Name of the test**:
  - **State of the system**: N/A
  - **Expected output**: caught exception from model.drawFromPile()

### Method under test: `onHandVisibilityButton()`
- **TC7: this method is called** ( :x: )
    - **Name of the test**:
    - **State of the system**: N/A
    - **Expected output**: called model.setIsFaceUpToOpposite()

### Method under test: `onPlayerHandCardButton(int handCardIndex)`
- **TC8: cards are face down** ( :x: )
    - **Name of the test**:
    - **State of the system**: isFaceUp = false
    - **Expected output**: called onFaceDownPlayerHandCardButton()

- **TC9: cards are face up** ( :x: )
    - **Name of the test**:
    - **State of the system**: isFaceUp = true
    - **Expected output**: called onFaceUpPlayerHandCardButton(handCardIndex)

### Method under test: `onFaceDownPlayerHandCardButton()`
- **TC10: this method is called** ( :x: )
    - **Name of the test**:
    - **State of the system**: N/A
    - **Expected output**: called onHandVisibilityButton()

### Method under test: `onFaceUpPlayerHandCardButton(int handCardIndex)`
- **TC11: player selects card successfully** ( :x: )
  - **Name of the test**:
  - **State of the system**: handCardIndex = 0
  - **Expected output**: called model.setIsSelectedOfCurrentPlayerHandCardAtIndexToOpposite(handCardIndex)

- **TC12: model throws InvalidStateException** ( :x: )
    - **Name of the test**:
    - **State of the system**: handCardIndex = 0
    - **Expected output**: caught exception from model.setIsSelectedOfCurrentPlayerHandCardAtIndexToOpposite(handCardIndex), called handleError()

### Method under test: `onStartGameButton()`
- **TC13: game starts successfully** ( :x: )
  - **Name of the test**:
  - **State of the system**: startingPlayerIndex = 0
  - **Expected output**: called model.startGame(), handleChangeCurrentPlayer(startingPlayerIndex)

- **TC14: model throws InvalidStateException** ( :x: )
    - **Name of the test**:
    - **State of the system**: N/A
    - **Expected output**: caught exception from model.startGame(), called handleError()

### Method under test: `handleError()`
- **TC15: this method is called** ( :x: )
    - **Name of the test**:
    - **State of the system**: N/A
    - **Expected output**: called screenSwitcher.showErrorView()