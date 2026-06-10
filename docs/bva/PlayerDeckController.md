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
    - called handleChangeCurrentPlayer with playerIndex
    - calls updateTurnControls

- **TC7: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onNameTag_called_failed
  - **State of the system**: model.getCurrentPlayerIndex throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

- **TC8: pendingTargetAction is present** ( :white_check_mark: )
  - **Name of the test**: onNameTag_pendingTargetActionPresent_executesAction
  - **State of the system**:
    - pendingTargetAction = Optional.of(mockAction)
    - playerIndex = 1
    - initialPlayerIndex = 0
    - newPlayerIndex = 2
    - model.getIsGameOngoing() returns true
  - **Expected output**:
    - mockAction.accept(1) is called
    - pendingTargetAction becomes empty
    - view.renderPlayerNameTags(2, true, DEAD_INDICES) is called
    - updateTurnControls() is called

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
- **TC21: Cards play successfully, Godcat card** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_godcatPlayed_overlayShown
  - **State of the system**:
    - canDrawFromDiscard = true
    - playedCardId = "GODCAT_1"
    - canEndTurn = true
    - selectedCards = [GODCAT]
  - **Expected output**:
    - model.playSelectedCards is called
    - expectUpdateDiscardPile is satisfied with canDrawFromDiscard and topDiscardId
    - rebindHandCards is called
    - expectUpdateTurnControls is satisfied with canEndTurn
    - view.bindGodcatConfirmButton is called
    - view.buildGodcatOverlay is called with GameConstants.GODCAT_CARDTYPE_OPTIONS

- **TC22: Non-Godcat card plays successfully** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_skipPlayed_updateModel
  - **State of the system**:
    - canDrawFromDiscard = true
    - playedCardId = "SKIP_1"
    - canEndTurn = true
    - selectedCards = [SKIP_1]
  - **Expected output**:
    - model.playSelectedCards is called
    - expectUpdateDiscardPile is satisfied with canDrawFromDiscard and topDiscardId
    - rebindHandCards is called
    - expectUpdateTurnControls is satisfied with canEndTurn
    - updateByCardType is called with card

- **TC23: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_called_failed
  - **State of the system**: model.playSelectedCards throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `updateByCardType(CardType cardType)`
- **TC24: Cards play successfully, no additional UI change** ( :white_check_mark: )
  - **Name of the test**: updateByCardType_noAdditionalUIChange_success
  - **State of the system**: cardType =
    - ATTACK
    - SHUFFLE
    - CLONE
    - SWAP_TOP_AND_BOTTOM
    - DRAW_FROM_THE_BOTTOM
    - TARGETED_ATTACK
    - WINNER_WINNER_CATNIP_DINNER
    - RAGEBAIT
    - RECYCLE
    - DOUBLE_UP
    - MILD_SHUFFLE 
  - **Expected output**: N/A

- **TC25: UI updated successfully, Skip card** ( :white_check_mark: )
  - **Name of the test**: updateByCardType_skipOrSuperSkipPlayed_updateUI
  - **State of the system**: cardType = SKIP
  - **Expected output**:
    - expectRenderNextTurn is satisfied with CURRENT_PLAYER_INDEX and canEndTurn

- **TC26: UI updated successfully, See The Future card** ( :white_check_mark: )
  - **Name of the test**: updateByCardType_seeTheFuturePlayed_updateUI
  - **State of the system**: cardType = SEE_THE_FUTURE
  - **Expected output**:
    - view.buildSeeTheFutureOverlay is called with model.getSeeTheFutureCardIds

- **TC27: UI updated successfully, Catomic Bomb card** ( :white_check_mark: )
  - **Name of the test**: updateByCardType_catomicBombPlayed_updateUI
  - **State of the system**: cardType = CATOMIC_BOMB
  - **Expected output**:
      - expectRenderNextTurn is satisfied with CURRENT_PLAYER_INDEX and canEndTurn

- **TC28: Cards play successfully, Super Skip card** ( :white_check_mark: )
  - **Name of the test**: updateByCardType_skipOrSuperSkipPlayed_updateUI
  - **State of the system**:
    - canDrawFromDiscard = true
    - topDiscardId = "SUPER_SKIP_1"
    - canPlaySelected = true
    - canEndTurn = true
    - playSelectedCards returns CardType.SUPER_SKIP
    - getCurrentPlayerIndex returns 0
  - **Expected output**:
    - model.playSelectedCards is called
    - view.renderDiscardPile with model.canDrawFromDiscard and model.getTopDiscardId is called
    - rebindHandCards is called
    - view.renderTurnControlSection with model.canPlaySelected and model.canEndTurn is called
    - handleNewTurn is called with model.getCurrentPlayerIndex:
      - model.changeCurrentPlayerIndex is called
      - model.setFaceUpToFalse is called
      - view.renderPlayerNameTags is called
      - view.renderHandVisibilityButton is called
      - rebindHandCards is called
      - view.renderDrawPile is called
      - view.buildAndRenderTurnControlSection is called

- **TC29: Cards play successfully, Godcat card** ( :white_check_mark: )
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

- **TC24: Targeted Attack card played** ( :x: )
  - **Name of the test**: onPlayCardsButton_targetedAttackPlayed_targetSelectionEnabled
  - **State of the system**:
    - canDrawFromDiscard = true
    - topDiscardId = "TARGETED_ATTACK_1"
    - canPlaySelected = true
    - canEndTurn = true
    - currentPlayerIndex = 0
    - playSelectedCards returns CardType.TARGETED_ATTACK
  - **Expected output**:
    - pendingTargetAction becomes present
    - view.renderPlayerNameTags(0, false, DEAD_INDICES) is called
    - view.renderTurnControlSection(false, false) is called

- **TC25: Ragebait card played** ( :x: )
  - **Name of the test**: onPlayCardsButton_ragebaitPlayed_targetSelectionEnabled
  - **State of the system**:
    - canDrawFromDiscard = true
    - topDiscardId = "RAGEBAIT_1"
    - canPlaySelected = true
    - canEndTurn = true
    - currentPlayerIndex = 0
    - playSelectedCards returns CardType.RAGEBAIT
  - **Expected output**:
    - pendingTargetAction becomes present
    - view.renderPlayerNameTags(0, false, DEAD_INDICES) is called
    - view.renderTurnControlSection(false, false) is called

- **TC30: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_called_failed
  - **State of the system**: model.playSelectedCards throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `applyTargetedAttackAction`
- **TC26: applyTargetedAttackAction called** ( :x: )
  - **Name of the test**: applyTargetedAttackAction_called_appliesAttackAndChangesPlayer
  - **State of the system**: targetIndex = 1
  - **Expected output**:
    - model.applyTargetedAttack(1) is called
    - handleChangeCurrentPlayer(1) is called

## Method under test: `applyRagebaitAction()`
- **TC27: applyRagebaitAction called** ( :x: )
  - **Name of the test**: applyRagebaitAction_called_appliesRagebaitAndRebindsHand
  - **State of the system**: targetIndex = 1
  - **Expected output**:
    - model.applyRagebait(1) is called
    - rebindHandCards() is called

## Method under test: `onEndTurnButton()`
- **TC31: Turn ends successfully** ( :white_check_mark: )
  - **Name of the test**: onEndTurnButton_called_success
  - **State of the system**:
    - canEndTurn = true
  - **Expected output**:
    - model.advanceTurn is called
    - expectRenderNextTurn is satisfied with CURRENT_PLAYER_INDEX and canEndTurn

- **TC32: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onEndTurnButton_called_failed
  - **State of the system**: model.advanceTurn throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `onDefuseButton()`
- **TC33: Defuse Exploding Kitten successfully** ( :white_check_mark: )
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

- **TC34: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onDefuseButton_called_failed
  - **State of the system**: model.playDefuse throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `onExplodeButton()`
- **TC35: Explode successfully, game continues** ( :white_check_mark: )
  - **Name of the test**: onExplodeButton_gameOngoing_continueNextTurn
  - **State of the system**:
    - canEndTurn = true
    - model.getIsGamOngoing = true
  - **Expected output**:
    - model.playExplode is called
    - view.hideOverlay is called
    - expectUpdateDrawPile is satisfied
    - expectRenderNextTurn is satisfied with CURRENT_PLAYER_INDEX and canEndTurn

- **TC36: Explode successfully, game over** ( :white_check_mark: )
  - **Name of the test**: onExplodeButton_gameOver_showWinOverlay
  - **State of the system**:
    - canEndTurn = true
    - model.getIsGamOngoing = false
  - **Expected output**:
    - model.playExplode is called
    - view.hideOverlay is called
    - expectUpdateDrawPile is satisfied
    - expectRenderNextTurn is satisfied with CURRENT_PLAYER_INDEX and canEndTurn
    - view.buildWinOverlay is called with model.getWinnerName()
    - view.bindPlayAgainButton is called

- **TC37: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onExplodeButton_called_failed
  - **State of the system**: model.playExplode throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `onGodcatConfirm()`
- **TC38: Valid card type** ( :white_check_mark: )
  - **Name of the test**: onGodcatConfirm_validCardType_applyGodcatCalled
  - **State of the system**: cardType = ATTACK
  - **Expected output**:
    - model.applyGodcat is called with CardType.ATTACK
    - view.hideOverlay is called
    - expectUpdateTurnControls is satisfied

- **TC39: Model throws on invalid card type** ( :white_check_mark: )
  - **Name of the test**: onGodcatConfirm_modelThrowsException_failed
  - **State of the system**: 
    - cardType = EXPLODING_KITTEN
    - model.applyGodcat throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception