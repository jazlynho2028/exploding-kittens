### Method under test: `PlayerCreateController(AssetProvider assets, PlayerCreateView view)`
- **TC1: This method is called** ( :white_check_mark: )
  - **Name of the test:** constuctor_called_success
  - **State of the system:** N/A
  - **Expected output:** onError has default empty handling, playerFields is size 2, called bindUI()

### Method under test: `onAddPlayer()`
- **TC2: Adding a field with minimum players** ( :white_check_mark: )
  - **Name of the test:** onAddPlayer_CurrentTwo_Success
  - **State of the system:** playerFields = [TextField, TextField]
  - **Expected output:** playerFields now holds three TextFields, setAddPlayerButtonDisabled gets called with false

- **TC3: Adding the last field allowed, the fourth player** ( :white_check_mark: )
  - **Name of the test:** onAddPlayer_CurrentThree_Success
  - **State of the System:** playerFields = [TextField, TextField, TextField]
  - **Expected output:** playerFields now holds four TextFields, setAddPlayerButtonDisabled gets called with true

- **TC4: Adding a fifth player that's above the maximum** ( :white_check_mark: )
  - **Name of the test:** onAddPlayer_CurrentFour_Failed
  - **State of the System:** PlayerFields = [TextField, TextField, TextField]
  - **Expected output:** calls onError.accept("You cannot have more than 4 players.")

### Method under test: `onConfirmNames()`
- **TC5: Confirming exactly below the minimum limit, 1 blank name player** ( :white_check_mark: )
  - **Name of the test:** onConfirmNames_OnePlayer_Failed
  - **State of the System:** view.getPlayerNamesFromFields() = [""]
  - **Expected output:** calls onError.accept("you need at least 2 players.")

- **TC6: Confirming at the minimum number of players** ( :white_check_mark: )
  - **Name of the test:** onConfirmNames_TwoPlayers_Success
  - **State of the System:** view.getPlayerNamesFromFields() = ["Alice", "Bob"]
  - **Expected output:** onSuccess.run() executes, list of confirmedNames becomes size 2

- **TC7: Confirming correct amount but onSuccess exception gets thrown** ( :white_check_mark: )
  - **Name of the test:** onConfirmNames_onSuccess_Error
  - **State of the System:** view.getPlayerNamesFromFields() = ["Alice", "Bob", "Dave"] onSuccess = error
  - **Expected output:** onSuccess.run() fails, onError.accept(<exception message>) runs

### Method under test: `getPlayerNumbers()`
- **TC8: Player fields has two fields** ( implemented in TC1 )
  - **Name of the test:** buildAndBindUI_called_success
  - **State of the System:** playerFields = [TextField, TextField]
  - **Expected output:** return 2

- **TC9: Player fields has three fields** ( implemented in TC2 )
  - **Name of the test:** onAddPlayer_CurrentTwo_Success
  - **State of the System:** playerFields = [TextField, TextField, TextField]
  - **Expected output:** return 3

- **TC10: Player fields has four fields** ( implemented in TC3 )
  - **Name of the test:** onAddPlayer_CurrentThree_Success
  - **State of the System:** playerFields = [TextField, TextField, TextField, Textfield]
  - **Expected output:** return 4

### Method under test: `onRestartButton()`
- **TC11: This method is called** ( :white_check_mark: )
  - **Name of the test:** onRestartButton_buttonPressed_success
  - **State of the System:** N/A
  - **Expected output:** called onRestart.run()