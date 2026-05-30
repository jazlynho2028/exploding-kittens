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
  - **Name of the test**: `peekTop_emptyDeck_throwsIllegalStateException`
  - **State of the system**: Deck is empty: []
  - **Expected output**: Throws IllegalStateException with message `error.emptyDeck`; deck is still empty: []

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
  - **Name of the test**: `removeTop_emptyDeck_throwsIllegalStateException`
  - **State of the system**: Deck is empty: []
  - **Expected output**: Throws IllegalStateException with message `error.emptyDeck`; deck is still empty: []

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
- **TC13: Peek bottom of empty deck** ( :white_check_mark: )
  - **Name of the test**: `peekBottom_emptyDeck_throwsIllegalStateException`
  - **State of the system**: Deck is empty: []
  - **Expected output**: Throws IllegalStateException with message `error.emptyDeck`; deck is still empty: []

- **TC14: Peek bottom of deck with one card** ( :white_check_mark: )
  - **Name of the test**: `peekBottom_oneCardDeck_returnsBottomCard`
  - **State of the system**: Deck has exactly one card: [card1]
  - **Expected output**: Returns card1; deck is still [card1]

- **TC15: Peek bottom of deck with more than one different card** ( :white_check_mark: )
  - **Name of the test**: `peekBottom_multipleDifferentCards_returnsBottomCard`
  - **State of the system**: Deck has more than one card with different cards: [card1, card2]
  - **Expected output**: Returns card2; deck is still [card1, card2]

- **TC16: Peek bottom of deck with more than one duplicate card** ( :white_check_mark: )
  - **Name of the test**: `peekBottom_multipleDuplicateCards_returnsBottomCard`
  - **State of the system**: Deck has more than one card with duplicates: [card1, card1]
  - **Expected output**: Returns card1; deck is still [card1, card1]

### Method under test: `peekTopNCards(int n)`
- **TC17: Peek top N cards from empty deck when n is zero** ( :white_check_mark: )
  - **Name of the test**: `peekTopNCards_emptyDeckAndZeroCards_returnsEmptyList`
  - **State of the system**: Deck is empty: []; n = 0
  - **Expected output**: Returns empty list []; deck is still empty: []

- **TC18: Peek top N cards from empty deck when n is positive** ( :white_check_mark: )
  - **Name of the test**: `peekTopNCards_emptyDeckAndPositiveCount_throwsIllegalStateException`
  - **State of the system**: Deck is empty: []; n = 1
  - **Expected output**: Throws IllegalStateException with message `error.emptyDeck`; deck is still empty: []

- **TC19: Peek top N cards when n is negative** ( :white_check_mark: )
  - **Name of the test**: `peekTopNCards_negativeCount_throwsIllegalArgumentException`
  - **State of the system**: Deck has cards: [card1, card2]; n = -1
  - **Expected output**: Throws IllegalArgumentException; deck is still [card1, card2]

- **TC20: Peek top N cards when n is zero** ( :white_check_mark: )
  - **Name of the test**: `peekTopNCards_zeroCount_returnsEmptyList`
  - **State of the system**: Deck has cards: [card1, card2]; n = 0
  - **Expected output**: Returns empty list []; deck is still [card1, card2]

- **TC21: Peek top N cards when n is one** ( :white_check_mark: )
  - **Name of the test**: `peekTopNCards_oneCardCount_returnsTopCardOnly`
  - **State of the system**: Deck has cards: [card1, card2]; n = 1
  - **Expected output**: Returns [card1]; deck is still [card1, card2]

- **TC22: Peek top N cards when n equals deck size** ( :white_check_mark: )
  - **Name of the test**: `peekTopNCards_countEqualsDeckSize_returnsAllCards`
  - **State of the system**: Deck has cards: [card1, card2]; n = 2
  - **Expected output**: Returns [card1, card2]; deck is still [card1, card2]

- **TC23: Peek top N cards when n is greater than deck size** ( :white_check_mark: )
  - **Name of the test**: `peekTopNCards_countGreaterThanDeckSize_throwsIllegalStateException`
  - **State of the system**: Deck has cards: [card1, card2]; n = 3
  - **Expected output**: Throws IllegalStateException with message `error.emptyDeck`; deck is still [card1, card2]

- **TC24: Peek top N cards with duplicate cards** ( :white_check_mark: )
  - **Name of the test**: `peekTopNCards_duplicateCards_returnsTopCardsInOrder`
  - **State of the system**: Deck has cards with duplicates: [card1, card1, card2]; n = 2
  - **Expected output**: Returns [card1, card1]; deck is still [card1, card1, card2]

### Method under test: `removeBottom()`
- **TC25: Remove bottom from empty deck** ( :white_check_mark: )
  - **Name of the test**: `removeBottom_emptyDeck_throwsIllegalStateException`
  - **State of the system**: Deck is empty: []
  - **Expected output**: Throws IllegalStateException with message `error.emptyDeck`; deck is still empty: []

- **TC26: Remove bottom from deck with one card** ( :white_check_mark: )
  - **Name of the test**: `removeBottom_oneCardDeck_returnsBottomCard`
  - **State of the system**: Deck has exactly one card: [card1]
  - **Expected output**: Returns card1; deck becomes empty: []

- **TC27: Remove bottom from deck with more than one different card** ( :white_check_mark: )
  - **Name of the test**: `removeBottom_multipleDifferentCards_returnsBottomCard`
  - **State of the system**: Deck has more than one card with different cards: [card1, card2]
  - **Expected output**: Returns card2; deck becomes [card1]

- **TC28: Remove bottom from deck with more than one duplicate card** ( :white_check_mark: )
  - **Name of the test**: `removeBottom_multipleDuplicateCards_returnsBottomCard`
  - **State of the system**: Deck has more than one card with duplicates: [card1, card1]
  - **Expected output**: Returns card1; deck becomes [card1]

### Method under test: `addCard(Card card)`
- **TC29: Add card to empty deck** ( :white_check_mark: )
  - **Name of the test**: `addCard_emptyDeck_addsCardToDeck`
  - **State of the system**: Deck is empty: []; card = card1
  - **Expected output**: Deck becomes [card1]; deck size is 1

- **TC30: Add card to deck with one card** ( :white_check_mark: )
  - **Name of the test**: `addCard_oneCardDeck_addsCardToBottom`
  - **State of the system**: Deck has exactly one card: [card1]; card = card2
  - **Expected output**: Deck becomes [card1, card2]; deck size is 2

- **TC31: Add card to deck with multiple different cards** ( :white_check_mark: )
  - **Name of the test**: `addCard_multipleDifferentCards_addsCardToBottom`
  - **State of the system**: Deck has cards: [card1, card2]; card = card3
  - **Expected output**: Deck becomes [card1, card2, card3]; deck size is 3

- **TC32: Add duplicate card to deck** ( :white_check_mark: )
  - **Name of the test**: `addCard_duplicateCard_addsCardToBottom`
  - **State of the system**: Deck has cards: [card1, card2]; card = card1
  - **Expected output**: Deck becomes [card1, card2, card1]; deck size is 3

### Method under test: `isEmpty()`
- **TC33: Check if empty deck is empty** ( :white_check_mark: )
  - **Name of the test**: `isEmpty_emptyDeck_returnsTrue`
  - **State of the system**: Deck is empty: []
  - **Expected output**: Returns true

- **TC34: Check if one-card deck is empty** ( :white_check_mark: )
  - **Name of the test**: `isEmpty_oneCardDeck_returnsFalse`
  - **State of the system**: Deck has exactly one card: [card1]
  - **Expected output**: Returns false

- **TC35: Check if multi-card deck is empty** ( :white_check_mark: )
  - **Name of the test**: `isEmpty_multipleCards_returnsFalse`
  - **State of the system**: Deck has cards: [card1, card2]
  - **Expected output**: Returns false

### Method under test: `getCards()`
- **TC36: Get cards from empty deck** ( :white_check_mark: )
  - **Name of the test**: `getCards_emptyDeck_returnsEmptyList`
  - **State of the system**: Deck is empty: []
  - **Expected output**: Returns empty list []; deck is still empty: []

- **TC37: Get cards from one-card deck** ( :white_check_mark: )
  - **Name of the test**: `getCards_oneCardDeck_returnsCopyWithOneCard`
  - **State of the system**: Deck has exactly one card: [card1]
  - **Expected output**: Returns [card1]; deck is still [card1]

- **TC38: Get cards from multiple-card deck without duplicates** ( :x: )
  - **Name of the test**: `getCards_multipleDifferentCards_returnsCopyInOrderWithoutDuplicates`
  - **State of the system**: Deck has cards without duplicates: [card1, card2]
  - **Expected output**: Returns [card1, card2] in the same order; deck is still [card1, card2]

- **TC39: Get cards from multiple-card deck with duplicates** ( :x: )
  - **Name of the test**: `getCards_multipleDuplicateCards_returnsCopyInOrderWithDuplicates`
  - **State of the system**: Deck has cards with duplicates: [card1, card1, card2]
  - **Expected output**: Returns [card1, card1, card2] with both copies of card1 preserved in order; deck is still [card1, card1, card2]