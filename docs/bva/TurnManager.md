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