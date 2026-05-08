### Method under test: `shuffle()`
- **TC1: Shuffle empty deck** ( :x: )
    - **State of the system**: Deck is empty: []
    - **Expected output**: Deck is still empty: []; orderingChanged = false

- **TC2: Shuffle deck with one card** ( :x: ) 
    - **State of the system**: Deck has exactly one card: [card1]
    - **Expected output**: Deck still has exactly one card: [card1]; orderingChanged = false

- **TC3: Shuffle deck with more than one duplicate card** ( :x: )
    - **State of the system**: Deck has more than one card with duplicates: [card1, card1]
    - **Expected output**: Deck still has two copies of card1: [card1, card1]; orderingChanged = false

- **TC4: Shuffle deck with more than one different card** ( :x: )
    - **State of the system**: Deck has more than one card with different cards: [card1, card2]
    - **Expected output**: Deck still contains card1 and card2; orderingChanged = true (we should implement this so that it will never be the same ordering)

### Method under test: `peekTop()`
- **TC5: Peek top of empty deck** ( :x: )
    - **State of the system**: Deck is empty: []
    - **Expected output**: Returns null; deck is still empty: []

- **TC6: Peek top of deck with one card** ( :x: )
    - **State of the system**: Deck has exactly one card: [card1]
    - **Expected output**: Returns card1; deck is still [card1]

- **TC7: Peek top of deck with more than one different card** ( :x: )
    - **State of the system**: Deck has more than one card with different cards: [card1, card2]
    - **Expected output**: Returns card1; deck is still [card1, card2]

- **TC8: Peek top of deck with more than one duplicate card** ( :x: )
    - **State of the system**: Deck has more than one card with duplicates: [card1, card1]
    - **Expected output**: Returns card1; deck is still [card1, card1]
