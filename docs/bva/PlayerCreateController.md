### Method under test: `buildPlayerCreateScene()`
- **TC1: This method is called** ( :white_check_mark: )
  - **Name of the test:** buildPlayerCreateScene_called_success
  - **State of the system:** N/A
  - **Expected output:** called buildDependentUI, bindUI, returns view.createPlayerCreateScene

### Method under test: `buildDependentUI()`
- **TC2: This method is called** ( :x: )
  - **Name of the test:**
  - **State of the system:** GameConstants.MIN_PLAYERS = 2
  - **Expected output:** called onAddPlayer twice

### Method under test: `bindUI()`
- **TC3: This method is called** ( :x: )
  - **Name of the test:**
  - **State of the system:** N/A
  - **Expected output:** called view.bindAddPlayerButton, view.bindConfirmButton, view.bindRestartButton

### Method under test: `onAddPlayer()`
- **TC4: Add a first player** ( :x: )
  - **Name of the test:** onAddPlayer_currentZero_success
  - **State of the system:** playerFieldsCount = 0
  - **Expected output:** playerFieldsCount = 1, called view.addPlayerField(1), setAddPlayerButtonDisabled gets called with false

- **TC5: Add a second player** ( :x: )
  - **Name of the test:** onAddPlayer_currentOne_success
  - **State of the system:** playerFieldsCount = 1
  - **Expected output:** playerFieldsCount = 2, called view.addPlayerField(2), setAddPlayerButtonDisabled gets called with false

- **TC6: Add a fourth player** ( :x: )
  - **Name of the test:** onAddPlayer_currentThree_success
  - **State of the System:** playerFieldsCount = 3
  - **Expected output:** playerFieldsCount = 4, called view.addPlayerField(4), setAddPlayerButtonDisabled gets called with true

- **TC7: Add a fifth player that's above the maximum** ( :x: )
  - **Name of the test:** onAddPlayer_currentFour_failed
  - **State of the System:** playerFieldsCount = 4
  - **Expected output:** playerFieldsCount = 4

### Method under test: `onConfirmNames()`
- **TC8: Confirm names success** ( :x: )
  - **Name of the test:** onConfirmNames_called_success
  - **State of the System:** N/A
  - **Expected output:** called populateConfirmedNames, onSuccess.run

- **TC9: Confirm names failed** ( :x: )
  - **Name of the test:** onConfirmNames_called_failed
  - **State of the System:** N/A
  - **Expected output:** called populateConfirmedNames, onSuccess.run throws Exception "An error occurred." and onError accepts it

### Method under test: `populateConfirmedNames()`
- **TC10: Empty names** ( :x: )
  - **Name of the test:** populateConfirmedNames_empty_empty
  - **State of the System:** inputsFromView = []
  - **Expected output:** confirmedNames = []

- **TC11: One name** ( :x: )
  - **Name of the test:** populateConfirmedNames_oneName_oneName
  - **State of the System:** inputsFromView = ["Steve"]
  - **Expected output:** confirmedNames = ["Steve"]

- **TC12: Two different names, one with space at end** ( :x: )
  - **Name of the test:** populateConfirmedNames_twoDifferentNames_twoNamesWithSpaceTrimmed
  - **State of the System:** inputsFromView = ["Steve ", "Steve"]
  - **Expected output:** confirmedNames = ["Steve", "Steve"]

- **TC13: Two duplicate names** ( :x: )
  - **Name of the test:** populateConfirmedNames_twoEqualNames_twoEqualNames
  - **State of the System:** inputsFromView = ["Steve", "Steve"]
  - **Expected output:** confirmedNames = ["Steve", "Steve"]

