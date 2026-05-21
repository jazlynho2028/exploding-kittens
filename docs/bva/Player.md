# BVA Analysis: Player Class
### Method under test: `addCardToHand(Card card)`
- **TC1: add card to empty hand** ( :checkmark: )
    - **Name of the test**: addCardToHand_emptyHand_cardAddedToEnd
    - **State of the system**: player hand is empty; card is non-null and valid 
    - **Expected output**: player hand has one card; card at index 0 is the specified card instance

- **TC2: add card to hand with one card** ( :checkmark: )
    - **Name of the test**: addCardToHand_handHasOneCard_cardAddedToEnd
    - **State of the system**: player hand contains one card; card is non-null and valid 
    - **Expected output**: player hand has two cards; card at index 1 (last position) is the specified card instance

- **TC3: add card to hand with five cards** ( :checkmark: )
    - **Name of the test**: addCardToHand_handHasFiveCards_cardAddedToEnd
    - **State of the system**: player hand contains five cards; card is non-null
    - **Expected output**: player hand contains six cards; card at index 5 (last position) is the specified card instance

- **TC4: add card to hand with duplicate cards** ( :checkmark: )
    - **Name of the test**: addCardToHand_handHasDuplicateCards_cardAddedToEnd
    - **State of the system**: player hand has duplicate cards; card is non-null
    - **Expected output**: player hand still has duplicate cards; card at last index (size - 1) is the specified card instance

### Method under test: `removeCardFromHand(Card card)`
- **TC5: remove non-existing card from empty hand** ( :checkmark: )
    - **Name of the test**: removeCardFromHand_emptyHandAndNonExistingCard_throwsIllegalArgumentException
    - **State of the system**: player hand is empty; card to remove does not exist in hand 
    - **Expected output**: GameException thrown with key "error.cardNotInHand"

- **TC6: remove non-existing card from hand with one card** ( :checkmark: )
    - **Name of the test**: removeCardFromHand_handHasOneCardAndNonExistingCard_throwsIllegalArgumentException
    - **State of the system**: player hand has one card; card to remove does not exist in hand 
    - **Expected output**: GameException thrown with key "error.cardNotInHand"

- **TC7: remove existing card from hand with one card** ( :checkmark: )
    - **Name of the test**: removeCardFromHand_handHasOneCardAndExistingCard_cardRemovedAndSizeIsZero
    - **State of the system**: player hand has one card; card to remove does exist in hand
    - **Expected output**: player hand does not have card anymore; player hand size is 0

- **TC8: remove non-existing card from hand with more than one card** ( :checkmark: )
    - **Name of the test**: removeCardFromHand_handHasMultipleCardsAndNonExistingCard_throwsIllegalArgumentException
    - **State of the system**: player hand has more than one card; card to remove does not exist in hand
    - **Expected output**: GameException thrown with key "error.cardNotInHand"

- **TC8: remove existing card from hand with more than one card** ( :checkmark: )
    - **Name of the test**: removeCardFromHand_handHasMultipleCardsAndExistingCard_cardRemovedAndSizeDecreasedByOne
    - **State of the system**: player hand has more than one card; card to remove does exist in hand 
    - **Expected output**: player hand does not have card anymore; player hand size decreased by 1

- **TC10: remove non-existing card from hand with duplicate cards** ( :checkmark: )
    - **Name of the test**: removeCardFromHand_handHasDuplicateCardsAndNonExistingCard_throwsIllegalArgumentException
    - **State of the system**: player hand has duplicate cards; card to remove does not exist in hand
    - **Expected output**: GameException thrown with key "error.cardNotInHand"

- **TC11: remove existing card from hand with duplicate cards** ( :checkmark: )
    - **Name of the test**: removeCardFromHand_handHasDuplicateCardsAndExistingCard_oneInstanceRemovedAndSizeDecreasedByOne
    - **State of the system**: player hand has duplicate cards; card to remove does exist in hand
    - **Expected output**: exactly one instance of the duplicate card is not in hand (the other is still in hand); player hand size decreased by 1