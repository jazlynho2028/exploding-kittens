# BVA Analysis: SuperSkip Method

### Method under test: `applySuperSkip()`
- **TC1: Super Skip on a standard single turn (drawCount = 1)** ( :white_check_mark: )
    - **Name of the test:** applySuperSkip_drawCountOne_TurnAdvances
    - **State of the system:** game is ongoing, drawCount = 1, currentPlayerIndex = 0
    - **Expected output:** decrementDrawCount() called once, turn advances to currentPlayerIndex = 1

- **TC2: Super Skip while under attack (drawCount = 2)** ( :white_check_mark: )
    - **Name of the test:** applySuperSkip_drawCountTwo_TurnAdvances
    - **State of the system:** game is ongoing, drawCount = 2, currentPlayerIndex = 0
    - **Expected output:** decrementDrawCount() called twice, turn advances to currentPlayerIndex = 1

- **TC3: Super Skip under stacked attacks (drawCount = 3+)** ( :white_check_mark: )
    - **Name of the test:** applySuperSkip_drawCountThree_TurnAdvances
    - **State of the system:** game is ongoing, drawCount = 3, currentPlayerIndex = 0
    - **Expected output:** decrementDrawCount() called three times, turn advances to currentPlayerIndex = 1

- **TC4: Super Skip completes turn for the last player in order (wraparound)** ( :white_check_mark: )
    - **Name of the test:** applySuperSkip_lastPlayer_turnWraps
    - **State of the system:** game is ongoing, drawCount = 1, currentPlayerIndex = numPlayers - 1
    - **Expected output:** decrementDrawCount() called once, currentPlayerIndex wraps to 0

- **TC5: Super Skip with minimum player count (2 players)** ( :x: )
    - **Name of the test:** applySuperSkip_twoPlayers_turnAdvances
    - **State of the system:** game is ongoing, 2 players, drawCount = 1, currentPlayerIndex = 0
    - **Expected output:** decrementDrawCount() called once, currentPlayerIndex wraps to 0

- **TC6: Super Skip with maximum player count (4 players)** ( :x: )
    - **Name of the test:** applySuperSkip_fourPlayers_turnAdvances
    - **State of the system:**  game is ongoing, 4 players, drawCount = 1, currentPlayerIndex = 0
    - **Expected output:** decrementDrawCount() called once, turn advances to currentPlayerIndex = 1
