### Method under test: `shuffle()`
- **TC1: Shuffle empty deck** ( :white_check_mark: )
  - **Name of the test**: `shuffle_emptyDeck_deckStaysEmpty`
  - **State of the system**: Deck is empty: []
  - **Expected output**: Deck is still empty: []; deck size is 0

- **TC2: Shuffle deck with one card** ( :white_check_mark: )
  - **Name of the test**: `shuffle_oneCardDeck_deckOrderUnchanged`
  - **State of the system**: Deck has exactly one card: [card1]
  - **Expected output**: Deck is still [card1]; deck size is 1

- **TC3: Shuffle deck with more than one different card using controlled random value** ( :white_check_mark: )
  - **Name of the test**: `shuffle_multipleDifferentCards_controlledRandomReordersDeck`
  - **State of the system**: Deck has more than one different card: [card2, card1]; mocked random value returns 0
  - **Expected output**: Deck still contains card1 and card2; card1 is at index 0 after shuffle

- **TC4: Shuffle deck with more than one duplicate card using controlled random values** ( :white_check_mark: )
  - **Name of the test**: `shuffle_multipleDuplicateCards_controlledRandomReordersDeck`
  - **State of the system**: Deck has more than one card with duplicates: [card1, card1, card2]; mocked random values return 0 and 0
  - **Expected output**: Deck becomes [card1, card2, card1]; deck size is 3

### Method under test: `peekTop()`
- **TC5: Peek top of empty deck** ( :white_check_mark: )
  - **Name of the test**: `peekTop_emptyDeck_throwsUnsupportedOperationException`
  - **State of the system**: Deck is empty: []
  - **Expected output**: Throws UnsupportedOperationException; deck is still empty: []

- **TC6: Peek top of deck with one card** ( :white_check_mark: )
  - **Name of the test**: `peekTop_oneCardDeck_returnsTopCard`
  - **State of the system**: Deck has exactly one card: [card1]
  - **Expected output**: Returns card1; deck is still [card1]

- **TC7: Peek top of deck with more than one different card** ( :white_check_mark: )
  - **Name of the test**: `peekTop_multipleDifferentCards_returnsTopCard`
  - **State of the system**: Deck has more than one card with different cards: [card1, card2]
  - **Expected output**: Returns card1; deck is still [card1, card2]

- **TC8: Peek top of deck with more than one duplicate card** ( :white_check_mark: )
  - **Name of the test**: `peekTop_multipleDuplicateCards_returnsTopCard`
  - **State of the system**: Deck has more than one card with duplicates: [card1, card1]
  - **Expected output**: Returns card1; deck is still [card1, card1]

### Method under test: `removeTop()`
- **TC9: Remove top from empty deck** ( :white_check_mark: )
  - **Name of the test**: `removeTop_emptyDeck_throwsUnsupportedOperationException`
  - **State of the system**: Deck is empty: []
  - **Expected output**: Throws UnsupportedOperationException; deck is still empty: []

- **TC10: Remove top from deck with one card** ( :white_check_mark: )
  - **Name of the test**: `removeTop_oneCardDeck_returnsTopCard`
  - **State of the system**: Deck has exactly one card: [card1]
  - **Expected output**: Returns card1; deck becomes empty: []

- **TC11: Remove top from deck with more than one different card** ( :white_check_mark: )
  - **Name of the test**: `removeTop_multipleDifferentCards_returnsTopCard`
  - **State of the system**: Deck has more than one card with different cards: [card1, card2]
  - **Expected output**: Returns card1; deck becomes [card2]

- **TC12: Remove top from deck with more than one duplicate card** ( :white_check_mark: )
  - **Name of the test**: `removeTop_multipleDuplicateCards_returnsTopCard`
  - **State of the system**: Deck has more than one card with duplicates: [card1, card1]
  - **Expected output**: Returns card1; deck becomes [card1]

### Method under test: `peekBottom()`
- **TC13: Peek bottom of empty deck** ( :x: )
  - **Name of the test**: `peekBottom_emptyDeck_throwsUnsupportedOperationException`
  - **State of the system**: Deck is empty: []
  - **Expected output**: Throws UnsupportedOperationException; deck is still empty: []

- **TC14: Peek bottom of deck with one card** ( :x: )
  - **Name of the test**: `peekBottom_oneCardDeck_returnsBottomCard`
  - **State of the system**: Deck has exactly one card: [card1]
  - **Expected output**: Returns card1; deck is still [card1]

- **TC15: Peek bottom of deck with more than one different card** ( :x: )
  - **Name of the test**: `peekBottom_multipleDifferentCards_returnsBottomCard`
  - **State of the system**: Deck has more than one card with different cards: [card1, card2]
  - **Expected output**: Returns card2; deck is still [card1, card2]

- **TC16: Peek bottom of deck with more than one duplicate card** ( :x: )
  - **Name of the test**: `peekBottom_multipleDuplicateCards_returnsBottomCard`
  - **State of the system**: Deck has more than one card with duplicates: [card1, card1]
  - **Expected output**: Returns card1; deck is still [card1, card1]