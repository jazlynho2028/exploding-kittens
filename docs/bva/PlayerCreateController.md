### Method under test: `onAddPlayer()`
- **TC1: Adding a field with 0 players** ( :white_check_mark: )
  - **Name of the test:** onAddPlayer_CurrentZero_Success
  - **State of the system:** playerFields = []
  - **Expected output:** playerFields now holds a singular TextField, setAddPlayerButtonDisabled gets called with false

- **TC2: Adding the last field allowed, the fourth player** ( :white_check_mark: )
  - **Name of the test:** onAddPlayer_CurrentThree_Success
  - **State of the System:** playerFields = [TextField, TextField, TextField]
  - **Expected output:** playerFields now holds four TextFields, setAddPlayerButtonDisabled gets called with true

- **TC3: Adding a fifth player that's above the maximum** ( :white_check_mark: )
  - **Name of the test:** onAddPlayer_CurrentFour_Failed
  - **State of the System:** PlayerFields = [TextField, TextField, TextField]
  - **Expected output:** calls onError.accept("You cannot have more than 4 players.")

### Method under test: `onConfirmNames()`
- **TC4: Confirming exactly below the minimum limit, 1 player** ( :white_check_mark: )
  - **Name of the test:** onConfirmNames_OnePlayer_Failed
  - **State of the System:** view.getPlayerNamesFromFields() = ["Alice"]
  - **Expected output:** calls onError.accept("you need at least 2 players.") onSuccess.run() not called

- **TC5: Confirming at the minimum number of players** ( :white_check_mark: )
  - **Name of the test:** onConfirmNames_TwoPlayers_Success
  - **State of the System:** view.getPlayerNamesFromFields() = ["Alice", "Bob"]
  - **Expected output:** onSuccess.run() executes, list of confirmedNames becomes size 2

- **TC6: Confirming correct amount but onSuccess exception gets thrown** ( :white_check_mark: )
  - **Name of the test:** onConfirmNames_onSuccess_Error
  - **State of the System:** view.getPlayerNamesFromFields() = ["Alice", "Bob", "Dave"] onSuccess = error
  - **Expected output:** onSuccess.run() fails, onError.accept(<exception message>) runs

### Method under test: `getPlayerNumbers()`
- **TC7: Empty player fields ** ( :white-check-mark: )
  - **Name of the test:** getPlayerNumbers_empty_return0
  - **State of the System:** playerFields = []
  - **Expected output:** return 0

- **TC8: Player fields has one field ** ( :white-check-mark: )
  - **Name of the test:** getPlayerNumbers_onePlayer_return1
  - **State of the System:** playerFields = [field]
  - **Expected output:** return 1

- **TC9: Player fields has two fields ** ( :white-check-mark: )
  - **Name of the test:** getPlayerNumbers_twoPlayers_return2
  - **State of the System:** playerFields = [field, field]
  - **Expected output:** return 2

### Method under test: `getPlayerCreateScene()`
- **TC10: this method is called ** ( :white-check-mark: )
  - **Name of the test**: getPlayerCreateScene_called_success
  - **State of the system**: N/A
  - **Expected output**: called view.createPlayerCreateScene()