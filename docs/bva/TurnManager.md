# BVA Analysis: TurnManager Class
### Method under test: `getCurrentPlayerIndex()`
- **TC1: first player's index before any turns taken with list of minimum players** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerIndex_twoPlayersInitialState_returnsZero
  - **State of the system**: TurnManager constructed with list of 2 players
  - **Expected output**: Returns 0 (first player's index)

- **TC2: first player's index before any turns taken with list of more than one player** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerIndex_moreThanOnePlayer_returnsZero
  - **State of the system**: TurnManager constructed with list of 3 players
  - **Expected output**: Returns 0 (first player's index)

- **TC3: first player's index before any turns taken with list of maximum players** ( :white_check_mark: )
  - **Name of the test**: getCurrentPlayerIndex_maximumPlayers_returnsZero
  - **State of the system**: TurnManager constructed with list of 4 players
  - **Expected output**: Returns 0 (first player's index)
### Method under test: `incrementDrawCount()`
- **TC4: Draw count incremented from its absolute minimum baseline** ( :white_check_mark: )
  - **Name of the test**: incrementDrawCount_initialZero_success
  - **State of the system**: currentDrawCount = 0
  - **Expected output**: getCurrentDrawCount() = 1
- **TC5: Draw count incremented on positive draw count** ( :white_check_mark: )
  - **Name of the test**: incrementDrawCount_fromOne_success
  - **State of the system**: currentDrawCount = 1
  - **Expected output**: getCurrentDrawCount() = 2
### Method under test: `decrementDrawCount()`
- **TC6: Draw count decremented from its absolute minimum baseline** ( :white_check_mark: )
  - **Name of the test**: decrementDrawCount_initialZero_success
  - **State of the system**: currentDrawCount = 0
  - **Expected output**: getCurrentDrawCount() = -1
- **TC7: Draw count incremented on nonzero draw count** ( :white_check_mark: )
  - **Name of the test**: decrementDrawCount_fromNonZero_success
  - **State of the system**: currentDrawCount = -1
  - **Expected output**: getCurrentDrawCount() = -2
### Method under test: `incrementRound()`
- **TC8: Round incremented from its minimum baseline** ( :white_check_mark: )
  - **Name of the test**: incrementRound_initialZero_success
  - **State of the system**: roundCounter = 0
  - **Expected output**: getRoundCounter() = 1
- **TC9: Draw count incremented on nonzero draw count** ( :white_check_mark: )
  - **Name of the test**: incrementRound_fromNonZero_success
  - **State of the system**: roundCounter = 1
  - **Expected output**: getRoundCounter() = 2
### Method under test: `advanceTurn()`
- **TC10: Advance turn away from starting baseline** ( :white_check_mark: )
  - **Name of the test**: advanceTurn_fromIndexZero_currentPlayerIndexBecomesOne
  - **State of the system**: TurnManager constructed with 3 players, currentPlayerIndex = 0
  - **Expected output**: getCurrentPlayerIndex() = 1 getRoundCounter() = 0, getCurrentDrawCount = 1
- **TC11: Advance turn on boundary just before list wraps around** ( :white_check_mark: )
  - **Name of the test**: advanceTurn_fromSecondToLastPlayer_currentIndexReachMax
  - **State of the system**: TurnManager initialized with 3 players currentPlayerIndex = 1
  - **Expected output**: getCurrentPlayerIndex() = 2 getRoundCounter() = 0 getCurrentDrawCount() = 1
- **TC12: Advance that resets index and increments the round** ( :white_check_mark: )
  - **Name of the test**: advanceTurn_fromMaxValidIndex_incrementsRound
  - **State of the system**: TurnManager initialized with 3 players currentPlayerIndex = 2
  - **Expected output**: getCurrentPlayerIndex() = 0 getRoundCounter() = 1 getCurrentDrawCount() = 1