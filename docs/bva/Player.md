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
    - **State of the system**: player hand is empty; card to remove does not exist in hand 
    - **Expected output**: IllegalStateException called with key "error.cardNotInHand"

- **TC6: remove non-existing card from hand with one card** ( :white_check_mark: )
    - **Name of the test**: removeCardFromHand_oneCardAndMissingCard_IllegalStateException
    - **State of the system**: player hand has one card; card to remove does not exist in hand 
    - **Expected output**: IllegalStateException called with key "error.cardNotInHand"

- **TC7: remove existing card from hand with one card** ( :white_check_mark: )
    - **Name of the test**: removeCardFromHand_oneCardAndMatchingCard_handBecomesEmpty
    - **State of the system**: player hand has one card; card to remove does exist in hand
    - **Expected output**: player hand does not have card anymore; player hand size is 0

- **TC8: remove non-existing card from hand with more than one card** ( :white_check_mark: )
    - **Name of the test**: removeCardFromHand_multipleCardsAndMissingCard_IllegalStateException
    - **State of the system**: player hand has more than one card; card to remove does not exist in hand
    - **Expected output**: IllegalStateException called with key "error.cardNotInHand"

- **TC8: remove existing card from hand with more than one card** ( :white_check_mark: )
    - **Name of the test**: removeCardFromHand_multipleCardsAndMatchingCard_cardRemoved
    - **State of the system**: player hand has more than one card; card to remove does exist in hand 
    - **Expected output**: player hand does not have card anymore; player hand size decreased by 1

- **TC10: remove non-existing card from hand with duplicate cards** ( :white_check_mark: )
    - **Name of the test**: removeCardFromHand_duplicateCardsAndMissingCard_IllegalStateException
    - **State of the system**: player hand has duplicate cards; card to remove does not exist in hand
    - **Expected output**: IllegalStateException called with key "error.cardNotInHand"

- **TC11: remove existing card from hand with duplicate cards** ( :white_check_mark: )
    - **Name of the test**: removeCardFromHand_duplicateCardsAndMatchingCard_oneInstanceRemoved
    - **State of the system**: player hand has duplicate cards; card to remove does exist in hand
    - **Expected output**: exactly one instance of the duplicate card is not in hand (the other is still in hand); player hand size decreased by 1

### Method under test: `deselectHandCards()`
- **TC12: deselecting hand with no cards in it** ( :white_check_mark: )
  - **Name of the test**: deselectHandCards_emptyHand_noChanges
  - **State of the system**: player hand is empty, No cards in hand are selected
  - **Expected output**: Method completes successfully, hand remains empty and no exceptions are thrown

- **TC13: Deselecting cards when none are currently selected (multiple cards)** ( :white_check_mark: )
  - **Name of the test**: deselectHandCards_nonEmptyHand_allUnselected
  - **State of the system**: Player hand has card objects, but none have isSelected = true
  - **Expected output**: Method completes successfully, all cards remain unselected

- **TC14: Deselecting cards when one is currently selected (multiple cards)** ( :x: )
  - **Name of the test**: deselectHandCards_nonEmptyHand_allUnselected
  - **State of the system**: Player hand has card objects, one has isSelected = true
  - **Expected output**: Expected output: Method Successful, Every card in the hand has its isSelected property set to false.

- **TC15: Deselecting cards when multiple cards are selected (multiple cards)** ( :x: )
  - **Name of the test**: deselectHandCards_nonEmptyHand_allUnselected
  - **State of the system**: Player hand has multiple card objects, more than one have isSelected = true
  - **Expected output**: Method Successful, Every card in the hand has its isSelected property set to false

- **TC16: Deselecting cards when all cards are currently selected (multiple cards)** ( :x: )
  - **Name of the test**: deselectHandCards_nonEmptyHand_allUnselected
  - **State of the system**: Player hand has multiple card objects, and every single card has isSelected = true
  - **Expected output**: Method Successful, Every card in the hand has its isSelected property set to false

- **TC17: Deselecting cards when no cards are selected (one card)** ( :x: )
  - **Name of the test**: deselectHandCards_nonEmptyHand_allUnselected
  - **State of the system**: Player hand has one card object, it has isSelected = true
  - **Expected output**: Method Successful, Every card in the hand has its isSelected property set to false

- **TC18: Deselecting cards when all cards are selected (one card)** ( :x: )
  - **Name of the test**: deselectHandCards_nonEmptyHand_allUnselected
  - **State of the system**: Player hand has one card object, it has isSelected = true
  - **Expected output**: Method Successful, Every card in the hand has its isSelected property set to false