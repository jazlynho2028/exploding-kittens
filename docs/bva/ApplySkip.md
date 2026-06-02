### Method under test: `toggleSelected()`
- **TC1: Skip on a standard single turn (drawCount = 1)** ( :x: )
    - **Name of the test:** applySkip_drawCountOne_TurnAdvances
    - **State of the system:** game is ongoing, drawCount = 1 (normal, unattacked turn)
    - **Expected output:** drawCount resets to 1 (from incrementTurn), currentPlayerIndex becomes 1, turn has advanced automatically

- **TC2: Skip while under attack (drawCount = 2)** ( :x: )
    - **Name of the test:** applySkip_drawCountTwo_TurnNotAdvanced
    - **State of the system:** game is ongoing, drawCount = 2, currentPlayerIndex = 0
    - **Expected output:** drawCount becomes 1, currentPlayerIndex remains 0, turn is NOT advanced, player must still draw a card

- **TC3: Skip under stacked attacks (drawCount = 3+)** ( :x: )
    - **Name of the test:** applySkip_drawCountThree_TurnNotAdvanced
    - **State of the system:** game is ongoing, drawCount = 3, currentPlayerIndex = 0
    - **Expected output:** drawCount becomes 2, currentPlayerIndex remains 0, turn is NOT advanced

- **TC4: Skip completes turn for the last player in order (wraparound)** ( :x: )
    - **Name of the test:** applySkip_lastPlayer_turnWraps
    - **State of the system:** game is ongoing, drawCount = 1, currentPlayerIndex = numPlayers - 1
    - **Expected output:** currentPlayerIndex wraps to 0, drawCount resets to 1 (from incrementTurn)

- **TC5: Skip with minimum player count (2 players)** ( :x: )
    - **Name of the test:** applySkip_twoPlayers_turnAdvances
    - **State of the system:**  game is ongoing, 2 players, drawCount = 1, currentPlayerIndex = 0
    - **Expected output:** currentPlayerIndex becomes 1, drawCount resets to 1, the single other player is now active

- **TC6: Skip on a standard single turn with maximum players (4 players)** ( :x: )
    - **Name of the test:** applySkip_fourPlayers_turnAdvances
    - **State of the system:**  game is ongoing, drawCount = 1, 4 players, currentPlayerIndex = 0
    - **Expected output:** drawCount resets to 1 (from incrementTurn), currentPlayerIndex = 1, turn has advanced automatically