### Method under test: `onNameTag(int playerIndex)`
- **TC1: player index is the same as current player index** ( :x: )
    - **Name of the test**:
    - **State of the system**: playerIndex = 0, currentPlayerIndex = 0
    - **Expected output**: no model method is called

- **TC2: player index is different from current player index** ( :x: )
    - **Name of the test**:
    - **State of the system**: playerIndex = 0, currentPlayerIndex = 1
    - **Expected output**: called handleChangeCurrentPlayer(playerIndex)

- **TC3: model throws InvalidStateException** ( :x: )
    - **Name of the test**:
    - **State of the system**: playerIndex = 0, currentPlayerIndex = 0
    - **Expected output**: called handleError()

### Method under test: `handleChangeCurrentPlayer(int playerIndex)`
- **TC4: this method is called** ( :x: )
    - **Name of the test**:
    - **State of the system**: playerIndex = 0
    - **Expected output**: called model.changeCurrentPlayerIndexAndSetFaceUpToFalse(playerIndex)

### Method under test: `onDrawPile()`
- **TC5: this method is called** ( :x: )
    - **Name of the test**:
    - **State of the system**: N/A
    - **Expected output**: called model.drawFromPile()

### Method under test: `onHandVisibilityButton()`
- **TC6: this method is called** ( :x: )
    - **Name of the test**:
    - **State of the system**: N/A
    - **Expected output**: called model.setIsFaceUpToOpposite()

### Method under test: `onPlayerHandCardButton(int handCardIndex)`
- **TC7: cards are face down** ( :x: )
    - **Name of the test**:
    - **State of the system**: isFaceUp = false
    - **Expected output**: called onFaceDownPlayerHandCardButton()

- **TC8: cards are face up** ( :x: )
    - **Name of the test**:
    - **State of the system**: isFaceUp = true
    - **Expected output**: called onFaceUpPlayerHandCardButton(handCardIndex)

### Method under test: `onFaceDownPlayerHandCardButton()`
- **TC9: this method is called** ( :x: )
    - **Name of the test**:
    - **State of the system**: N/A
    - **Expected output**: called onHandVisibilityButton()

### Method under test: `onFaceUpPlayerHandCardButton(int handCardIndex)`
- **TC10: model throws InvalidStateException** ( :x: )
    - **Name of the test**:
    - **State of the system**: handCardIndex = 0
    - **Expected output**: caught exception from model.setIsSelectedOfCurrentPlayerHandCardAtIndexToOpposite(handCardIndex), called handleError()

- **TC11: card from current player hand at index is initially selected** ( :x: )
    - **Name of the test**:
    - **State of the system**: handCardIndex = 0
    - **Expected output**: called model.setIsSelectedOfCurrentPlayerHandCardAtIndexToOpposite(handCardIndex)

### Method under test: `onStartGameButton()`
- **TC12: model throws InvalidStateException** ( :x: )
    - **Name of the test**:
    - **State of the system**: N/A
    - **Expected output**: caught exception from model.startGame(), called handleError()

- **TC13: game starts successfully** ( :x: )
    - **Name of the test**:
    - **State of the system**: startingPlayerIndex = 0
    - **Expected output**: called handleChangeCurrentPlayer(startingPlayerIndex)

### Method under test: `handleError()`
- **TC14: this method is called** ( :x: )
    - **Name of the test**:
    - **State of the system**: N/A
    - **Expected output**: called sceneManager.showErrorView()