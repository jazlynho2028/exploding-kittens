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
  - **Name of the test**: buildPlayerDeckScene_buildDependentUIThrows_callsOnError
  - **State of the system**: buildDependentUI throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `buildDependentUI()`
- **TC3: This method is executed successfully** ( :white_check_mark: )
  - **Name of the test**: buildDependentUI_called_buildsHandCardsAndNameTags
  - **State of the system**:
    - isFaceUp = true
    - isGameOngoing = false
  - **Expected output**:
    - view.buildAndAddPlayerHandCards is called with CURRENT_PLAYER_HAND_IDS, isFaceUp, CAN_PLAY
    - view.buildAddRenderPlayerNameTags is called with PLAYER_NAMES, CURRENT_PLAYER_INDEX, !isGameOngoing, ALIVE_INDICES

## Method under test: `bindUI()`
- **TC4: This method is called** ( :white_check_mark: )
  - **Name of the test**: bindUI_called_bindsAllButtons
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
  - **Name of the test**: onNameTag_samePlayerIndex_noChange
  - **State of the system**:
    - playerIndex = CURRENT_PLAYER_INDEX
    - model.getCurrentPlayerIndex returns CURRENT_PLAYER_INDEX
  - **Expected output**:
    - model.getCurrentPlayerIndex is called
    - no further model or view interactions occur

- **TC6: Player index is different from current player index** ( :white_check_mark: )
  - **Name of the test**: onNameTag_differentPlayerIndex_changesPlayerAndRerenders
  - **State of the system**:
    - playerIndex = 1
    - model.getCurrentPlayerIndex returns CURRENT_PLAYER_INDEX (0)
  - **Expected output**:
    - model.changeCurrentPlayerIndex is called with playerIndex
    - updateAll is called (rebindHandCards, updateTurnControls, updateNameTags, updateDrawPile, updateDiscardPile, updateHandVisibilityButton)

- **TC7: pendingTargetAction is present** ( :white_check_mark: )
  - **Name of the test**: onNameTag_pendingTargetActionPresent_executesActionThenChangesPlayer
  - **State of the system**:
    - pendingTargetAction = Optional.of(mockAction)
    - playerIndex = 1
    - model.getCurrentPlayerIndex returns CURRENT_PLAYER_INDEX (0)
  - **Expected output**:
    - mockAction.accept(playerIndex) is called
    - pendingTargetAction becomes empty
    - model.changeCurrentPlayerIndex is called with playerIndex
    - updateAll is called

- **TC8: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onNameTag_modelThrowsException_callsOnError
  - **State of the system**: model.getCurrentPlayerIndex throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `updateAll()`
- **TC9: This method is executed successfully** ( :white_check_mark: )
  - **Name of the test**: updateAll_called_updatesAllViewComponents
  - **State of the system**:
    - isFaceUp = false
    - isGameOngoing = true
  - **Expected output** (in order):
    - view.buildAndAddPlayerHandCards is called with CURRENT_PLAYER_HAND_IDS, isFaceUp, CAN_PLAY
    - view.bindPlayerHandCardButtons is called
    - view.renderTurnControlSection is called with CAN_PLAY_SELECTED, CAN_END_TURN
    - view.renderPlayerNameTags is called with CURRENT_PLAYER_INDEX, !isGameOngoing, ALIVE_INDICES
    - view.renderDrawPile is called with CAN_DRAW, IS_DRAW_PILE_EMPTY
    - view.renderDiscardPile is called with TOP_DISCARD_ID
    - view.renderHandVisibilityButton is called with isFaceUp, true

## Method under test: `rebindHandCards()`
- **TC10: This method is called** ( :white_check_mark: )
  - **Name of the test**: rebindHandCards_called_rebuildsAndBindsHandCards
  - **State of the system**:
    - isFaceUp = true
  - **Expected output**:
    - view.buildAndAddPlayerHandCards is called with CURRENT_PLAYER_HAND_IDS, isFaceUp, CAN_PLAY
    - view.bindPlayerHandCardButtons is called

## Method under test: `onDrawPile()`
- **TC11: Draw non-exploding card successfully** ( :white_check_mark: )
  - **Name of the test**: onDrawPile_nonExplodingCardDrawn_callsUpdateAll
  - **State of the system**:
    - drawnCard type = DEFUSE
  - **Expected output**:
    - updateAll is called

- **TC12: Draw Exploding Kitten, has Defuse** ( :white_check_mark: )
  - **Name of the test**: onDrawPile_explodingKittenDrawn_defusable_buildsExplodeOverlay
  - **State of the system**:
    - isDefusable = true
    - drawnCardId = "EXPLODINGKITTEN_1"
    - drawPileSize = 3
  - **Expected output**:
    - view.bindDefuseButton is called
    - view.buildExplodeOverlay is called with isDefusable, drawnCardId, drawPileSize - 1

- **TC13: Draw Exploding Kitten, no Defuse** ( :white_check_mark: )
  - **Name of the test**: onDrawPile_explodingKittenDrawn_notDefusable_buildsExplodeOverlay
  - **State of the system**:
    - isDefusable = false
    - drawnCardId = "EXPLODINGKITTEN_1"
    - drawPileSize = 3
  - **Expected output**:
    - view.bindExplodeButton is called
    - view.buildExplodeOverlay is called with isDefusable, drawnCardId, drawPileSize - 1

- **TC14: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onDrawPile_modelThrowsException_callsOnError
  - **State of the system**: model.drawFromPile throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `onHandVisibilityButton()`
- **TC15: This method is executed successfully** ( :white_check_mark: )
  - **Name of the test**: onHandVisibilityButton_called_togglesAndRebinds
  - **State of the system**:
    - isFaceUp = true (before toggle)
  - **Expected output**:
    - model.toggleFaceUp is called
    - view.renderHandVisibilityButton is called with isFaceUp, true
    - view.buildAndAddPlayerHandCards is called with CURRENT_PLAYER_HAND_IDS, isFaceUp, CAN_DRAW
    - view.bindPlayerHandCardButtons is called

- **TC16: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onHandVisibilityButton_modelThrowsException_callsOnError
  - **State of the system**: model.toggleFaceUp throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `onPlayerHandCardButton(int handCardIndex)`
- **TC17: Cards are face up** ( :white_check_mark: )
  - **Name of the test**: onPlayerHandCardButton_cardsFaceUp_togglesSelectionAndUpdatesTurnControls
  - **State of the system**:
    - handCardIndex = 2
    - isFaceUp = true
  - **Expected output**:
    - model.toggleSelectedPlayerCardAt is called with handCardIndex
    - view.renderTurnControlSection is called with CAN_PLAY_SELECTED, CAN_END_TURN

- **TC18: Cards are face down** ( :white_check_mark: )
  - **Name of the test**: onPlayerHandCardButton_cardsFaceDown_delegatesToHandVisibilityButton
  - **State of the system**:
    - handCardIndex = 0
    - isFaceUp = false
  - **Expected output**: onHandVisibilityButton is called

- **TC19: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onPlayerHandCardButton_modelThrowsException_callsOnError
  - **State of the system**: model.getIsFaceUp throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `onStartGameButton()`
- **TC20: Game starts successfully** ( :white_check_mark: )
  - **Name of the test**: onStartGameButton_called_startsGameAndRendersUI
  - **State of the system**:
    - isGameOngoing = true
  - **Expected output**:
    - model.startGame is called
    - view.buildAndRenderTurnControlSection is called with isGameOngoing, CAN_PLAY_SELECTED, CAN_END_TURN
    - updateAll is called

- **TC21: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onStartGameButton_modelThrowsException_callsOnError
  - **State of the system**: model.startGame throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `onPlayCardsButton()`
- **TC22: Godcat card played** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_godcatPlayed_showsGodcatOverlay
  - **State of the system**:
    - model.playSelectedCards returns GODCAT
  - **Expected output**:
    - updateAll is called
    - view.bindGodcatConfirmButton is called
    - view.buildGodcatOverlay is called with GameConstants.GODCAT_CARDTYPE_OPTIONS

- **TC23: Skip variant played** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_skipVariantPlayed_delegatesToUpdateByCardType
  - **State of the system**:
    - model.playSelectedCards returns SKIP or SUPER_SKIP
  - **Expected output**:
    - updateAll is called
    - updateByCardType is called with cardType (no additional view interactions for SKIP/SUPER_SKIP)

- **TC24: Targeted Attack card played** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_targetedAttackPlayed_enablesPlayerSelectMode
  - **State of the system**:
    - model.playSelectedCards returns TARGETED_ATTACK
    - currentPlayerIndex = CURRENT_PLAYER_INDEX
  - **Expected output**:
    - updateAll is called
    - pendingTargetAction becomes present (set to model::applyTargetedAttack)
    - view.renderPlayerNameTags is called with CURRENT_PLAYER_INDEX, true, ALIVE_INDICES
    - view.renderDrawPile is called with false, IS_DRAW_PILE_EMPTY
    - view.renderHandVisibilityButton is called with isFaceUp, false
    - view.buildAndAddPlayerHandCards is called with CURRENT_PLAYER_HAND_IDS, isFaceUp, false
    - view.renderTurnControlSection is called with false, false

- **TC25: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onPlayCardsButton_modelThrowsException_callsOnError
  - **State of the system**: model.playSelectedCards throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `updateByCardType(CardType cardType)`
- **TC26: No additional UI change** ( :white_check_mark: )
  - **Name of the test**: updateByCardType_noAdditionalUIChange_noViewInteractions
  - **State of the system**: cardType =
    - ATTACK
    - SHUFFLE
    - SKIP
    - CATOMIC_BOMB
    - SUPER_SKIP
    - CLONE
    - SWAP_TOP_AND_BOTTOM
    - DRAW_FROM_THE_BOTTOM
    - WINNER_WINNER_CATNIP_DINNER
    - RAGEBAIT
    - RECYCLE
    - DOUBLE_UP
    - MILD_SHUFFLE
  - **Expected output**: no model or view interactions occur

- **TC27: See The Future card played** ( :white_check_mark: )
  - **Name of the test**: updateByCardType_seeTheFuturePlayed_buildsOverlay
  - **State of the system**:
    - cardType = SEE_THE_FUTURE
    - model.getSeeTheFutureCardIds returns ["ATTACK_1", "SKIP_2", "DEFUSE_3"]
  - **Expected output**:
    - view.buildSeeTheFutureOverlay is called with futureCardIds

- **TC28: Targeted Attack card type** ( :white_check_mark: )
  - **Name of the test**: updateByCardType_targetedAttackPlayed_enablesPlayerSelectMode
  - **State of the system**:
    - cardType = TARGETED_ATTACK
    - currentPlayerIndex = CURRENT_PLAYER_INDEX
  - **Expected output**:
    - pendingTargetAction becomes present (set to model::applyTargetedAttack)
    - view.renderPlayerNameTags is called with CURRENT_PLAYER_INDEX, true, ALIVE_INDICES
    - view.renderDrawPile is called with false, IS_DRAW_PILE_EMPTY
    - view.renderHandVisibilityButton is called with isFaceUp, false
    - view.buildAndAddPlayerHandCards is called with CURRENT_PLAYER_HAND_IDS, isFaceUp, false
    - view.renderTurnControlSection is called with false, false

## Method under test: `onEndTurnButton()`
- **TC29: Turn ends successfully, game ongoing** ( :white_check_mark: )
  - **Name of the test**: onEndTurnButton_called_advancesTurnAndRendersNext
  - **State of the system**:
    - model.getIsGameOngoing returns true
  - **Expected output**:
    - model.advanceTurn is called
    - updateAll is called

- **TC30: Turn ends, game over** ( :white_check_mark: )
  - **Name of the test**: onEndTurnButton_gameOver_showsWinOverlay
  - **State of the system**:
    - model.getIsGameOngoing returns false
    - winnerName = "Audrey"
  - **Expected output**:
    - model.advanceTurn is called
    - updateAll is called
    - view.buildWinOverlay is called with winnerName
    - view.bindPlayAgainButton is called with onRestart

- **TC31: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onEndTurnButton_modelThrowsException_callsOnError
  - **State of the system**: model.advanceTurn throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `onDefuseButton()`
- **TC32: Defuse Exploding Kitten successfully** ( :white_check_mark: )
  - **Name of the test**: onDefuseButton_called_defusesAndAdvancesTurn
  - **State of the system**:
    - insertIndex = 2
  - **Expected output**:
    - view.getExplodingKittenInsertIndex is called
    - model.playDefuse is called with insertIndex
    - view.hideOverlay is called
    - updateAll is called

- **TC33: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onDefuseButton_modelThrowsException_callsOnError
  - **State of the system**: model.playDefuse throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `onExplodeButton()`
- **TC34: Explode successfully, game ongoing** ( :white_check_mark: )
  - **Name of the test**: onExplodeButton_gameOngoing_advancesToNextTurn
  - **State of the system**:
    - model.getIsGameOngoing returns true
  - **Expected output**:
    - model.playExplode is called
    - view.hideOverlay is called
    - updateAll is called

- **TC35: Explode successfully, game over** ( :white_check_mark: )
  - **Name of the test**: onExplodeButton_gameOver_showsWinOverlay
  - **State of the system**:
    - model.getIsGameOngoing returns false
    - winnerName = "Audrey"
  - **Expected output**:
    - model.playExplode is called
    - view.hideOverlay is called
    - updateAll is called
    - view.buildWinOverlay is called with winnerName
    - view.bindPlayAgainButton is called with onRestart

- **TC36: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onExplodeButton_modelThrowsException_callsOnError
  - **State of the system**: model.playExplode throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception

## Method under test: `onGodcatConfirm()`
- **TC37: Valid card type** ( :white_check_mark: )
  - **Name of the test**: onGodcatConfirm_validCardType_appliesGodcatAndRendersAll
  - **State of the system**:
    - view.getSelectedGodcatCardType returns ATTACK
  - **Expected output**:
    - view.getSelectedGodcatCardType is called
    - model.applyGodcat is called with ATTACK
    - view.hideOverlay is called
    - updateAll is called
    - updateByCardType is called with ATTACK

- **TC38: Caught exception from model** ( :white_check_mark: )
  - **Name of the test**: onGodcatConfirm_modelThrowsException_callsOnError
  - **State of the system**:
    - view.getSelectedGodcatCardType returns ATTACK
    - model.applyGodcat throws RuntimeException "An error occurred."
  - **Expected output**: onError accepts exception