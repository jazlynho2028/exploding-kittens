# BVA Analysis: Player Class

### Method under test: `addCardToHand(Card card)`
- **TC1: add card to empty hand** ( :white_check_mark: )
    - **Name of the test**: addCardToHand_emptyHand_cardAddedToEnd
    - **State of the system**: player hand is empty; card is non-null and valid 
    - **Expected output**: player hand has one card; card at index 0 is the specified card instance

- **TC2: add card to hand with one card** ( :white_check_mark: )
    - **Name of the test**: addCardToHand_handHasOneCard_cardAddedToEnd
    - **State of the system**: player hand contains one card; card is non-null and valid 
    - **Expected output**: player hand has two cards; card at index 1 (last position) is the specified card instance

- **TC3: add card to hand with five cards** ( :white_check_mark: )
    - **Name of the test**: addCardToHand_handHasFiveCards_cardAddedToEnd
    - **State of the system**: player hand contains five cards; card is non-null
    - **Expected output**: player hand contains six cards; card at index 5 (last position) is the specified card instance

- **TC4: add card to hand with duplicate cards** ( :white_check_mark: )
    - **Name of the test**: addCardToHand_handHasDuplicateCards_cardAddedToEnd
    - **State of the system**: player hand has duplicate cards; card is non-null
    - **Expected output**: player hand still has duplicate cards; card at last index (size - 1) is the specified card instance

### Method under test: `removeCardFromHand(Card card)`
- **TC5: remove non-existing card from empty hand** ( :white_check_mark: )
    - **Name of the test**: removeCardFromHand_emptyHand_IllegalStateException
    - **State of the system**: 
      - player hand is empty
      - card to remove does not exist in hand 
    - **Expected output**: IllegalStateException called with key "error.cardNotInHand"

- **TC6: remove non-existing card from hand with one card** ( :white_check_mark: )
    - **Name of the test**: removeCardFromHand_oneCardAndMissingCard_IllegalStateException
    - **State of the system**: 
      - player hand has one card
      - card to remove does not exist in hand 
    - **Expected output**: IllegalStateException called with key "error.cardNotInHand"

- **TC7: remove existing card from hand with one card** ( :white_check_mark: )
    - **Name of the test**: removeCardFromHand_oneCardAndMatchingCard_handBecomesEmpty
    - **State of the system**: 
      - player hand has one card
      - card to remove does exist in hand
    - **Expected output**: 
      - player hand does not have card anymore
      - player hand size is 0
      - winnerWinnerActivatedRound = 0

- **TC8: remove non-existing card from hand with more than one card** ( :white_check_mark: )
    - **Name of the test**: removeCardFromHand_multipleCardsAndMissingCard_IllegalStateException
    - **State of the system**: 
      - player hand has more than one card
      - card to remove does not exist in hand
    - **Expected output**: IllegalStateException called with key "error.cardNotInHand"

- **TC8: remove existing card from hand with more than one card** ( :white_check_mark: )
    - **Name of the test**: removeCardFromHand_multipleCardsAndMatchingCard_cardRemoved
    - **State of the system**: player hand has more than one card; card to remove does exist in hand 
    - **Expected output**: 
      - player hand does not have card anymore
      - player hand size decreased by 1
      - winnerWinnerActivatedRound = 0

- **TC10: remove non-existing card from hand with duplicate cards** ( :white_check_mark: )
    - **Name of the test**: removeCardFromHand_duplicateCardsAndMissingCard_IllegalStateException
    - **State of the system**: 
      - player hand has duplicate cards
      - card to remove does not exist in hand
    - **Expected output**: IllegalStateException called with key "error.cardNotInHand"

- **TC11: remove existing card from hand with duplicate cards** ( :white_check_mark: )
    - **Name of the test**: removeCardFromHand_duplicateCardsAndMatchingCard_oneInstanceRemoved
    - **State of the system**: 
      - player hand has duplicate cards
      - card to remove does exist in hand
    - **Expected output**: 
      - exactly one instance of the duplicate card is not in hand (the other is still in hand)
      - player hand size decreased by 1
      - winnerWinnerActivatedRound = 0

### Method under test: `deselectHandCards()`
- **TC12: deselecting hand with no cards in it** ( :white_check_mark: )
  - **Name of the test**: deselectHandCards_emptyHand_noChanges
  - **State of the system**: 
    - player hand is empty
    - No cards in hand are selected
  - **Expected output**: Method completes successfully, hand remains empty and no exceptions are thrown

- **TC13: Deselecting cards when none are currently selected (multiple cards)** ( :white_check_mark: )
  - **Name of the test**: deselectHandCards_nonEmptyHand_allUnselected
  - **State of the system**: Player hand has card objects, but none have isSelected = true
  - **Expected output**: Method completes successfully, all cards remain unselected

- **TC14: Deselecting cards when one is currently selected (multiple cards)** ( :white_check_mark: )
  - **Name of the test**: deselectHandCards_nonEmptyHand_allUnselected
  - **State of the system**: Player hand has card objects, one has isSelected = true
  - **Expected output**: Expected output: Method Successful, Every card in the hand has its isSelected property set to false.

- **TC15: Deselecting cards when multiple cards are selected (multiple cards)** ( :white_check_mark: )
  - **Name of the test**: deselectHandCards_nonEmptyHand_allUnselected
  - **State of the system**: Player hand has multiple card objects, more than one have isSelected = true
  - **Expected output**: Method Successful, Every card in the hand has its isSelected property set to false

- **TC16: Deselecting cards when all cards are currently selected (multiple cards)** ( :white_check_mark: )
  - **Name of the test**: deselectHandCards_nonEmptyHand_allUnselected
  - **State of the system**: Player hand has multiple card objects, and every single card has isSelected = true
  - **Expected output**: Method Successful, Every card in the hand has its isSelected property set to false

- **TC17: Deselecting cards when no cards are selected (one card)** ( :white_check_mark: )
  - **Name of the test**: deselectHandCards_nonEmptyHand_allUnselected
  - **State of the system**: Player hand has one card object, it has isSelected = true
  - **Expected output**: Method Successful, Every card in the hand has its isSelected property set to false

- **TC18: Deselecting cards when all cards are selected (one card)** ( :white_check_mark: )
  - **Name of the test**: deselectHandCards_nonEmptyHand_allUnselected
  - **State of the system**: Player hand has one card object, it has isSelected = true
  - **Expected output**: Method Successful, Every card in the hand has its isSelected property set to false

### Method under test: `toggleSelectedHandCardAt()`
- **TC19: Toggle index less than zero boundary check** ( :white_check_mark: )
  - **Name of the test**: toggleSelectedHandCardAt_indexLessThanZero_callsException
  - **State of the system**: 
    - Player hand can have any number of cards
    - index provided is -1
  - **Expected output**: IllegalArgumentException called with "error.invalidHandCardIndex"

- **TC20: Toggle valid lower boundary index on a non-empty hand** ( :white_check_mark: )
  - **Name of the test**: toggleSelectedHandCardAt_validLowerBoundIndex_cardToggled
  - **State of the system**: 
    - Player hand can have any number of cards
    - index provided is 0
  - **Expected output**: Method executes successfully; card at index 0 changes its selection state

- **TC21: Toggle index exactly equal to hand size upper boundary check** ( :white_check_mark: )
  - **Name of the test**: toggleSelectedHandCardAt_indexUpperBound_cardToggled
  - **State of the system**: 
    - Player hand has 2
    - index provided is equal to 1
  - **Expected output**: Method executes successfully; card at index 1 changes its selection state

- **TC22: Toggle index greater than hand size boundary check** ( :white_check_mark: )
  - **Name of the test**: toggleSelectedHandCardAt_indexGreaterThanHandSize_callsException
  - **State of the system**: 
    - Player hand has 2 cards
    - index provided is greater than 1
  - **Expected output**: IllegalArgumentException called with "error.invalidHandCardIndex"

- **TC23: Toggle index exactly equal to hand size upper boundary check** ( :white_check_mark: )
  - **Name of the test**: toggleSelectedHandCardAt_indexExactlyEqualToHandSize_throwsException
  - **State of the system**: 
    - Player hand has 2 cards
    - index provided is equal to 2 (index == hand.size())
  - **Expected output**: IllegalArgumentException called with "error.invalidHandCardIndex"

- **TC24: Toggle index zero on an empty hand boundary check** ( :white_check_mark: )
  - **Name of the test**: toggleSelectedHandCardAt_emptyHandIndexZero_throwsException
  - **State of the system**: 
    - Player hand has 0 cards
    - index provided is 0
  - **Expected output**: IllegalArgumentException called with "error.invalidHandCardIndex"

### Method under test: `getSelectedCards()`
- **TC25: Get selected cards from empty hand** ( :white_check_mark: )
  - **Name of the test**: getSelectedCards_emptyHand_returnsEmptyList
  - **State of the system**: player hand is empty
  - **Expected output**: returns an empty list

- **TC26: Get selected cards when no cards are selected** ( :white_check_mark: )
  - **Name of the test**: getSelectedCards_oneCardUnselected_returnsEmptyList
  - **State of the system**: 
    - player hand has one card
    - card has isSelected = false
  - **Expected output**: returns an empty list

- **TC27: Get selected cards when the card is selected** ( :white_check_mark: )
  - **Name of the test**: getSelectedCards_oneCardSelected_returnsListWithCard
  - **State of the system**:
    - player hand has one card
    - card has isSelected = true
  - **Expected output**: returns a list containing exactly that one card

- **TC28: Get selected cards when no cards are selected** ( :white_check_mark: )
  - **Name of the test**: getSelectedCards_multipleCardsNoneSelected_returnsEmptyList
  - **State of the system**: 
    - player hand has three cards
    - all cards have isSelected = false
  - **Expected output**: returns an empty list

- **TC29: Get selected cards when more than one card is selected** ( :white_check_mark: )
  - **Name of the test**: getSelectedCards_multipleCardsSomeSelected_returnsOnlySelectedCards
  - **State of the system**: 
    - player hand has three cards
    - some but not all have isSelected = true (two cards have isSelected = true, one card has isSelected = false)
  - **Expected output**: returns a list containing only the two selected cards; the one unselected card is not included

- **TC30: Get selected cards when more than one card is selected** ( :white_check_mark: )
  - **Name of the test**: getSelectedCards_multipleCardsAllSelected_returnsAllCards
  - **State of the system**: 
    - player hand has three cards
    - every card has isSelected = true
  - **Expected output**: returns a list containing all three cards in the hand

### Method under test: `getHandIds()`
- **TC31: Get hand IDs from empty hand** ( :white_check_mark: )
  - **Name of the test**: getHandIds_emptyHand_returnsEmptyList
  - **State of the system**: player hand is empty
  - **Expected output**: returns an empty list

- **TC32: Get hand IDs from hand with one card** ( :white_check_mark: )
  - **Name of the test**: getHandIds_oneCard_returnsListWithOneId
  - **State of the system**: player hand has one card with id
  - **Expected output**: returns a list containing exactly that card's ID

- **TC33: Get hand IDs from hand with more than one card** ( :white_check_mark: )
  - **Name of the test**: getHandIds_multipleCards_returnsAllIdsInOrder
  - **State of the system**: player hand has multiple cards each with distinct known IDs
  - **Expected output**: returns a list of all card IDs in the same order as the hand

- **TC34: Get hand IDs from hand with duplicate cards** ( :white_check_mark: )
  - **Name of the test**: getHandIds_duplicateCards_returnsDuplicateIds
  - **State of the system**: player hand has two duplicate cards with the same ID
  - **Expected output**: returns a list of all cards where the duplicate ID appears twice for the two duplicate cards 

### Method under test: `getName()`
- **TC35: Baseline validation of name property** ( :white_check_mark: )
  - **Name of the test**: getName_validName_returnsExactString
  - **State of the system**: Player initialized with a specific name string (e.g., "Alice")
  - **Expected output**: getName() returns exactly "Alice"

### Method under test: `isAlive()`
- **TC36: Player is alive** ( :white_check_mark: )
  - **Name of the test**: isAlive_playerIsAlive_returnTrue
  - **State of the system**: Player is alive
  - **Expected output**: return true

- **TC37: Player is dead** ( :white_check_mark: )
  - **Name of the test**: isAlive_playerIsDead_returnFalse
  - **State of the system**: Player is dead
  - **Expected output**: return false

### Method under test: `eliminate()`
- **TC38: Player is alive** ( implemented in TC37 )
  - **Name of the test**: isAlive_playerIsDead_returnFalse
  - **State of the system**: Player is alive
  - **Expected output**: Player is dead

- **TC39: Player is dead** ( :white_check_mark: )
  - **Name of the test**: eliminatePlayer_playerCreated_setIsAliveToFalse
  - **State of the system**: Player is dead
  - **Expected output**: Player is dead

### Method under test: `activateWinnerWinnerFromRound(int round)`
- **TC40: Round 0** ( :white_check_mark: )
  - **Name of the test**: activateWinnerWinnerFromRound_roundZero_failed
  - **State of the system**: round = 0
  - **Expected output**: throw IllegalArgumentException "error.invalidRound"

- **TC41: Round 1** ( :white_check_mark: )
  - **Name of the test**: activateWinnerWinnerFromRound_validRound_setWinnerWinnerActivatedRound
  - **State of the system**: round = 1
  - **Expected output**: winnerWinnerActivatedRound = 1

- **TC42: Round 2** ( :white_check_mark: )
  - **Name of the test**: activateWinnerWinnerFromRound_validRound_setWinnerWinnerActivatedRound
  - **State of the system**: round = 2
  - **Expected output**: winnerWinnerActivatedRound = 2

### Method under test: `isWinnerWinnerActivated()`
- **TC43: Round 0** ( :white_check_mark: )
  - **Name of the test**: isWinnerWinnerActivated_roundZero_returnFalse
  - **State of the system**: winnerWinnerActivatedRound = 0
  - **Expected output**: return false

- **TC44: Round 1** ( :white_check_mark: )
  - **Name of the test**: isWinnerWinnerActivated_roundOne_returnTrue
  - **State of the system**: winnerWinnerActivatedRound = 1
  - **Expected output**: return true