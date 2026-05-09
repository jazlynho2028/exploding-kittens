# BVA Analysis: Player Class
### Method under test: `addCardToHand(Card card)`
- **TC1: add card to empty hand** ( checkmark )
    - **State of the system**: player hand is empty; card is non-null and valid 
    - **Expected output**: player hand has one card; hand contains the specified card

- **TC2: add card to hand with one card** ( checkmark )
    - **State of the system**: player hand contains one card; card is non-null and valid 
    - **Expected output**: player hand has two cards; hand contains the specified card

- **TC3: add card to hand with five cards** ( checkmark )
    - **State of the system**: player hand contains five cards; card is non-null
    - **Expected output**: player hand contains six cards; hand contains specified card 

- **TC4: add card to hand with duplicate cards** ( checkmark )
    - **State of the system**: player hand has duplicate cards; card is non-null
    - **Expected output**: player hand still has duplicate cards; hand contains specified card

### Method under test: `removeCardFromHand(Card card)`
- **TC5: remove non-existing card from empty hand** ( x )
    - **State of the system**: player hand is empty; card to remove holds any value 
    - **Expected output**: IllegalArgumentException

- **TC6: remove non-existing card from hand with one card** ( x )
    - **State of the system**: player hand has one card; card to remove does not exist in hand 
    - **Expected output**: IllegalArgumentException

- **TC7: remove existing card from hand with one card** ( x )
    - **State of the system**: player hand has one card; card to remove does exist in hand
    - **Expected output**: player hand does not have card anymore 

- **TC9: remove non-existing card from hand with more than one card** ( x )
  - **State of the system**: player hand has more than one card; card to remove does not exist in hand
  - **Expected output**: IllegalArgumentException

- **TC10: remove existing card from hand with more than one card** ( x )
    - **State of the system**: player hand has more than one card; card to remove does exist in hand 
    - **Expected output**: player hand does not have card anymore 