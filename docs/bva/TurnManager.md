# BVA Analysis: TurnManager Class

### Method under test: `TurnManager(int numPlayers)`
- **TC1: Zero players throws exception** ( :white_check_mark: )
  - **Name of the test**: constructor_zeroPlayers_throwsException
  - **State of the system**: numPlayers = 0
  - **Expected output**: IllegalArgumentException with the message "error.zeroOrNegativePlayers"

- **TC2: One player** ( :white_check_mark: )
  - **Name of the test**: constructor_validPlayerCount_setInitialCounts
  - **State of the system**: numPlayers = 1
  - **Expected output**:
    - currentPlayerIndex = 0
    - drawCount = 1
    - roundCount = 1

- **TC3: Two players** ( :white_check_mark: )
  - **Name of the test**: constructor_validPlayerCount_setInitialCounts
  - **State of the system**: numPlayers = 2
  - **Expected output**:
    - currentPlayerIndex = 0
    - drawCount = 1
    - roundCount = 1

### Method under test: `decrementDrawCount()`
- **TC4: Decrementing from zero throws state exception** ( :white_check_mark: )
  - **Name of the test**: decrementDrawCount_fromZero_throwsIllegalStateException
  - **State of the system**: drawCount = 0
  - **Expected output**: IllegalStateException("error.negativeDrawCount")

- **TC5: Decrementing from a positive value successfully reduces count** ( :white_check_mark: )
  - **Name of the test**: decrementDrawCount_positiveDrawCount_decrementedByOne
  - **State of the system**: drawCount = 1
  - **Expected output**: drawCount = 0

- **TC6: Decrementing from a positive value subtracts from the total** ( :white_check_mark: )
  - **Name of the test**: decrementDrawCount_fromTwo_toOne
  - **State of the system**: drawCount = 2
  - **Expected output**: drawCount = 1

### Method under test: `incrementTurn(IntPredicate isAlive)`
- **TC7: Advance turn away from starting baseline across varying player sizes** ( :white_check_mark: )
  - **Name of the test**: incrementTurn_nextPlayerIsAlive_updatesPlayerIndexCorrectly
  - **State of the system**: 
    - numPlayers = N (N = 2, 3, 4)
    - currentPlayerIndex = 0
    - deadIndices = []
    - roundCount = 1
  - **Expected output**: 
    - currentPlayerIndex = 1
    - roundCount = 1

- **TC8: Advance turn on boundary just before list wraps around** ( :white_check_mark: )
  - **Name of the test**: incrementTurn_nextPlayerIsAlive_updatesPlayerIndexCorrectly
  - **State of the system**: 
    - numPlayers = N (N = 2, 3, 4)
    - currentPlayerIndex = N - 2
    - deadIndices = []
    - roundCount = 2
  - **Expected output**: 
    - currentPlayerIndex = N - 1
    - roundCount = 2

- **TC9: Advance that hits upper boundary, wrapping index back to zero** ( :white_check_mark: )
  - **Name of the test**: incrementTurn_nextPlayerIsAlive_updatesPlayerIndexCorrectly
  - **State of the system**: 
    - numPlayers = N (N = 2, 3, 4)
    - currentPlayerIndex = N - 1
    - deadIndices = []
    - roundCount = 1
  - **Expected output**: 
    - currentPlayerIndex = 0
    - roundCount = 2

- **TC10: Current player at 0, 2 players, next player is dead** ( :white_check_mark: )
  - **Name of the test**: incrementTurn_nextPlayerIsDead_updatesPlayerIndexCorrectly
  - **State of the system**:
    - numPlayers = 2
    - currentPlayerIndex = 0
    - deadIndices = [1]
    - roundCount = 1
  - **Expected output**:
    - currentPlayerIndex = 0
    - roundCount = 2

- **TC11: Current player at 1, 2 players, next player is dead** ( :white_check_mark: )
  - **Name of the test**: incrementTurn_nextPlayerIsDead_updatesPlayerIndexCorrectly
  - **State of the system**:
    - numPlayers = 2
    - currentPlayerIndex = 1
    - deadIndices = [0]
    - roundCount = 1
  - **Expected output**:
    - currentPlayerIndex = 1
    - roundCount = 2

- **TC12: Current player at 0, 3 players, next player is dead** ( :white_check_mark: )
  - **Name of the test**: incrementTurn_nextPlayerIsDead_updatesPlayerIndexCorrectly
  - **State of the system**:
    - numPlayers = 3
    - currentPlayerIndex = 0
    - deadIndices = [1]
    - roundCount = 1
  - **Expected output**:
    - currentPlayerIndex = 2
    - roundCount = 1

- **TC13: Current player at 1, 3 players, next player is dead** ( :white_check_mark: )
  - **Name of the test**: incrementTurn_nextPlayerIsDead_updatesPlayerIndexCorrectly
  - **State of the system**:
    - numPlayers = 3
    - currentPlayerIndex = 1
    - deadIndices = [2]
    - roundCount = 2
  - **Expected output**:
    - currentPlayerIndex = 0
    - roundCount = 3

- **TC14: Current player at 2, 3 players, next player is dead** ( :white_check_mark: )
  - **Name of the test**: incrementTurn_nextPlayerIsDead_updatesPlayerIndexCorrectly
  - **State of the system**:
    - numPlayers = 3
    - currentPlayerIndex = 2
    - deadIndices = [0]
    - roundCount = 1
  - **Expected output**:
    - currentPlayerIndex = 1
    - roundCount = 2

- **TC15: Current player at 0, 4 players, next player is dead** ( :white_check_mark: )
  - **Name of the test**: incrementTurn_nextPlayerIsDead_updatesPlayerIndexCorrectly
  - **State of the system**:
    - numPlayers = 4
    - currentPlayerIndex = 0
    - deadIndices = [1]
    - roundCount = 1
  - **Expected output**:
    - currentPlayerIndex = 2
    - roundCount = 1

- **TC16: Current player at 2, 4 players, next two players are dead** ( :white_check_mark: )
  - **Name of the test**: incrementTurn_nextTwoPlayersAreDead_updatesPlayerIndexCorrectly
  - **State of the system**:
    - numPlayers = 4
    - currentPlayerIndex = 2
    - deadIndices = [0, 3]
    - roundCount = 1
  - **Expected output**:
    - currentPlayerIndex = 1
    - roundCount = 2

- **TC17: Current player at 2, 4 players, next three players are dead** ( :white_check_mark: )
  - **Name of the test**: incrementTurn_nextThreePlayersAreDead_updatesPlayerIndexCorrectly
  - **State of the system**:
    - numPlayers = 4
    - currentPlayerIndex = 2
    - deadIndices = [0, 1, 3]
    - roundCount = 1
  - **Expected output**:
    - currentPlayerIndex = 2
    - roundCount = 2

- **TC18: Current player at 0, 2 players, all dead** ( :white_check_mark: )
  - **Name of the test**: incrementTurn_allPlayersAreDead_failed
  - **State of the system**:
    - numPlayers = 2
    - deadIndices = [0, 1]
  - **Expected output**: throw IllegalStateException "error.noAlivePlayers"

### Method under test: `setCurrentPlayerIndex(int newPlayerIndex)`
- **TC18: Negative index** ( :white_check_mark: )
  - **Name of the test**: setCurrentPlayerIndex_invalidIndex_failed
  - **State of the system**: TurnManager constructed with 1 player, newPlayerIndex = 0
  - **Expected output**: throw IllegalArgumentException "error.invalidPlayerIndex"

- **TC19: Minimum valid index** ( :white_check_mark: )
  - **Name of the test**: setCurrentPlayerIndex_validIndex_setNewIndex
  - **State of the system**: TurnManager constructed with 2 players, newPlayerIndex = 0
  - **Expected output**: currentPlayerIndex = newPlayerIndex

- **TC20: Maximum valid index** ( :white_check_mark: )
  - **Name of the test**: setCurrentPlayerIndex_validIndex_setNewIndex
  - **State of the system**: TurnManager constructed with 2 players, newPlayerIndex = 1
  - **Expected output**: currentPlayerIndex = newPlayerIndex

- **TC21: One above maximum valid index** ( :white_check_mark: )
  - **Name of the test**: setCurrentPlayerIndex_invalidIndex_failed
  - **State of the system**: TurnManager constructed with 1 player, newPlayerIndex = 1
  - **Expected output**: throw IllegalArgumentException "error.invalidPlayerIndex"

### Method under test: `incrementDrawCount()`
- **TC14: Decrementing from zero throws state exception** ( :white_check_mark: )
  - **Name of the test**: incrementDrawCount_validDrawCount_incrementedByOne
  - **State of the system**: drawCount = 0
  - **Expected output**: drawCount = 1

- **TC15: incrementing from a positive value successfully increases count** ( :white_check_mark: )
  - **Name of the test**: incrementDrawCount_validDrawCount_incrementedByOne
  - **State of the system**: drawCount = 1
  - **Expected output**: drawCount = 2