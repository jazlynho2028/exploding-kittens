# BVA Analysis: Game Class
### Method under test: `isGameOngoing()`
- **TC1: check before game starts** ( x )
    - **State of the system**: TurnManager constructed with 1 player
    - **Expected output**: false

- **TC2: check after game starts** ( x )
    - **State of the system**: TurnManager constructed with 1 player
    - **Expected output**:true 

### Method under test: `startGame()`
- **TC3: starting game with minimum valid player count** ( x )
    - **State of the system**: Game constructed with 2 player
    - **Expected output**: isGameOngoing() is true; each player has hand of cards

- **TC4: starting game with many players** ( x )
    - **State of the system**: Game constructed with 4 players
    - **Expected output**: isGameOngoing() is true; each player has hand of cards 

- **TC5: starting game with less than 2 players** ( x )
    - **State of the system**: Game constructed with 1 player
    - **Expected output**: throws IllegalStateException

### Method under test: `drawCardAndGiveToPlayer( Card card, Player player )`
- **TC6: draw null card and give to player** ( x )
    - **State of the system**: drawPile has cards; player is valid; card is null
    - **Expected output**: throws IllegalArgumentException

- **TC7: draw one card from pile with one card** ( x )
    - **State of the system**: drawPile has one card; player hand is empty 
    - **Expected output**: drawPile is empty; player hand size = 1; player has drawn card

- **TC8: draw one card from pile with many cards** ( x )
    - **State of the system**: drawPile has multiple cards; player hand is empty
    - **Expected output**: drawPile size decreases by 1; player hand size = 1

- **TC9: draw card from pile with many cards and give to player with existing hand** ( x )
    - **State of the system**: drawPile has cards; player has one card in hand
    - **Expected output**: drawPile size decreases by 1; player hand size = 2 
