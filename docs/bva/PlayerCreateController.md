# BVA Analysis: PlayerCreateController Class
### Method under test: `buildPlayerCreateScene()`
- **TC1: This method is called** ( :white_check_mark: )
  - **Name of the test:** buildPlayerCreateScene_called_success
  - **State of the system:** N/A
  - **Expected output:** 
    - buildDependentUI is called
    - bindUI is called
    - returns view.createPlayerCreateScene

### Method under test: `buildDependentUI()`
- **TC2: This method is called** ( :white_check_mark: )
  - **Name of the test:** buildDependentUI_called_success
  - **State of the system:** GameConstants.MIN_PLAYERS = 2
  - **Expected output:** onAddPlayer twice is called

### Method under test: `bindUI()`
- **TC3: This method is called** ( :white_check_mark: )
  - **Name of the test:** bindUI_called_success
  - **State of the system:** N/A
  - **Expected output:** 
    - view.bindAddPlayerButton is called
    - view.bindConfirmButton is called
    - view.bindRestartButton is called

### Method under test: `onAddPlayer()`
- **TC4: Add a first player** ( :white_check_mark: )
  - **Name of the test:** onAddPlayer_add1To4_success
  - **State of the system:** playerFieldsCount = 0
  - **Expected output:** playerFieldsCount = 1
    - view.addPlayerField(1) is called
    - view.setAddPlayerButtonDisabled with false is called

- **TC5: Add a second player** ( :white_check_mark: )
  - **Name of the test:** onAddPlayer_add1To4_success
  - **State of the system:** playerFieldsCount = 1
  - **Expected output:** 
    - playerFieldsCount = 2
    - view.addPlayerField(2) is called

- **TC6: Add a third player** ( :white_check_mark: )
  - **Name of the test:** onAddPlayer_add1To4_success
  - **State of the system:** playerFieldsCount = 2
  - **Expected output:** 
    - playerFieldsCount = 3
    - view.addPlayerField(3) is called

- **TC7: Add a fourth player** ( :white_check_mark: )
  - **Name of the test:** onAddPlayer_add1To4_success
  - **State of the System:** playerFieldsCount = 3
  - **Expected output:** 
    - playerFieldsCount = 4
    - view.addPlayerField(4) is called
    - view.setAddPlayerButtonDisabled with true is called

- **TC8: Add a fifth player that's above the maximum** ( :white_check_mark: )
  - **Name of the test:** onAddPlayer_currentFour_noChange
  - **State of the System:** playerFieldsCount = 4
  - **Expected output:** playerFieldsCount = 4

### Method under test: `onConfirmNames()`
- **TC9: Confirm names success** ( :white_check_mark: )
  - **Name of the test:** onConfirmNames_called_success
  - **State of the System:** N/A
  - **Expected output:**
    - populateConfirmedNames is called
    - onSuccess.run is called

- **TC10: Confirm names failed** ( :white_check_mark: )
  - **Name of the test:** onConfirmNames_called_failed
  - **State of the System:** onSuccess.run throws RuntimeException "An error occurred."
  - **Expected output:** 
    - populateConfirmedNames is called
    - onError accepts exception

### Method under test: `populateConfirmedNames()`
- **TC11: Empty names** ( :white_check_mark: )
  - **Name of the test:** populateConfirmedNames_givenInputsFromView_populatedAndTrimmed
  - **State of the System:** inputsFromView = []
  - **Expected output:** confirmedNames = []

- **TC12: One name** ( :white_check_mark: )
  - **Name of the test:** populateConfirmedNames_givenInputsFromView_populatedAndTrimmed
  - **State of the System:** inputsFromView = ["Steve"]
  - **Expected output:** confirmedNames = ["Steve"]

- **TC13: Two different names, one with space at end** ( :white_check_mark: )
  - **Name of the test:** populateConfirmedNames_givenInputsFromView_populatedAndTrimmed
  - **State of the System:** inputsFromView = ["Steve ", "Steve"]
  - **Expected output:** confirmedNames = ["Steve", "Steve"]

- **TC14: Two duplicate names** ( :white_check_mark: )
  - **Name of the test:** populateConfirmedNames_givenInputsFromView_populatedAndTrimmed
  - **State of the System:** inputsFromView = [" Steve ", " Steve "]
  - **Expected output:** confirmedNames = ["Steve", "Steve"]

- **TC15: Two names with one empty** ( :white_check_mark: )
  - **Name of the test:** populateConfirmedNames_givenInputsFromView_populatedAndTrimmed
  - **State of the System:** inputsFromView = ["", " Steve"]
  - **Expected output:** confirmedNames = ["Steve"]

