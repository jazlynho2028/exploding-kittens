# BVA Analysis: DeckBuilder Class

### Method under test: `InitializeDeck`
- **TC1: Minimum allowed players ** ( :white_check_mark: )
  - **Name of the test**: initializeDeck_MinimumPlayers_AppendsThreeDefuses
  - **State of the system**: stateless initialization via empty constructor
  - **Expected output**: deck has 59 cards, of which 3 are "DEFUSE" cards. deck is shuffled.

- **TC2: Maximum allowed players ** ( :x: )
  - **Name of the test**: initializeDeck_MaximumPlayers_AppendsOneDefuse
  - **State of the system**: stateless initialization via empty constructor
  - **Expected output**: deck has 57 cards, of which 1 is a "DEFUSE" card. deck is shuffled.

### Method under test: `InitializeDeckWithoutDefuses()`
- **TC3: Verify size of base deck ** ( :white_check_mark: )
  - **Name of the test**: initializeDeckWithoutDefuses_TotalCardCount_EqualsBaselineConstant
  - **State of the system**: no inputs because deck of non-defuse cards does not depend on player count
  - **Expected output**: returns a list containing exactly 56 baseline cards.

- **TC4: Single-card counts ** ( :white_check_mark: )
  - **Name of the test**: initializeDeckWithoutDefuses_SingleCountCards_PopulateCorrectQuantitiesAndIDs
  - **State of the system**: no inputs because deck of non-defuse cards does not depend on player count
  - **Expected output**: validates that all 1-count cards (e.g., `MILD_DRAW`, `GODCAT`, `CATOMIC_BOMB`) 
    only exist once in deck and are mapped sequentially to cardID containing 1.

- **TC5: Double-card counts ** ( :white_check_mark: )
  - **Name of the test**: initializeDeckWithoutDefuses_DoubleInstanceCards_PopulateCorrectQuantitiesAndIDs
  - **State of the system**: no inputs because deck of non-defuse cards does not depend on player count
  - **Expected output**: validates that all double-allocation cards (`SUPER_SKIP`) exist exactly twice 
    and scale string sequence IDs sequentially from 1 to 2.

- **TC6: Triple-card counts ** ( :white_check_mark: )
  - **Name of the test**: initializeDeckWithoutDefuses_TripleInstanceCards_PopulateCorrectQuantitiesAndIDs
  - **State of the system**: no inputs because deck of non-defuse cards does not depend on player count
  - **Expected output**: validates that all triple-allocation cards (`ATTACK`, `SKIP`, `CLONE`, `SWAP_TOP_AND_BOTTOM`,
    `DRAW_FROM_THE_BOTTOM`) exist exactly three times and scale sequence IDs sequentially from 1 to 3.

- **TC7: Quadruple-card counts ** ( :white_check_mark: )
  - **Name of the test**: initializeDeckWithoutDefuses_QuadrupleInstanceCards_PopulateCorrectQuantitiesAndIDs
  - **State of the system**: no inputs because deck of non-defuse cards does not depend on player count
  - **Expected output**: validates that all quadruple-allocation cards (`FERAL_CAT`, `SEE_THE_FUTURE`, `SHUFFLE`,
    `TARGETED_ATTACK`, and `CAT_CARD` variants) exist exactly three times and scale sequence IDs sequentially from 1 to 3.

### Helper functions
### Method under test: `calculateDefusesToAdd(int numPlayers)`
- **TC8: Defuse count for minimum player count (2 players) ** ( :white_check_mark: )
    - **Name of the test**: calculateDefusesToAdd_MinimumPlayers_ReturnsThree
    - **State of the system**: numPlayers = 2
    - **Expected output**: returns 4 (5 defuses - 2 players = 3 defuses left)

- **TC9: Defuse count for maximum player count (4 players) ** ( :white_check_mark: )
  - **Name of the test**: calculateDefusesToAdd_MaximumPlayers_ReturnsOne
  - **State of the system**: numPlayers = 4
  - **Expected output**: returns 2 (5 defuses - 4 players = 1 defuses left)

- **TC10: Negative Defuse count calculated (6 players) ** ( :white_check_mark: )
  - **Name of the test**: calculateDefusesToAdd_NegativeDefuses_ThrowsException
  - **State of the system**: numPlayers = 6
  - **Expected output**: throws an error (5 starting defuses - 6 players = -1, throw error)

### Method under test: `createCardID(CardType type, int num)`
- **TC11: Lower boundary valid CardID sequence number of 1 ** ( :white_check_mark: )
  - **Name of the test**: createCardID_LowerValidInput_ReturnsCorrectString
  - **State of the system**: card type = FERAL_CAT, num = 1
  - **Expected output**: returns string "FERALCAT_1"

- **TC12: Upper boundary valid CardID sequence number of 3 ** ( :white_check_mark: )
  - **Name of the test**: createCardID_UpperValidInput_ReturnsCorrectString
  - **State of the system**: card type = ATTACK, num = 3
  - **Expected output**: returns string "ATTACK_3"

- **TC13: Invalid boundary CardID sequence number of 0 ** ( :white_check_mark: )
  - **Name of the test**: createCardID_InvalidInputNum_ThrowsException
  - **State of the system**: card type = MILD_DRAW, num = 0
  - **Expected output**: throws IllegalArgumentException
