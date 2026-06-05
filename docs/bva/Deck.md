# BVA Analysis: Deck Class

### Method under test: `shuffle()`
- **TC1: Shuffle empty deck** ( :white_check_mark: )
  - **Name of the test**: `shuffle_emptyDeck_deckStaysEmpty`
  - **State of the system**: Deck is empty: []
  - **Expected output**: 
    - Deck is still empty: []
    - deck size is 0

- **TC2: Shuffle deck with one card** ( :white_check_mark: )
  - **Name of the test**: `shuffle_oneCardDeck_deckOrderUnchanged`
  - **State of the system**: Deck has exactly one card: [card1]
  - **Expected output**: 
    - Deck is still [card1]
    - deck size is 1

- **TC3: Shuffle deck with more than one different card using controlled random value** ( :white_check_mark: )
  - **Name of the test**: `shuffle_multipleDifferentCards_controlledRandomReordersDeck`
  - **State of the system**: Deck has more than one different card: [card2, card1]; mocked random value returns 0
  - **Expected output**: 
    - Deck still contains card1 and card2
    - card1 is at index 0 after shuffle

- **TC4: Shuffle deck with more than one duplicate card using controlled random values** ( :white_check_mark: )
  - **Name of the test**: `shuffle_multipleDuplicateCards_controlledRandomReordersDeck`
  - **State of the system**: Deck has more than one card with duplicates: [card1, card1, card2]; mocked random values return 0 and 0
  - **Expected output**: 
    - Deck becomes [card1, card2, card1]
    - deck size is 3

### Method under test: `peekTop()`
- **TC5: Peek top of empty deck** ( :white_check_mark: )
  - **Name of the test**: `peekTop_emptyDeck_throwsIllegalStateException`
  - **State of the system**: Deck is empty: []
  - **Expected output**: 
    - Throws IllegalStateException with message `error.emptyDeck`
    - deck is still empty: []

- **TC6: Peek top of deck with one card** ( :white_check_mark: )
  - **Name of the test**: `peekTop_nonEmptyDeck_returnsTopCard` with case `one-card deck`
  - **State of the system**: Deck has exactly one card: [card1]
  - **Expected output**: 
    - Returns card1
    - deck is still [card1]

- **TC7: Peek top of deck with more than one different card** ( :white_check_mark: )
  - **Name of the test**: `peekTop_nonEmptyDeck_returnsTopCard` with case `multiple different cards`
  - **State of the system**: Deck has more than one card with different cards: [card1, card2]
  - **Expected output**: 
    - Returns card1
    - deck is still [card1, card2]

- **TC8: Peek top of deck with more than one duplicate card** ( :white_check_mark: )
  - **Name of the test**: `peekTop_nonEmptyDeck_returnsTopCard` with case `multiple duplicate cards`
  - **State of the system**: Deck has more than one card with duplicates: [card1, card1]
  - **Expected output**: 
    - Returns card1
    - deck is still [card1, card1]

### Method under test: `removeTop()`
- **TC9: Remove top from empty deck** ( :white_check_mark: )
  - **Name of the test**: `removeTop_emptyDeck_throwsIllegalStateException`
  - **State of the system**: Deck is empty: []
  - **Expected output**: 
    - Throws IllegalStateException with message `error.emptyDeck`
    - deck is still empty: []

- **TC10: Remove top from deck with one card** ( :white_check_mark: )
  - **Name of the test**: `removeTop_nonEmptyDeck_returnsTopCard` with case `one-card deck`
  - **State of the system**: Deck has exactly one card: [card1]
  - **Expected output**: 
    - Returns card1
    - deck becomes empty: []

- **TC11: Remove top from deck with more than one different card** ( :white_check_mark: )
  - **Name of the test**: `removeTop_nonEmptyDeck_returnsTopCard` with case `multiple different cards`
  - **State of the system**: Deck has more than one card with different cards: [card1, card2]
  - **Expected output**: 
    - Returns card1
    - deck becomes [card2]

- **TC12: Remove top from deck with more than one duplicate card** ( :white_check_mark: )
  - **Name of the test**: `removeTop_nonEmptyDeck_returnsTopCard` with case `multiple duplicate cards`
  - **State of the system**: Deck has more than one card with duplicates: [card1, card1]
  - **Expected output**: 
    - Returns card1
    - deck becomes [card1]

### Method under test: `peekBottom()`
- **TC13: Peek bottom of empty deck** ( :white_check_mark: )
  - **Name of the test**: `peekBottom_emptyDeck_throwsIllegalStateException`
  - **State of the system**: Deck is empty: []
  - **Expected output**: 
    - Throws IllegalStateException with message `error.emptyDeck`
    - deck is still empty: []

- **TC14: Peek bottom of deck with one card** ( :white_check_mark: )
  - **Name of the test**: `peekBottom_nonEmptyDeck_returnsBottomCard` with case `one-card deck`
  - **State of the system**: Deck has exactly one card: [card1]
  - **Expected output**: 
    - Returns card1
    - deck is still [card1]

- **TC15: Peek bottom of deck with more than one different card** ( :white_check_mark: )
  - **Name of the test**: `peekBottom_nonEmptyDeck_returnsBottomCard` with case `multiple different cards`
  - **State of the system**: Deck has more than one card with different cards: [card1, card2]
  - **Expected output**: 
    - Returns card2
    - deck is still [card1, card2]

- **TC16: Peek bottom of deck with more than one duplicate card** ( :white_check_mark: )
  - **Name of the test**: `peekBottom_nonEmptyDeck_returnsBottomCard` with case `multiple duplicate cards`
  - **State of the system**: Deck has more than one card with duplicates: [card1, card1]
  - **Expected output**: 
    - Returns card1
    - deck is still [card1, card1]

### Method under test: `peekTopNCards(int n)`
- **TC17: Peek top N cards from empty deck when n is zero** ( :white_check_mark: )
  - **Name of the test**: `peekTopNCards_zeroCount_returnsEmptyList` with case `empty deck`
  - **State of the system**: Deck is empty: []; n = 0
  - **Expected output**: 
    - Returns empty list []
    - deck is still empty: []

- **TC18: Peek top N cards from empty deck when n is positive** ( :white_check_mark: )
  - **Name of the test**: `peekTopNCards_invalidCount_throwsException` with case `empty deck and positive count`
  - **State of the system**: Deck is empty: []; n = 1
  - **Expected output**: 
    - Throws IllegalStateException with message `error.notEnoughCards`
    - deck is still empty: []

- **TC19: Peek top N cards when n is negative** ( :white_check_mark: )
  - **Name of the test**: `peekTopNCards_invalidCount_throwsException` with case `negative count`
  - **State of the system**: Deck has cards: [card1, card2]; n = -1
  - **Expected output**: Throws IllegalArgumentException with message `error.peekNegativeCards`

- **TC20: Peek top N cards when n is zero** ( :white_check_mark: )
  - **Name of the test**: `peekTopNCards_zeroCount_returnsEmptyList` with case `non-empty deck`
  - **State of the system**: Deck has cards: [card1, card2]; n = 0
  - **Expected output**: 
    - Returns empty list []
    - deck is still [card1, card2]

- **TC21: Peek top N cards when n is one** ( :white_check_mark: )
  - **Name of the test**: `peekTopNCards_validCount_returnsTopCardsInOrder` with case `one-card count`
  - **State of the system**: Deck has cards: [card1, card2]; n = 1
  - **Expected output**: 
    - Returns [card1]
    - deck is still [card1, card2]

- **TC22: Peek top N cards when n equals deck size** ( :white_check_mark: )
  - **Name of the test**: `peekTopNCards_validCount_returnsTopCardsInOrder` with case `count equals deck size`
  - **State of the system**: Deck has cards: [card1, card2]; n = 2
  - **Expected output**: 
    - Returns [card1, card2]
    - deck is still [card1, card2]

- **TC23: Peek top N cards when n is greater than deck size** ( :white_check_mark: )
  - **Name of the test**: `peekTopNCards_invalidCount_throwsException` with case `count greater than deck size`
  - **State of the system**: Deck has cards: [card1, card2]; n = 3
  - **Expected output**: 
    - Throws IllegalStateException with message `error.notEnoughCards`
    - deck is still [card1, card2]

- **TC24: Peek top N cards with duplicate cards** ( :white_check_mark: )
  - **Name of the test**: `peekTopNCards_validCount_returnsTopCardsInOrder` with case `duplicate cards`
  - **State of the system**: Deck has cards with duplicates: [card1, card1, card2]; n = 2
  - **Expected output**: 
    - Returns [card1, card1]
    - deck is still [card1, card1, card2]

### Method under test: `removeBottom()`
- **TC25: Remove bottom from empty deck** ( :white_check_mark: )
  - **Name of the test**: `removeBottom_emptyDeck_throwsIllegalStateException`
  - **State of the system**: Deck is empty: []
  - **Expected output**: 
    - Throws IllegalStateException with message `error.emptyDeck`
    - deck is still empty: []

- **TC26: Remove bottom from deck with one card** ( :white_check_mark: )
  - **Name of the test**: `removeBottom_nonEmptyDeck_returnsBottomCard` with case `one-card deck`
  - **State of the system**: Deck has exactly one card: [card1]
  - **Expected output**: 
    - Returns card1
    - deck becomes empty: []

- **TC27: Remove bottom from deck with more than one different card** ( :white_check_mark: )
  - **Name of the test**: `removeBottom_nonEmptyDeck_returnsBottomCard` with case `multiple different cards`
  - **State of the system**: Deck has more than one card with different cards: [card1, card2]
  - **Expected output**: 
    - Returns card2
    - deck becomes [card1]

- **TC28: Remove bottom from deck with more than one duplicate card** ( :white_check_mark: )
  - **Name of the test**: `removeBottom_nonEmptyDeck_returnsBottomCard` with case `multiple duplicate cards`
  - **State of the system**: Deck has more than one card with duplicates: [card1, card1]
  - **Expected output**: 
    - Returns card1
    - deck becomes [card1]

### Method under test: `addCardtoTop(Card card)`
- **TC29: Add card to empty deck** ( :white_check_mark: )
  - **Name of the test**: `addCardToTop_validCard_addsCardToTop` with case `empty deck`
  - **State of the system**: Deck is empty: []; card = card1
  - **Expected output**: 
    - Deck becomes [card1]
    - deck size is 1

- **TC30: Add card to deck with one card** ( :white_check_mark: )
  - **Name of the test**: `addCardToTop_validCard_addsCardToTop` with case `one-card deck`
  - **State of the system**: Deck has exactly one card: [card1]; card = card2
  - **Expected output**: 
    - Deck becomes [card2, card1]
    - deck size is 2

- **TC31: Add card to deck with multiple different cards** ( :white_check_mark: )
  - **Name of the test**: `addCardToTop_validCard_addsCardToTop` with case `multiple different cards`
  - **State of the system**: Deck has cards: [card1, card2]; card = card3
  - **Expected output**: 
    - Deck becomes [card3, card1, card2]
    - deck size is 3

- **TC32: Add duplicate card to deck** ( :white_check_mark: )
  - **Name of the test**: `addCardToTop_validCard_addsCardToTop` with case `duplicate card`
  - **State of the system**: Deck has cards: [card1, card2]; card = card1
  - **Expected output**: 
    - Deck becomes [card1, card1, card2]
    - deck size is 3

### Method under test: `isEmpty()`
- **TC33: Check if empty deck is empty** ( :white_check_mark: )
  - **Name of the test**: `isEmpty_emptyDeck_returnsTrue`
  - **State of the system**: Deck is empty: []
  - **Expected output**: Returns true

- **TC34: Check if one-card deck is empty** ( :white_check_mark: )
  - **Name of the test**: `isEmpty_nonEmptyDeck_returnsFalse` with case `one-card deck`
  - **State of the system**: Deck has exactly one card: [card1]
  - **Expected output**: Returns false

- **TC35: Check if multi-card deck is empty** ( :white_check_mark: )
  - **Name of the test**: `isEmpty_nonEmptyDeck_returnsFalse` with case `multiple-card deck`
  - **State of the system**: Deck has cards: [card1, card2]
  - **Expected output**: Returns false

### Method under test: `getCards()`
- **TC36: Get cards from empty deck** ( :white_check_mark: )
  - **Name of the test**: `getCards_called_returnsCopyInDeckOrder` with case `empty deck`
  - **State of the system**: Deck is empty: []
  - **Expected output**: 
    - Returns empty list []
    - deck is still empty: []

- **TC37: Get cards from one-card deck** ( :white_check_mark: )
  - **Name of the test**: `getCards_called_returnsCopyInDeckOrder` with case `one-card deck`
  - **State of the system**: Deck has exactly one card: [card1]
  - **Expected output**: 
    - Returns [card1]
    - deck is still [card1]

- **TC38: Get cards from multiple-card deck without duplicates** ( :white_check_mark: )
  - **Name of the test**: `getCards_called_returnsCopyInDeckOrder` with case `multiple different cards`
  - **State of the system**: Deck has cards without duplicates: [card1, card2]
  - **Expected output**: 
    - Returns [card1, card2] in the same order
    - deck is still [card1, card2]

- **TC39: Get cards from multiple-card deck with duplicates** ( :white_check_mark: )
  - **Name of the test**: `getCards_called_returnsCopyInDeckOrder` with case `multiple duplicate cards`
  - **State of the system**: Deck has cards with duplicates: [card1, card1, card2]
  - **Expected output**: 
    - Returns [card1, card1, card2] with both copies of card1 preserved in order
    - deck is still [card1, card1, card2]

### Method under test: `insertCardAt(Card card, int index)`
- **TC40: Insert card into empty deck at index zero** ( :white_check_mark: )
  - **Name of the test**: `insertCardAt_validIndex_insertsCardAtIndex` with case `empty deck at index zero`
  - **State of the system**: Deck is empty: []; card = card1; index = 0
  - **Expected output**: Deck becomes [card1]

- **TC41: Insert card at top of non-empty deck** ( :white_check_mark: )
  - **Name of the test**: `insertCardAt_validIndex_insertsCardAtIndex` with case `insert at top`
  - **State of the system**: Deck has cards: [card1, card2]; card = card3; index = 0
  - **Expected output**: Deck becomes [card3, card1, card2]

- **TC42: Insert card in middle of deck** ( :white_check_mark: )
  - **Name of the test**: `insertCardAt_validIndex_insertsCardAtIndex` with case `insert in middle`
  - **State of the system**: Deck has cards: [card1, card2]; card = card3; index = 1
  - **Expected output**: Deck becomes [card1, card3, card2]

- **TC43: Insert card at end of deck** ( :white_check_mark: )
  - **Name of the test**: `insertCardAt_validIndex_insertsCardAtIndex` with case `insert at end`
  - **State of the system**: Deck has cards: [card1, card2]; card = card3; index = 2
  - **Expected output**: Deck becomes [card1, card2, card3]

- **TC44: Insert duplicate card into deck** ( :white_check_mark: )
  - **Name of the test**: `insertCardAt_validIndex_insertsCardAtIndex` with case `insert duplicate card`
  - **State of the system**: Deck has cards: [card1, card2]; card = card1; index = 1
  - **Expected output**: Deck becomes [card1, card1, card2]

- **TC45: Insert card at negative index** ( :white_check_mark: )
  - **Name of the test**: `insertCardAt_invalidIndex_throwsIllegalArgumentException` with case `negative index`
  - **State of the system**: Deck has cards: [card1, card2]; card = card3; index = -1
  - **Expected output**: 
    - Throws IllegalArgumentException with message `error.invalidDeckIndex`
    - deck is still [card1, card2]

- **TC46: Insert card at index greater than deck size** ( :white_check_mark: )
  - **Name of the test**: `insertCardAt_invalidIndex_throwsIllegalArgumentException` with case `index greater than size`
  - **State of the system**: Deck has cards: [card1, card2]; card = card3; index = 3
  - **Expected output**: 
    - Throws IllegalArgumentException with message `error.invalidDeckIndex`
    - deck is still [card1, card2]

### Method under test: `addCardtoBottom(Card card)`
- **TC29: Add card to empty deck** ( :white_check_mark: )
  - **Name of the test**: `addCardToBottom_validCard_addsCardToBottom` with case `empty deck`
  - **State of the system**: Deck is empty: []; card = card1
  - **Expected output**:
    - Deck becomes [card1]
    - deck size is 1

- **TC30: Add card to deck with one card** ( :white_check_mark: )
  - **Name of the test**: `addCardToBottom_validCard_addsCardToBottom` with case `one-card deck`
  - **State of the system**: Deck has exactly one card: [card1]; card = card2
  - **Expected output**:
    - Deck becomes [card1, card2]
    - deck size is 2

- **TC31: Add card to deck with multiple different cards** ( :white_check_mark: )
  - **Name of the test**: `addCardToBottom_validCard_addsCardToTop` with case `multiple different cards`
  - **State of the system**: Deck has cards: [card1, card2]; card = card3
  - **Expected output**:
    - Deck becomes [card1, card2, card3]
    - deck size is 3

- **TC32: Add duplicate card to deck** ( :white_check_mark: )
  - **Name of the test**: `addCardToTopBottom_validCard_addsCardToTop` with case `duplicate card`
  - **State of the system**: Deck has cards: [card1, card2]; card = card1
  - **Expected output**:
    - Deck becomes [card1, card2, card1]
    - deck size is 3