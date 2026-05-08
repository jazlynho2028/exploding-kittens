# BVA Analysis: Player Class
### Method under test: `getName()`
- **TC1: get name when name is filled** ( x )
    - **State of the system**: Player constructed with name = "Alice"
    - **Expected output**: Returns "Alice"

- **TC2: get name when name is empty** ( x )
    - **State of the system**: Player constructed with name = ""
    - **Expected output**: Returns "Player 1"

### Method under test: `addCardToHand(Card card)`
- **TC3: add card to empty hand** ( x )
    - **State of the system**: player hand is empty; card is non-null and valid 
    - **Expected output**: player hand has one card; hand contains the specified card

- **TC4: add card to hand with one card** ( x )
    - **State of the system**: player hand contains one card; card is non-null and valid 
    - **Expected output**: player hand has two cards; hand contains the specified card

- **TC5: add card to hand with one card** ( x )
    - **State of the system**: player hand contains one card; card is non-null
    - **Expected output**: player hand contains two cards; hand contains specified card 

- **TC6: add card to hand with five cards** ( x )
    - **State of the system**: player hand contains five cards; card is non-null
    - **Expected output**: player hand contains six cards; hand contains specified card 

- **TC7: add card to hand that already had card** ( x )
    - **State of the system**: player hand contains existing card; card is non-null
    - **Expected output**: player hand contains one more of the existing card

- **TC8: add card to hand with duplicate cards** ( x )
    - **State of the system**: player hand has duplicate cards; card is non-null
    - **Expected output**: player hand still has duplicate cards; hand contains specified card

### Method under test: `removeCardFromHand(Card card)`
- **TC9: remove non-existing card from empty hand** ( x )
    - **State of the system**: player hand is empty; card to remove holds any value 
    - **Expected output**: IllegalArgumentException

- **TC10: remove non-existing card from hand with one card** ( x )
    - **State of the system**: player hand has one card; card to remove does not exist in hand 
    - **Expected output**: IllegalArgumentException

- **TC11: remove existing card from hand with one card** ( x )
    - **State of the system**: player hand has one card; card to remove does exist in hand
    - **Expected output**: player hand does not have card anymore 

- **TC12: remove non-existing card from hand with more than one card** ( x )
  - **State of the system**: player hand has more than one card; card to remove does not exist in hand
  - **Expected output**: IllegalArgumentException

- **TC13: remove existing card from hand with more than one card** ( x )
    - **State of the system**: player hand has more than one card; card to remove does exist in hand 
    - **Expected output**: player hand does not have card anymore 