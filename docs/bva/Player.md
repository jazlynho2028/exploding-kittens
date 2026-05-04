# BVA Analysis: Player Class
### Method under test: `getName()`
- **TC1: get name when name is filled** ( x )
    - **State of the system**: Player constructed with name = "Alice"
    - **Expected output**: Returns "Alice"

- **TC2: get name when name is empty** ( x )
    - **State of the system**: Player constructed with name = ""
    - **Expected output**: Returns "Player 1"

### Method under test: `addCardToHand(Card card)`
- **TC3: add null card to empty hand** ( x )
    - **State of the system**: player hand is empty; card is null
    - **Expected output**: Throws IllegalArgumentException

- **TC4: add non-null card to empty hand** ( x )
    - **State of the system**: player hand is empty; card is non-null and valid 
    - **Expected output**: player hand has one card; hand contains the specified card

- **TC5: add non-null card to hand with one card** ( x )
    - **State of the system**: player hand contains one card; card is non-null and valid 
    - **Expected output**: player hand has two cards; hand contains the specified card

- **TC6: add non-null card to hand with one card** ( x )
    - **State of the system**: player hand contains one card; card is non-null
    - **Expected output**: player hand contains two cards; hand contains specified card 

- **TC7: add non-null card to hand with five cards** ( x )
    - **State of the system**: player hand contains five cards; card is non-null
    - **Expected output**: player hand contains six cards; hand contains specified card 

- **TC8: add non-null card to hand that already had card** ( x )
    - **State of the system**: player hand contains existing card; card is non-null
    - **Expected output**: player hand contains one more of the existing card

- **TC9: add non-null card to hand with duplicate cards** ( x )
    - **State of the system**: player hand has duplicate cards; card is non-null
    - **Expected output**: player hand still has duplicate cards; hand contains specified card

### Method under test: `removeCardFromHand(Card card)`
- **TC10: remove non-existing card from empty hand** ( x )
    - **State of the system**: player hand is empty; card to remove holds any value 
    - **Expected output**: IllegalArgumentException

- **TC11: remove non-existing card from hand with one card** ( x )
    - **State of the system**: player hand has one card; card to remove does not exist in hand 
    - **Expected output**: IllegalArgumentException

- **TC12: remove existing card from hand with one card** ( x )
    - **State of the system**: player hand has one card; card to remove does exist in hand
    - **Expected output**: player hand does not have card anymore 

- **TC13: remove non-existing card from hand with more than one card** ( x )
  - **State of the system**: player hand has more than one card; card to remove does not exist in hand
  - **Expected output**: IllegalArgumentException

- **TC14: remove existing card from hand with more than one card** ( x )
    - **State of the system**: player hand has more than one card; card to remove does exist in hand 
    - **Expected output**: player hand does not have card anymore 