# BVA Analysis: Game Class
### Method under test: `startGame()`
- **TC1: start game with minimum valid players** ( x )
  - **State of the system**: Game constructed with two player names; isGameOngoing is false
  - **Expected output**: 
    - isGameOngoing is true
    - players list has length 2
    - each player hand has 8 cards (1 Defuse, 7 other) 
    - drawPile contains N-1 = 1 Exploding Kitten card
    - drawPile contains 6-N = 4 Defuse cards
    - turnManager initialized at index 0

- **TC2: start game with maximum valid players** ( x )
  - **State of the system**: Game constructed with four player names; isGameOngoing is false
  - **Expected output**:
    - isGameOngoing is true
    - players list has length 4
    - each player hand has 8 cards (1 Defuse, 7 other) 
    - drawPile contains N-1 = 3 Exploding Kitten cards
    - drawPile contains 6-N = 2 Defuse cards 
    - turnManager initialized at index 0

- **TC3: start game with more than one valid players** ( x )
  - **State of the system**: Game constructed with three player names; isGameOngoing is false
  - **Expected output**:
    - isGameOngoing is true
    - players list has length 3
    - each player has 8 cards in hand (1 Defuse, 7 other)
    - drawPile contains N-1 = 2 Exploding Kitten cards
    - drawPile contains 6-N = 3 Defuse cards
    - turnManager initialized at index 0

- **TC4: start game with too little players** ( x )
  - **State of the system**: Game constructed with 1 player name; isGameOngoing is false
  - **Expected output**: IllegalArgumentException

- **TC5: start game with too many players** ( x )
  - **State of the system**: Game constructed with 5 player names; isGameOngoing is false
  - **Expected output**: IllegalArgumentException

### Method under test: `getIsBeforeDraw()`

- **TC6: draw availability immediately after game starts** ( x )
  - **State of the system**: startGame completed; isGameOngoing is true
  - **Expected output**: true

- **TC7: draw availability after player draws a card** ( x )
  - **State of the system**: canDraw is true; drawFromPile() is called
  - **Expected output**: false

- **TC8: draw availability after turn advances** ( x )
  - **State of the system**: canDraw is false; advanceTurn() is called
  - **Expected output**: true for new player

- **TC9: draw availability after player plays a card** ( x )
  - **State of the system**: canDraw is true; playSelectedCards() is called with non-turn-ending card
  - **Expected output**: true 

- **TC10: draw availability when game is not ongoing** ( x )
  - **State of the system**: isGameOngoing is false
  - **Expected output**: false

- **TC12: draw availability at the end of the turn rotation** ( x )
  - **State of the system**: canDraw is false; advanceTurn() is called 
  - **Expected output**: true for player at index 0
