# BVA Analysis: DeckBuilder Class

### Method under test: `DeckBuilder(int numPlayers)`
- **TC1: Minimum allowed players (parameterized test) ** ( :white_check_mark: )
  - **Name of the test**: constructor_ValidPlayerCounts_PopulatesCorrectDeckSizeAndDefuses
  - **State of the system**: numPlayers = 2
  - **Expected output**: instance is initialized. internal deck has 59 cards, of which 3 are "DEFUSE" cards. deck is shuffled.

- **TC2: Maximum allowed players (parameterized test) ** ( :white_check_mark: )
  - **Name of the test**: constructor_ValidPlayerCounts_PopulatesCorrectDeckSizeAndDefuses
  - **State of the system**: numPlayers = 4
  - **Expected output**: instance is initialized. internal deck has 57 cards, of which 1 is a "DEFUSE" card. deck is shuffled.

- **TC3: Base Game Card Quantities ** ( :white_check_mark: )
  - **Name of the test**: constructor_ValidPlayerCounts_MaintainsStaticBaseCardQuantities
  - **State of the system**: n/a (same card quantity regardless of numPlayers & numPlayers verified in Game class)
  - **Expected output**: deck contains every single base game CardType (excluding DEFUSE) as its static quantity 
    defined in GameConstants (e.g., exactly 3 ATTACK, 1 GODCAT, 0 EXPLODING_KITTENS)

### Helper functions
### Method under test: `calculateDefusesToAdd(int numPlayers)`
- **TC4: Defuse count for minimum player count (2 players) ** ( :white_check_mark: )
    - **Name of the test**: calculateDefusesToAdd_MinimumPlayers_ReturnsThree
    - **State of the system**: numPlayers = 2
    - **Expected output**: returns 4 (5 defuses - 2 players = 3 defuses left)

- **TC5: Defuse count for maximum player count (4 players) ** ( :white_check_mark: )
  - **Name of the test**: calculateDefusesToAdd_MaximumPlayers_ReturnsOne
  - **State of the system**: numPlayers = 4
  - **Expected output**: returns 2 (5 defuses - 4 players = 1 defuses left)

- **TC6: Negative Defuse count calculated (6 players) ** ( :white_check_mark: )
  - **Name of the test**: calculateDefusesToAdd_NegativeDefuses_ThrowsException
  - **State of the system**: numPlayers = 6
  - **Expected output**: throws an error (5 starting defuses - 6 players = -1, throw error)

### Method under test: `createCardID(CardType type, int num)`
- **TC7: Lower boundary valid CardID sequence number of 1 ** ( :white_check_mark: )
  - **Name of the test**: createCardID_LowerValidInput_ReturnsCorrectString
  - **State of the system**: card type = FERAL_CAT, num = 1
  - **Expected output**: returns string "FERALCAT_1"

- **TC8: Upper boundary valid CardID sequence number of 3 ** ( :white_check_mark: )
  - **Name of the test**: createCardID_UpperValidInput_ReturnsCorrectString
  - **State of the system**: card type = ATTACK, num = 3
  - **Expected output**: returns string "ATTACK_3"

- **TC9: Invalid boundary CardID sequence number of 0 ** ( :white_check_mark: )
  - **Name of the test**: createCardID_InvalidInputNum_ThrowsException
  - **State of the system**: card type = MILD_DRAW, num = 0
  - **Expected output**: throws IllegalArgumentException
