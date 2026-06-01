# BVA Analysis: TurnManager Class
### Method under test: `TurnManager(List<Player> players)`
- **TC1: Empty players list throws exception** ( :white_check_mark: )
  - **Name of the test**: constructor_emptyPlayerList_throwsException
  - **State of the system**: players = []
  - **Expected output**: IllegalArgumentException with the message "error.emptyPlayerList"

- **TC2: One player** ( :white_check_mark: )
  - **Name of the test**: constructor_validPlayerCount_setInitialCounts
  - **State of the system**: players = [player1]
  - **Expected output**:
    - currentPlayerIndex = 0
    - drawCount = 1
    - roundCount = 1

- **TC3: Two players** ( :white_check_mark: )
  - **Name of the test**: constructor_validPlayerCount_setInitialCounts
  - **State of the system**: players = [player1, player2]
  - **Expected output**:
    - currentPlayerIndex = 0
    - drawCount = 1
    - roundCount = 1

### Method under test: `getCurrentPlayerIndex()`
- **TC4: first player's index before any turns taken with list of minimum players** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerIndex_twoPlayersInitialState_returnsZero
  - **State of the system**: TurnManager constructed with list of 2 players
  - **Expected output**: Returns 0 (first player's index)

- **TC5: first player's index before any turns taken with list of more than one player** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerIndex_moreThanOnePlayer_returnsZero
  - **State of the system**: TurnManager constructed with list of 3 players
  - **Expected output**: Returns 0 (first player's index)

- **TC6: first player's index before any turns taken with list of maximum players** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerIndex_maximumPlayers_returnsZero
  - **State of the system**: TurnManager constructed with list of 4 players
  - **Expected output**: Returns 0 (first player's index)

### Method under test: `incrementDrawCount()`
- **TC7: Draw count incremented from its absolute minimum baseline** ( :white_check_mark: )
  - **Name of the test**: incrementDrawCount_fromZero_success
  - **State of the system**: currentDrawCount = 0
  - **Expected output**: getCurrentDrawCount() = 1

- **TC8: Draw count incremented on positive draw count** ( :white_check_mark: )
  - **Name of the test**: incrementDrawCount_fromOne_success
  - **State of the system**: currentDrawCount = 1
  - **Expected output**: getCurrentDrawCount() = 2

### Method under test: `decrementDrawCount()`
- **TC9: Decrementing from zero throws state exception*** ( :white_check_mark: )
  - **Name of the test**: decrementDrawCount_fromZero_throwsIllegalStateException
  - **State of the system**: currentDrawCount = 0
  - **Expected output**: IllegalStateException("error.negativeDrawCount")

- **TC10: Decrementing from a positive value successfully reduces count** ( :white_check_mark: )
  - **Name of the test**: decrementDrawCount_fromOne_toZero
  - **State of the system**: currentDrawCount = 1
  - **Expected output**: getCurrentDrawCount() = 0

- **TC11: Decrementing from a positive value subtracts from the total** ( :white_check_mark: )
  - **Name of the test**: decrementDrawCount_fromTwo_toOne
  - **State of the system**: currentDrawCount = 2
  - **Expected output**: getCurrentDrawCount() = 1

### Method under test: `incrementRound()`
- **TC12: Round incremented from its minimum baseline** ( :white_check_mark: )
  - **Name of the test**: incrementRound_initialOne_toTwo
  - **State of the system**: roundCounter = 1
  - **Expected output**: getRoundCounter() = 2

- **TC13: Round count incremented on nonzero draw count** ( :white_check_mark: )
  - **Name of the test**: incrementRound_fromTwo_toThree
  - **State of the system**: roundCounter = 2
  - **Expected output**: getRoundCounter() = 3

### Method under test: `advanceTurn()`
- **TC14: Advance turn away from starting baseline across varying player sizes** ( :white_check_mark: )
  - **Name of the test**: advanceTurn_boundaryScenarios_updatesPlayerIndexCorrectly
  - **State of the system**: TurnManager constructed with N players (N = 2, 3, 4), currentPlayerIndex = 0
  - **Expected output**: getCurrentPlayerIndex() = 1, deselectHandCards called on current player

- **TC15: Advance turn on boundary just before list wraps around** ( :white_check_mark: )
  - **Name of the test**: advanceTurn_boundaryScenarios_updatesPlayerIndexCorrectly
  - **State of the system**: TurnManager constructed with N players (N = 2, 3, 4), currentPlayerIndex = N - 2
  - **Expected output**: getCurrentPlayerIndex() = N - 1, deselectHandCards called on current player

- **TC16: Advance that hits upper boundary, wrapping index back to zero** ( :white_check_mark: )
  - **Name of the test**: advanceTurn_boundaryScenarios_updatesPlayerIndexCorrectly
  - **State of the system**: TurnManager constructed with N players (N = 2, 3, 4), currentPlayerIndex = N - 1
  - **Expected output**: getCurrentPlayerIndex() = 0, deselectHandCards called on current player

- **TC17: Strict testing on index of player when starting next round** ( :white_check_mark: )
  - **Name of the test**: advanceTurn_atLastPlayer_wrapsToZeroExactly
  - **State of the system**: TurnManager constructed with 3 players advanced turn three times
  - **Expected output**: getCurrentPlayerIndex() = 0, deselectHandCards called on current player

- **TC18: Strict testing on round count when not starting next round** ( :white_check_mark: )
  - **Name of the test**: advanceTurn_nextPlayer_sameRoundCount
  - **State of the system**: TurnManager constructed with 2 players advanced turn once
  - **Expected output**: getRoundCount() = 1, deselectHandCards called on current player

- **TC19: Strict testing on round count when starting next round** ( :white_check_mark: )
  - **Name of the test**: advanceTurn_wrapsToStartingPlayer_incrementsRoundCount
  - **State of the system**: TurnManager constructed with 2 players advanced turn twice times
  - **Expected output**: getRoundCount() = 2, deselectHandCards called on current player

- **TC20: Strict testing on draw count when advancing turn** ( :white_check_mark: )
  - **Name of the test**: advanceTurn_fromDrawCountOne_toTwo 
  - **State of the system**: TurnManager constructed with 1 player advanced turn once
  - **Expected output**: getDrawCount() = 2, deselectHandCards called on current player

### Method under test: `getCurrentPlayer()`
- **TC21: getCurrentPlayer returns the correct player** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayer_oneTurnAdvanced_returnsCorrectPlayerInstance
  - **State of the system**: TurnManager constructed with 2 players 1 turn advanced
  - **Expected output**: getCurrentPlayer = player2

### Method under test: `getCurrentPlayerHandIds()`
- **TC22: This method is called** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerHandIds_onePlayer_callsPlayerMethod
  - **State of the system**: TurnManager constructed with 1 player
  - **Expected output**: calls getHandIds on current player

### Method under test: `getCurrentSelectedCards()`
- **TC23: This method is called** ( :white_check_mark: )
  - **Name of the test**: getCurrentSelectedCards_onePlayer_callsPlayerMethod
  - **State of the system**: TurnManager constructed with 1 player
  - **Expected output**: calls getSelectedCards on current player

### Method under test: `getStartingPlayerIndex()`
- **TC24: getCurrentPlayer returns the index 0** ( :white_check_mark: )
  - **Name of the test**: getStartingPlayerIndex_successfullyReturnsStartingIndex
  - **State of the system**: TurnManager constructed with 1 player
  - **Expected output**: getStartingPlayerIndex() returns 0

### Method under test: `toggleSelectedPlayerCardAt(int handCardIndex)`
- **TC25: This method is called successfully** ( :white_check_mark: )
  - **Name of the test**: toggleSelectedPlayerCardAt_onePlayer_callsPlayerMethod
  - **State of the system**: TurnManager constructed with 1 player
  - **Expected output**: calls toggleSelectedHandCardAt with handCardIndex on current player

- **TC26: Player method throws exception** ( :white_check_mark: )
  - **Name of the test**: toggleSelectedPlayerCardAt_onePlayer_failed
  - **State of the system**: 
    - TurnManager constructed with 1 player
    - toggleSelectedHandCardAt with handCardIndex on current player throws IllegalArgumentException "error.invalidHandCardIndex"
  - **Expected output**: throw IllegalArgumentException "error.invalidHandCardIndex"

### Method under test: `updateAfterDraw(Card card)`
- **TC27: This method is called** ( :white_check_mark: )
  - **Name of the test**: updateAfterDraw_onePlayer_decrementDrawAndCallPlayerMethods
  - **State of the system**: TurnManager constructed with 1 player
  - **Expected output**:
    - decrementDrawCount is called
    - deselectHandCards is called on current player
    - addCardToHand is called on current player

- **TC28: Helper method throws exception** ( :white_check_mark: )
  - **Name of the test**: updateAfterDraw_onePlayer_failed
  - **State of the system**: 
    - TurnManager constructed with 1 player
    - decrementDrawCount throws IllegalStateException "error.negativeDrawCount"
  - **Expected output**: throw IllegalStateException "error.negativeDrawCount"

### Method under test: `removeCardFromCurrentPlayerHand(Card card)`
- **TC29: This method is called successfully** ( :white_check_mark: )
  - **Name of the test**: removeCardFromCurrentPlayerHand_onePlayer_callsPlayerMethod
  - **State of the system**: TurnManager constructed with 1 player
  - **Expected output**: calls removeCardFromHand with card on current player

- **TC30: Player method throws exception** ( :white_check_mark: )
  - **Name of the test**: removeCardFromCurrentPlayerHand_onePlayer_failed
  - **State of the system**: 
    - TurnManager constructed with 1 player
    - removeCardFromHand with card on current player throws IllegalStateException "error.cardNotInHand"
  - **Expected output**: throw IllegalStateException "error.cardNotInHand"

### Method under test: `setCurrentPlayerIndex(int newPlayerIndex)`
- **TC31: Negative index** ( :x: )
  - **Name of the test**: setCurrentPlayerIndex_invalidIndex_failed
  - **State of the system**: TurnManager constructed with 1 player, newPlayerIndex = 0
  - **Expected output**: throw IllegalArgumentException "error.invalidPlayerIndex"

- **TC32: Minimum valid index** ( :x: )
  - **Name of the test**: setCurrentPlayerIndex_validIndex_setNewIndex
  - **State of the system**: TurnManager constructed with 2 players, newPlayerIndex = 0
  - **Expected output**: currentPlayerIndex = newPlayerIndex

- **TC33: Maximum valid index** ( :x: )
  - **Name of the test**: setCurrentPlayerIndex_validIndex_setNewIndex
  - **State of the system**: TurnManager constructed with 2 players, newPlayerIndex = 1
  - **Expected output**: currentPlayerIndex = newPlayerIndex

- **TC34: One above maximum valid index** ( :x: )
  - **Name of the test**: setCurrentPlayerIndex_invalidIndex_failed
  - **State of the system**: TurnManager constructed with 1 player, newPlayerIndex = 1
  - **Expected output**: throw IllegalArgumentException "error.invalidPlayerIndex"
