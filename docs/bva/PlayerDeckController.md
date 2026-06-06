# BVA Analysis: PlayerDeckController Class

## Method under test: `buildPlayerDeckScene()`
- **TC1: This method is called** ( :white_check_mark: )
  - **Name of the test**: buildPlayerDeckScene_called_success
  - **State of the system**: N/A
  - **Expected output**:
    - buildDependentUI is called
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
    - isFaceUp = true
  - **Expected output**:
    - expectRebuildHandCards is satisfied with isFaceUp
    - expectRebuildNameTags is satisfied

## Method under test: `bindUI()`
- **TC4: This method is called** ( :white_check_mark: )
  - **Name of the test**: bindUI_called_success
  - **State of the system**: N/A
  - **Expected output**:
    - view.bindDrawPileButton is called
    - view.bindHandVisibilityButton is called
    - view.bindStartGameButton is called
    - view.bindPlayCardsButton is called
    - view.bindEndTurnButton is called
    - view.bindNameTags is called
    - view.bindPlayerHandCardButtons is called

## Method under test: `onNameTag(int playerIndex)`
- **TC5: Player index is the same as current player index** ( :white_check_mark: )
  - **Name of the test**: onNameTag_playerStaysTheSame_noChange
  - **State of the system**:
    - playerIndex = 0
    - currentPlayerIndex = 0
  - **Expected output**:
    - getCurrentPlayerIndexExpectation is satisfied
    - handleChangeCurrentPlayer is not called

- **TC6: Player index is different from current player index** ( :white_check_mark: )
  - **Name of the test**: onNameTag_playerChanges_success
  - **State of the system**:
    - playerIndex = 1
    - currentPlayerIndex = 0
  - **Expected output**:
    - getCurrentPlayerIndexExpectation is satisfied
    - handleChangeCurrentPlayer is called with playerIndex

- **TC7: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onNameTag_called_failed
  - **State of the system**: model.getCurrentPlayerIndex throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `handleChangeCurrentPlayer(int playerIndex)`
- **TC8: This method is executed successfully** ( :white_check_mark: )
  - **Name of the test**: handleChangeCurrentPlayer_playerChanges_success
  - **State of the system**:
    - playerIndex = 0
    - isFaceUp = true
  - **Expected output**:
    - model.changeCurrentPlayerIndex is called with playerIndex
    - model.setFaceUpToFalse is called
    - expectUpdateNameTags is satisfied
    - view.renderHandVisibilityButton is called with isFaceUp
    - rebindHandCards is called

## Method under test: `rebindHandCards()`
- **TC9: This method is called** ( :white_check_mark: )
  - **Name of the test**: rebindHandCards_called_success
  - **State of the system**:
    - isFaceUp = true
  - **Expected output**:
    - expectRebindHandCards is satisfied with isFaceUp

## Method under test: `onDrawPile()`
- **TC10: Draw non-exploding card successfully** ( :white_check_mark: )
  - **Name of the test**: onDrawPile_drawNonExplodingCard_success
  - **State of the system**:
    - canEndTurn = true
    - drawnCard = DEFUSE_1
  - **Expected output**:
    - rebindHandCards is called
    - expectUpdateDrawPile is satisfied
    - expectUpdateTurnControls is satisfied with canEndTurn

- **TC11: Draw Exploding Kitten, has Defuse** ( :white_check_mark: )
  - **Name of the test**: onDrawPile_drawExplodingCard_buildExplodeOverlay
  - **State of the system**:
    - hasDefuse = true
    - drawnCard = EXPLODINGKITTEN_1
  - **Expected output**:
    - view.bindDefuseButton is called
    - view.buildExplodeOverlay is called with hasDefuse, drawnCardId, and drawPileSize - 1

- **TC12: Draw Exploding Kitten, no Defuse** ( :white_check_mark: )
  - **Name of the test**: onDrawPile_drawExplodingCard_buildExplodeOverlay
  - **State of the system**:
    - hasDefuse = false
    - drawnCard = EXPLODINGKITTEN_1
  - **Expected output**:
    - view.bindExplodeButton is called
    - view.buildExplodeOverlay is called with hasDefuse, drawnCardId, and drawPileSize - 1

- **TC13: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onDrawPile_drawsCard_failed
  - **State of the system**: model.drawFromPile throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `onHandVisibilityButton()`
- **TC14: This method is executed successfully** ( :white_check_mark: )
  - **Name of the test**: onHandVisibilityButton_called_success
  - **State of the system**:
    - isFaceUp = true
  - **Expected output**:
    - model.toggleFaceUp is called
    - view.renderHandVisibilityButton is called with isFaceUp
    - rebindHandCards is called

- **TC15: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onHandVisibilityButton_called_failed
  - **State of the system**: model.toggleFaceUp throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `onPlayerHandCardButton(int handCardIndex)`
- **TC16: Cards are face up** ( :white_check_mark: )
  - **Name of the test**: onPlayerHandCardButton_cardsFaceUp_success
  - **State of the system**:
    - handCardIndex = 0
    - isFaceUp = true
    - canEndTurn = false
  - **Expected output**:
    - model.toggleSelectedPlayerCardAt is called with handCardIndex
    - expectUpdateTurnControls is satisfied with canEndTurn

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

## Method under test: `onStartGameButton()`
- **TC19: Game starts successfully** ( :white_check_mark: )
  - **Name of the test**: onStartGameButton_called_success
  - **State of the system**:
    - startingPlayerIndex = 0
    - canEndTurn = true
  - **Expected output**:
    - model.startGame is called
    - handleChangeCurrentPlayer is called with startingPlayerIndex
    - expectUpdateDrawPile is satisfied
    - expectRebuildTurnControls is satisfied with canEndTurn

- **TC20: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onStartGameButton_called_failed
  - **State of the system**: model.startGame throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `onPlayCardsButton()`
- **TC21: Cards play successfully, no additional UI change** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_noAdditionalUIChange_success
  - **State of the system**:
    - canDrawFromDiscard = true
    - topDiscardId = "DOUBLEUP_1"
    - canEndTurn = true
    - selectedCards = [DOUBLE_UP]
  - **Expected output**:
    - model.playSelectedCards is called
    - expectUpdateDiscardPile is satisfied with canDrawFromDiscard and topDiscardId
    - rebindHandCards is called
    - expectUpdateTurnControls is satisfied with canEndTurn

- **TC22: Cards play successfully, Skip card** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_skipPlayed_updatedPlayer
  - **State of the system**:
    - canDrawFromDiscard = true
    - topDiscardId = "SKIP_1"
    - canEndTurn = true
    - selectedCards = [SKIP]
  - **Expected output**:
    - model.playSelectedCards is called
    - expectUpdateDiscardPile is satisfied with canDrawFromDiscard and topDiscardId
    - rebindHandCards is called
    - expectUpdateTurnControls is satisfied with canEndTurn
    - expectRenderNextTurn is satisfied with CURRENT_PLAYER_INDEX and canEndTurn

- **TC23: Cards play successfully, See The Future card** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_seeTheFuturePlayed_updatedPlayer
  - **State of the system**:
    - canDrawFromDiscard = true
    - topDiscardId = "SEETHEFUTURE_1"
    - canEndTurn = true
    - selectedCards = [SEE_THE_FUTURE]
  - **Expected output**:
    - model.playSelectedCards is called
    - expectUpdateDiscardPile is satisfied with canDrawFromDiscard and topDiscardId
    - rebindHandCards is called
    - expectUpdateTurnControls is satisfied with canEndTurn
    - view.buildSeeTheFutureOverlay is called with model.peekSeeTheFutureCardIds

- **TC24: Cards play successfully, Godcat card** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_godcatPlayed_overlayShown
  - **State of the system**:
    - canDrawFromDiscard = true
    - topDiscardId = "GODCAT_1"
    - canEndTurn = true
    - selectedCards = [GODCAT]
  - **Expected output**:
    - model.playSelectedCards is called
    - expectUpdateDiscardPile is satisfied with canDrawFromDiscard and topDiscardId
    - rebindHandCards is called
    - expectUpdateTurnControls is satisfied with canEndTurn
    - view.bindGodcatConfirmButton is called
    - view.buildGodcatOverlay is called with GameConstants.GODCAT_CARDTYPE_OPTIONS

- **TC25: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_called_failed
  - **State of the system**: model.playSelectedCards throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `onEndTurnButton()`
- **TC26: Turn ends successfully** ( :white_check_mark: )
  - **Name of the test**: onEndTurnButton_called_success
  - **State of the system**:
    - canEndTurn = true
  - **Expected output**:
    - model.advanceTurn is called
    - expectRenderNextTurn is satisfied with CURRENT_PLAYER_INDEX and canEndTurn

- **TC27: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onEndTurnButton_called_failed
  - **State of the system**: model.advanceTurn throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `onDefuseButton()`
- **TC28: Defuse Exploding Kitten successfully** ( :white_check_mark: )
  - **Name of the test**: onDefuseButton_called_success
  - **State of the system**:
    - explodingKittenInsertIndex = 0
    - canDrawFromDiscard = true
    - topDiscardId = "DEFUSE_1"
    - isFaceUp = true
    - canEndTurn = true
  - **Expected output**:
    - view.getExplodingKittenInsertIndex is called
    - model.playDefuse is called with explodingKittenInsertIndex
    - view.hideOverlay is called
    - expectUpdateDiscardPile is satisfied with canDrawFromDiscard and topDiscardId
    - expectRebindHandCards is satisfied with isFaceUp
    - expectRenderNextTurn is satisfied with CURRENT_PLAYER_INDEX and canEndTurn

- **TC29: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onDefuseButton_called_failed
  - **State of the system**: model.playDefuse throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `onExplodeButton()`
- **TC30: Explode successfully** ( :white_check_mark: )
  - **Name of the test**: onExplodeButton_called_success
  - **State of the system**:
    - canEndTurn = true
  - **Expected output**:
    - model.playExplode is called
    - view.hideOverlay is called
    - expectUpdateDrawPile is satisfied
    - expectRenderNextTurn is satisfied with CURRENT_PLAYER_INDEX and canEndTurn

- **TC31: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onExplodeButton_called_failed
  - **State of the system**: model.playExplode throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `onGodcatConfirm()`
- **TC32: Confirm called successfully** ( :white_check_mark: )
  - **Name of the test**: onGodcatConfirm_validCardType_success
  - **State of the system**: selectedGodcatCardType = ATTACK
  - **Expected output**: onConfirmGodcatCard is called with view.getSelectedGodcatCardType

- **TC33: Caught exception** ( :white_check_mark: )
  - **Name of the test**: onGodcatConfirm_modelThrowsException_failed
  - **State of the system**: view.getSelectedGodcatCardType throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `onConfirmGodcatCard(CardType cardType)`
- **TC34: Valid card type** ( :white_check_mark: )
  - **Name of the test**: onConfirmGodcatCard_validCardType_applyGodcatCalled
  - **State of the system**: cardType = ATTACK
  - **Expected output**:
    - model.applyGodcat is called with CardType.ATTACK
    - view.hideOverlay is called

- **TC35: Model throws on invalid card type** ( :white_check_mark: )
  - **Name of the test**: onConfirmGodcatCard_modelThrowsException_failed
  - **State of the system**: 
    - cardType = EXPLODING_KITTEN
    - model.applyGodcat throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception