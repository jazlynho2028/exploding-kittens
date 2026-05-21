# BVA Analysis: Game Class
### Method under test: `startGame()`
- **TC1: start game with minimum valid players** ( x )
  - **State of the system**: Game constructed with two player names
  - **Expected output**:
    - players list has length 2
    - isGameOngoing is true
    - canDraw is false
    - isFaceUp is false
    - drawPile is initialized as empty collection 
    - discardPile initialized as empty collection 
    - turnManager is null

- **TC2: start game with maximum valid players** ( x )
  - **State of the system**: Game constructed with four player names
  - **Expected output**:
    - players list has length 4
    - isGameOngoing is true
    - canDraw is false
    - isFaceUp is false
    - drawPile initialized as empty collection 
    - discardPile initialized as empty collection 
    - turnManager is null

- **TC3: start game with too little players** ( x )
  - **State of the system**: Game constructed with 1 player name
  - **Expected output**: IllegalArgumentException

- **TC4: start game with too many players** ( x )
  - **State of the system**: Game constructed with 5 player names
  - **Expected output**: IllegalArgumentException

- **TC5: start game with null player list** ( x )
  - **State of the system**: null passed as the player names instead of a true collection 
  - **Expected output**: IllegalArgumentException (Null pointer case) 

### Method under test: `startGame()`

- **TC6: start game with minimum valid players** ( x )
  - **State of the system**: Game successfully constructed with 2 player names; isGameOngoing is false
  - **Expected output**: 
    - isGameOngoing is true
    - canDraw is true
    - players list has length 2
    - each player hand has 8 cards (1 Defuse, 7 other) 
    - drawPile contains N-1=1 Exploding Kitten card
    - drawPile contains 6-N=4 Defuse cards
    - turnManager initialized at array index 0

- **TC7:start game with maximum valid players** ( x )
  - **State of the system**: Game successfully constructed with 4 player names; isGameOngoing is false
  - **Expected output**: 
    - isGameOngoing is true
    - canDraw is true
    - players list has length 4
    - each player hand as 8 cards (1 Defuse, 7 other) 
    - drawPile contains N-1=3 Exploding Kitten card
    - drawPile contains 6-N=2 Defuse cards
    - turnManager initialized at array index 0

- **TC8: start game with more than one valid player** ( x )
  - **State of the system**: Game successfully constructed with 3 player names; isGameOngoing is false
  - **Expected output**:
    - isGameOngoing is true
    - canDraw is true
    - players list has length 3
    - each player hand has 8 cards (1 Defuse, 7 other)
    - drawPile contains N-1=2 Exploding Kitten card
    - drawPile contains 6-N=3 Defuse cards
    - turnManager initialized at array index 0