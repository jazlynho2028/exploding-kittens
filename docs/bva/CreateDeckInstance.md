# BVA Analysis: DeckBuilder Integration

### Method under test: `createDeckInstance()`
- **TC1: createDeckInstance with empty list** ( :white_check_mark: )
  - **Name of the test**: `createDeckInstance_emptyList_returnsEmptyDeck`
  - **State of the system**: Input list is empty: []
  - **Expected output**:
    - Deck is empty: []
    - Deck size is 0
    - No calls to mockRandom

- **TC2: createDeckInstance with one card** ( :white_check_mark: )
  - **Name of the test**: `createDeckInstance_oneCard_returnsDeckWithOneCard`
  - **State of the system**: Input list has exactly one card: [card1]
  - **Expected output**:
    - Deck contains [card1]
    - Deck size is 1
    - No calls to mockRandom (single card needs no shuffle)

- **TC3: createDeckInstance with two different cards using controlled random value** ( :white_check_mark: )
  - **Name of the test**: `createDeckInstance_twoDifferentCards_returnsDeckWithBothCards`
  - **State of the system**: 
    - Input list has two different cards: [card1, card2]
    - mocked random returns 0
  - **Expected output**:
    - Deck contains [card2, card1]
    - Deck size is 2
    - mockRandom.nextInt(TWO_CARDS) called once

- **TC4: createDeckInstance with three cards including one duplicate using controlled random values** ( :white_check_mark: )
  - **Name of the test**: `createDeckInstance_threeCardsOneDuplicate_returnsDeckWithAllCards`
  - **State of the system**: Input list has three cards with one duplicate: 
    - [card1, card2, card1]
    - mocked random returns 0 and 0
  - **Expected output**:
    - Deck contains [card2, card1, card1]
    - Deck size is 3
    - mockRandom.nextInt(THREE_CARDS) called once, mockRandom.nextInt(TWO_CARDS) called once