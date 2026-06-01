### Method under test: `toggleSelected()`
- **TC1: toggle selected while selected is false** ( :white_check_mark: )
  - **Name of the test:** toggleSelected_CurrentFalse_Success
  - **State of the system:** isSelected = false
  - **Expected output:** isSelected = true
- **TC2: toggle selected while selected is true** ( :white_check_mark: )
  - **Name of the test:** toggleSelected_CurrentTrue_Success
  - **State of the system:** isSelected = true
  - **Expected output:** isSelected = false
### Method under test: `setIsSelected(boolean isSelected)`
- **TC3: set card isSelected to true** ( :x: )
  - **Name of the test:** setIsSelected_setToTrue
  - **State of the system:** isSelected = false
  - **Expected output:** isSelected = true